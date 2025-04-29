package com.zcqzwy.springframework.context;

import com.zcqzwy.springframework.beans.BeansException;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConfigurableApplicationContext </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 * Spring 应用上下文，支持配置相关属性
 * SPI（服务提供接口），大多数（如果不是全部）应用上下文都应实现此接口。
 * 除了在 {@link ApplicationContext} 接口中提供的应用上下文客户端方法外，
 * 还提供了配置应用上下文的工具。
 *<p>配置和生命周期方法封装在此接口中，以避免让应用上下文客户端代码直接看到这些方法。
 * 当前的方法应仅由启动和关闭代码使用。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ConfigurableApplicationContext extends ApplicationContext {

  /**
   * 加载或刷新配置的持久化表示，可能是 XML 文件、属性文件或关系数据库模式。
   * <p>由于这是一个启动方法，如果失败，它应该销毁已经创建的单例对象，以避免资源悬空。
   * 换句话说，在调用此方法后，要么所有单例对象都被实例化，要么一个都不实例化。相当于一个原子方法
   * @throws BeansException 如果无法初始化 bean 工厂
   * @throws IllegalStateException 如果已经初始化且不支持多次刷新尝试
   */
  void refresh() throws BeansException;

  /**
   * 向 JVM 运行时注册一个 shutdown 钩子，在 JVM 关闭时，关闭此上下文除非此时它已经关闭。
   * <p>该方法支持多次调用。只有一个 shutdown hook
   * （最大） 将为每个上下文实例注册。
   * @see java.lang.Runtime#addShutdownHook
   * @see #close（）
   */
  void registerShutdownHook();

  /**
   * 关闭此应用程序上下文，释放所有资源和锁
   * implementation 可能会成立。这包括销毁所有缓存的单例 bean。
   * <p>注意：<i>不在</i>父级上下文上调用 {@code close};
   * 父级上下文有自己独立的生命周期。
   * <p>此方法可多次调用，无副作用：后续{@code close} 对已关闭的上下文的调用将被忽略。
   */
  void close();

}