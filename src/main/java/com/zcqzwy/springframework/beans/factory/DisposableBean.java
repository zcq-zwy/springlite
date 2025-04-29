package com.zcqzwy.springframework.beans.factory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DisposableBean </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface DisposableBean {

  /**
   * bean摧毁的时候的回调方法
   * @throws Exception 摧毁异常
   */
  void destroy() throws Exception;

}
