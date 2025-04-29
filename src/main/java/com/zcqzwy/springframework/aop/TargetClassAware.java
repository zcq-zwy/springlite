package com.zcqzwy.springframework.aop;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: TargetClassAware </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface TargetClassAware {

/**
 * 返回实现对象后面的目标类
 * （通常是代理配置或实际代理）。
 *
 * @return 目标类，如果未知，则为 {@code null}
 */
  Class<?>[] getTargetClass();

}
