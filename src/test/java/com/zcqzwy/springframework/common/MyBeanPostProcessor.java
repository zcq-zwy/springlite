package com.zcqzwy.springframework.common;

import com.zcqzwy.springframework.bean.AccountService;
import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.BeanPostProcessor;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: MyBeanPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class MyBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if ("accountService".equals(beanName)) {
      AccountService accountService = (AccountService) bean;
      accountService.setLocation("北京");
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if ("userService".equals(beanName)) {
      System.out.println("userService---->postProcessAfterInitialization");
    }
    return bean;
  }

}
