package com.zcqzwy.springframework.listener;

import com.zcqzwy.springframework.context.ApplicationListener;
import com.zcqzwy.springframework.context.event.ContextClosedEvent;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ContextClosedEventListener </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    System.out.println("关闭事件：" + this.getClass().getName());
  }

}
