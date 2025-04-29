package com.zcqzwy.springframework.core.io;

import java.net.URL;
import org.jspecify.annotations.Nullable;
import com.zcqzwy.springframework.util.Assert;
import com.zcqzwy.springframework.util.ClassUtils;
import com.zcqzwy.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ClassPathResource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ClassPathResource implements Resource {

  /**
   * Internal representation of the original path supplied by the user,
   * used for creating relative paths and resolving URLs and InputStreams.
   */
  private final String path;

  private final String absolutePath;

  private final @Nullable ClassLoader classLoader;

  private final @Nullable Class<?> clazz;



  public ClassPathResource(String path) {
    this(path, (ClassLoader) null);
  }

  public ClassPathResource(String path, @Nullable ClassLoader classLoader) {
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("Path must not be empty");
    }
    String pathToUse = StringUtils.cleanPath(path);
    if (pathToUse.startsWith(ROOT_PATH)) {
      pathToUse = pathToUse.substring(1);
    }
    this.path = pathToUse;
    this.absolutePath = pathToUse;
    this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    this.clazz = null;
  }

  public ClassPathResource(String path, @Nullable Class<?> clazz) {
    Assert.notNull(path, "Path must not be null");
    this.path = StringUtils.cleanPath(path);

    String absolutePath = this.path;
    if (clazz != null && !absolutePath.startsWith(ROOT_PATH)) {
      absolutePath = ClassUtils.classPackageAsResourcePath(clazz) + "/" + absolutePath;
    }
    else if (absolutePath.startsWith(ROOT_PATH)) {
      absolutePath = absolutePath.substring(1);
    }
    this.absolutePath = absolutePath;

    this.classLoader = null;
    this.clazz = clazz;
  }


  public final String getPath() {
    return this.absolutePath;
  }

  public final @Nullable ClassLoader getClassLoader() {
    return (this.clazz != null ? this.clazz.getClassLoader() : this.classLoader);
  }

  @Override
  public String getDescription() {
    return "class path resource [" + this.absolutePath + "]";
  }

  @Override
  public boolean exists() {
    return (resolveURL() != null);
  }

/**
   * 解析底层类路径资源的 URL。
   * @return 解析的 URL，如果无法解析，则为 {@code null}
   */
  protected URL resolveURL() {
    if (this.clazz != null) {
      return this.clazz.getResource(this.path);
    }
    else if (this.classLoader != null) {
      return this.classLoader.getResource(this.path);
    }
    else {
      return ClassLoader.getSystemResource(this.path);
    }
  }


  @Override
  public InputStream getInputStream() throws IOException {
    InputStream is;
    if (this.clazz != null) {
      is = this.clazz.getResourceAsStream(this.path);
    }
    else if (this.classLoader != null) {
      is = this.classLoader.getResourceAsStream(this.absolutePath);
    }
    else {
      is = ClassLoader.getSystemResourceAsStream(this.absolutePath);
    }
    if (is == null) {
      throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
    }
    return is;
  }



}
