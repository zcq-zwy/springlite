package com.zcqzwy.springframework.aop;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: TargetSource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 * A {@code TargetSource} 用于获取
 一个 {@code TargetSource} 用于获取 AOP 调用的当前 “目标”（target），
 如果没有任何环绕通知（around advice）选择自行终止拦截器链，则该目标将通过反射调用。
 如果一个 {@code TargetSource} 是 “静态的”（static），它将始终返回相同的 target，
 这允许 AOP 框架进行优化。动态 target source 可以支持池化、热交换等功能。
 应用开发者通常不需要直接与 {@code TargetSources} 打交道：这是一个 AOP 框架接口。
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface TargetSource extends TargetClassAware {

  /**
   * 返回此 {@link TargetSource} 返回的目标类型。
   * <p>可以返回 {@code null}，尽管{@code TargetSource} 可能只适用于预先确定的目标类。
   *
   * @return 此 {@link TargetSource} 返回的目标类型
   */
  @Override
  Class<?>[] getTargetClass();

  /**
   * 对 {@link #getTarget（）} 的所有调用都会返回相同的对象吗？<p>在这种情况下，无需调用
   * {@link #releaseTarget（Object）}，AOP 框架可以缓存{@link #getTarget（）} 的返回值。
   *
   * @return {@code true} 如果目标是不可变的
   * @see #getTarget
   */
  boolean isStatic();

  /**
   * 返回目标实例。在AOP 框架调用 AOP 方法调用的 “target”。
   *
   * @return 目标对象，其中包含 joinpoint
   * @throws Exception 如果无法解析目标对象，则出现异常
   */
  Object getTarget() throws Exception;

  /**
   * 释放从{@link #getTarget（）} 方法。
   *
   * @param target 通过调用 {@link #getTarget（）} 获取的目标对象
   * @throws Exception 如果对象无法释放，则出现异常
   */
  void releaseTarget(Object target) throws Exception;

}
