package com.zcqzwy.springframework.listener;

import com.zcqzwy.springframework.context.ApplicationListener;
import com.zcqzwy.springframework.event.CustomEvent;
import java.util.Date;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: CustomEventListener </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class CustomEventListener implements ApplicationListener<CustomEvent> {

  @Override
  public void onApplicationEvent(CustomEvent event) {
    System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
    System.out.println("消息：" + event.getId() + ":" + event.getMessage());
  }

}
