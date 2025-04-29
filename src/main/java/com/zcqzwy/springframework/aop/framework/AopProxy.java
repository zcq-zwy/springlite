package com.zcqzwy.springframework.aop.framework;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AopProxy </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 * 委托配置的 AOP 代理抽象，允许创建的实际代理对象。
 * 开箱即用<p>的实现可用于 JDK 动态代理和 CGLIB 代理，
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface AopProxy {


/**
     * 创建新的代理对象。
     * <p>使用 AopProxy 的默认类加载器（如果需要创建代理）：
     * 通常是线程上下文类 loader。
     * @return 新的代理对象（从未 {@code null}）
     */
    Object getProxy() throws Exception;

/**
     * 创建新的代理对象。
     * <p>使用给定的类加载器（如果需要创建代理）。
     * {@code null} 将简单地向下传递，从而导致低级
     * 代理工具的默认值，通常与选择的默认值不同
     * 通过 AopProxy 实现的 {@link #getProxy（）} 方法。
     * @param classLoader 创建类加载器以创建代理
     *（或 {@code null} 为低级代理工具的默认值）
     * @return 新的代理对象（从未 {@code null}）
     */
    Object getProxy(ClassLoader classLoader);

  }


