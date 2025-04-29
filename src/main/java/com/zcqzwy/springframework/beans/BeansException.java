package com.zcqzwy.springframework.beans;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeansException </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class BeansException extends RuntimeException {

  public BeansException(String msg) {
    super(msg);
  }

  public BeansException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
