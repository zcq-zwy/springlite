package com.zcqzwy.springframework.core;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: Ordered </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface Ordered {

  /**
   * 最小排序号
   */
  int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

  /**
   * 最大排序号
   */
  int LOWEST_PRECEDENCE = Integer.MAX_VALUE;


  /**
   * 获取排序号，数字越小，优先级也越高
   * @return 排序号
   */
  int getOrder();

}


