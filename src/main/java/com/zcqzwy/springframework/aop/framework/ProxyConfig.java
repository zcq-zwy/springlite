package com.zcqzwy.springframework.aop.framework;

import com.zcqzwy.springframework.util.Assert;
import java.io.Serializable;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ProxyConfig </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ProxyConfig implements Serializable {

  /**
   * use serialVersionUID from Spring 1.2 for interoperability
   */
  private static final long serialVersionUID = -8409359707199703185L;
  boolean opaque = false;
  boolean exposeProxy = false;
  private boolean proxyTargetClass = false;
  private boolean optimize = false;
  private boolean frozen = false;

  /**
   * 返回是否直接代理目标类以及任何接口。
   */
  public boolean isProxyTargetClass() {
    return this.proxyTargetClass;
  }

  /**
   * 设置是否直接代理目标类，而不仅仅是代理
   * 特定接口。默认值为 “false”。
   * <p>将此项设置为 “true” 以强制对 TargetSource 的公开
   * 目标类。如果该目标类是接口，则 JDK 代理将为
   * 为给定接口创建。如果该目标类是任何其他类，
   * 将为给定类创建一个 CGLIB 代理。
   * <p>注意：根据具体代理工厂的配置，
   * 如果没有接口，也将应用 proxy-target-class 行为
   * 已指定（并且未激活接口自动检测）。
   */
  public void setProxyTargetClass(boolean proxyTargetClass) {
    this.proxyTargetClass = proxyTargetClass;
  }

  /**
   * 返回代理是否应执行主动优化。
   */
  public boolean isOptimize() {
    return this.optimize;
  }

  /**
   * 设置代理是否应执行主动优化。“激进优化”的确切含义会有所不同之间，但通常会有一些权衡。
   * 默认值为 “false”。<p>例如，优化通常意味着advice更改不会在创建代理后生效。因此，优化
   * 默认处于禁用状态。优化值 “true” 可能会被忽略
   * 如果其他设置排除了优化：例如，如果 “exposeProxy”
   * 设置为 “true”，这与优化不兼容。
   */
  public void setOptimize(boolean optimize) {
    this.optimize = optimize;
  }

  /**
   * 返回此配置创建的代理是否应为
   */
  public boolean isOpaque() {
    return this.opaque;
  }

  /**
   * 设置是否应阻止此配置创建的代理从转换为
   * <p>默认值为 “false”，这意味着任何 AOP 代理都可以强制转换为
   */
  public void setOpaque(boolean opaque) {
    this.opaque = opaque;
  }

  /**
   * Return whether the AOP proxy will expose the AOP proxy for
   * each invocation.
   */
  public boolean isExposeProxy() {
    return this.exposeProxy;
  }

  /**
   * 1.暴露代理对象：当设置为 true 时，AOP 框架会将当前代理对象存储在 ThreadLocal 中，
   * 允许通过 AopContext.currentProxy() 获取该代理。
   * 2.解决自调用问题：
   * 确保目标对象内部方法通过代理调用其他方法时，AOP 通知（如事务、日志）依然生效。
   * 若直接使用 this.method()，调用会绕过代理，导致通知失效。
   * <p>默认值为 “false”，以避免不必要的额外拦截。
   * 避免不必要的性能开销（维护 ThreadLocal）和潜在副作用。大多数场景无需自调用代理方法。
   * 代理对象存储：当 exposeProxy=true，代理对象被存储在 ThreadLocal（通过 AopContext 类管理）。
   * 调用链拦截：AOP 拦截器（如 MethodInterceptor）在方法执行前将代理对象设置到 AopContext，执行后清理。
   */
  public void setExposeProxy(boolean exposeProxy) {
    this.exposeProxy = exposeProxy;
  }

   /**
   * 返回 config 是否被冻结，无法对 advice 进行更改。
   */
  public boolean isFrozen() {
    return this.frozen;
  }

  /**
   * 设置是否应冻结此配置。
   * <p>当 config 被冻结时，无法对 advice 进行更改。这是对于优化很有用，当我们不希望调用者
   * 能够在转换为 Advised 后作配置。
   */
  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

/**
   * 从另一个 config 对象复制配置。
   *
   * @param other 要从中复制配置的其他对象
   */
  public void copyFrom(ProxyConfig other) {
    Assert.notNull(other, "Other ProxyConfig object must not be null");
    this.proxyTargetClass = other.proxyTargetClass;
    this.optimize = other.optimize;
    this.exposeProxy = other.exposeProxy;
    this.frozen = other.frozen;
    this.opaque = other.opaque;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("proxyTargetClass=").append(this.proxyTargetClass).append("; ");
    sb.append("optimize=").append(this.optimize).append("; ");
    sb.append("opaque=").append(this.opaque).append("; ");
    sb.append("exposeProxy=").append(this.exposeProxy).append("; ");
    sb.append("frozen=").append(this.frozen);
    return sb.toString();
  }

}
