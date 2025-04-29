package com.zcqzwy.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: Advisor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface Advisor {

/**
   * 返回此方面的Advice部分。建议可以是
   * interceptor、a before advice、a throws advice 等。
   * @return 切入点匹配时应适用的建议
   */
  Advice getAdvice();

}
