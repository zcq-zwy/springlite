package com.zcqzwy.springframework.util;

import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.cglib.proxy.Factory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ClassUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ClassUtils {
  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    }
    catch (Throwable ex) {
      // Cannot access thread context ClassLoader - falling back to system class loader...
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = ClassUtils.class.getClassLoader();
    }
    return cl;
  }

  private ClassUtils() {
    // 工具类禁止实例化
  }


  public static boolean isJdkDynamicProxyClass(Class<?> clazz) {
    return clazz != null && Proxy.isProxyClass(clazz);
  }

  /**
   * CGLIB 代理类标识符（类名中包含的特定字符串）
   */
  private static final String CGLIB_CLASS_SEPARATOR = "$$EnhancerByCGLIB$$";

  /**
   * 判断一个类是否是 CGLIB 生成的代理类
   * @param clazz 待检查的类
   * @return true 如果是 CGLIB 代理类，否则 false
   */
  public static boolean isCglibProxyClass(Class<?> clazz) {
    if (clazz == null) {
      return false;
    }

    // 特征 1: 类名中包含 CGLIB 标识符
    boolean hasCglibClassName = clazz.getName().contains(CGLIB_CLASS_SEPARATOR);

    // 特征 2: 实现 CGLIB 的 Factory 接口
    boolean implementsCglibFactory = Factory.class.isAssignableFrom(clazz);

    // 同时满足类名特征和接口特征，则判定为 CGLIB 代理类
    return hasCglibClassName && implementsCglibFactory;
  }

  /**
   * 获取目标类（如果是 CGLIB 代理类，则返回父类；否则返回原类）
   * @param clazz 输入类
   * @return 目标类
   */
  public static Class<?> getTargetClass(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }
    return isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
  }

  /**
   * 将类的包名转换为资源路径（将包名中的"."替换为"/"）
   * 示例：
   * com.example.MyClass → com/example
   * java.lang.String[]   → java/lang（自动处理数组类型）
   * 默认包（无包声明） → 空字符串
   *
   * @param clazz 目标类（允许为null，返回空字符串）
   * @return 资源路径格式的包名
   */
  public static String classPackageAsResourcePath(Class<?> clazz) {
    if (clazz == null) {
      return "";
    }

    // 处理数组类型（递归获取组件类型的类）
    Class<?> targetClass = clazz;
    while (targetClass.isArray()) {
      targetClass = targetClass.getComponentType();
    }

    String className = targetClass.getName();
    int lastDotIndex = className.lastIndexOf('.');

    if (lastDotIndex == -1) {
      // 默认包或无包名
      return "";
    }

    String packageName = className.substring(0, lastDotIndex);
    return packageName.replace('.', '/');
  }


  /**
   * 扫描指定包路径下包含指定注解的类
   * @param basePackage 要扫描的包路径 (如: com.example)
   * @param annotation 目标注解类型
   * @return 包含目标注解的类集合
   */
  public static Set<Class<?>> scanPackageByAnnotation(String basePackage,
      Class<? extends Annotation> annotation) {
    String packagePath = basePackage.replace('.', '/');
    Set<Class<?>> classes = new HashSet<>();

    try {
      Enumeration<URL> resources = Thread.currentThread()
          .getContextClassLoader()
          .getResources(packagePath);

      while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
        if (resource.getProtocol().equals("file")) {
          // 处理文件系统目录
          classes.addAll(scanFileSystem(resource, annotation));
        } else if (resource.getProtocol().equals("jar")) {
          // 处理 JAR 包
          classes.addAll(scanJarFile(resource, basePackage, annotation));
        }
      }
    } catch (IOException | URISyntaxException | ClassNotFoundException e) {
      throw new RuntimeException("Package scan failed", e);
    }

    return classes;
  }

  // ---------------------- 私有方法 ----------------------

  /**
   * 扫描文件系统目录
   */
  private static Set<Class<?>> scanFileSystem(URL resource,
      Class<? extends Annotation> annotation)
      throws URISyntaxException, IOException, ClassNotFoundException {
    Path rootPath = Paths.get(resource.toURI());
    System.out.println("[DEBUG] 扫描根目录: " + rootPath);

    try (Stream<Path> pathStream = Files.walk(rootPath)) {
      return pathStream
          .filter(Files::isRegularFile)
          .peek(path -> System.out.println("[DEBUG] 处理文件: " + path))
          .filter(path -> path.toString().endsWith(".class"))
          .peek(path -> System.out.println("[DEBUG] 发现类文件: " + path))
          .map(path -> convertPathToClassName(rootPath, path))
          .peek(className -> System.out.println("[DEBUG] 转换类名: " + className))
          .map(className -> {
            try {
              return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
              System.out.println("[WARN] 类加载失败: " + className);
              return null;
            }
          })
          .filter(Objects::nonNull)
          .peek(clazz -> System.out.println("[DEBUG] 成功加载类: " + clazz.getName()))
          .filter(clazz -> clazz.isAnnotationPresent(annotation))
          .peek(clazz -> System.out.println("[DEBUG] 发现带注解的类: " + clazz.getName()))
          .collect(Collectors.toSet());
    }
  }

  /**
   * 扫描 JAR 包
   */
  private static Set<Class<?>> scanJarFile(URL resource,
      String basePackage,
      Class<? extends Annotation> annotation)
      throws IOException, ClassNotFoundException {

    String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
    String packagePath = basePackage.replace('.', '/');

    try (JarFile jar = new JarFile(jarPath)) {
      return jar.stream()
          .filter(entry -> entry.getName().endsWith(".class"))
          .map(JarEntry::getName)
          .filter(name -> name.startsWith(packagePath))
          .map(name -> name.replace('/', '.').replace(".class", ""))
          .map(ClassUtils::loadClass)
          .filter(clazz -> clazz != null && clazz.isAnnotationPresent(annotation))
          .collect(Collectors.toSet());
    }
  }

  /**
   * 转换文件路径为全限定类名
   */
  private static String convertPathToClassName(Path rootPath, Path classFile) {
    // 计算相对路径（相对于根目录）
    Path relativePath = rootPath.relativize(classFile);

    // 转换为包名格式
    String className = relativePath.toString()
        .replace(File.separatorChar, '.')
        .replace(".class", "")
        .replaceAll("^\\.+", "");

    // 获取根目录对应的基础包名（需根据实际情况调整）
    String basePackage = extractBasePackage(rootPath);

    // 拼接完整类名
    return basePackage + "." + className;
  }

  /**
   * 从 rootPath 解析基础包名（示例逻辑）
   */
  private static String extractBasePackage(Path rootPath) {
    // 示例：将路径转换为包名（需根据项目结构调整）
    // rootPath: D:\project\target\classes\com\example
    // → com.example
    String pathStr = rootPath.toString().replace(File.separatorChar, '.');
    int startIndex = pathStr.indexOf("com.");
    if (startIndex != -1) {
      return pathStr.substring(startIndex);
    }
    return "";
  }

  /**
   * 安全加载类 (忽略无法加载的类)
   */
  private static Class<?> loadClass(String className) {
    try {
      return Class.forName(className, false,
          Thread.currentThread().getContextClassLoader());
    } catch (ClassNotFoundException | NoClassDefFoundError |
             UnsupportedClassVersionError e) {
      // 忽略无法加载的类
      return null;
    }
  }
}
