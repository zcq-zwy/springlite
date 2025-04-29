package com.zcqzwy.springframework.aop;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: PointcutAdvisor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface PointcutAdvisor extends Advisor {


  /**
   * 获取驱动此advisor的 Pointcut。
   * @return Pointcut是连接点的表达式
   */
  Pointcut getPointcut();
}