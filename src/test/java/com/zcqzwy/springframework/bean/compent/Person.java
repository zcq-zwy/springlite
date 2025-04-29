package com.zcqzwy.springframework.bean.compent;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: Person </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/

import com.zcqzwy.springframework.beans.factory.DisposableBean;
import com.zcqzwy.springframework.beans.factory.InitializingBean;
import com.zcqzwy.springframework.beans.factory.annotation.Autowired;
import com.zcqzwy.springframework.beans.factory.annotation.Qualifier;
import com.zcqzwy.springframework.stereotype.Component;

/**
 * @author derekyi
 * @date 2020/11/24
 */
@Component
public class Person implements InitializingBean, DisposableBean {

  private String name;

  private int age;

  @Autowired
  @Qualifier("car")
  private Car car;

  public void customInitMethod() {
    System.out.println("I was born in the method named customInitMethod");
  }

  public void customDestroyMethod() {
    System.out.println("I died in the method named customDestroyMethod");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("I was born in the method named afterPropertiesSet");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("I died in the method named destroy");
  }

  public String getName() {
    return name;
  }


  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", car=" + car +
        '}';
  }
}
