package com.zcqzwy.springframework.beans.factory.config;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanReference </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *  以抽象的方式暴露了对bean名称的引用
 * 并不一定意味着对实际bean实例的引用；它只是表达了对bean名称的逻辑引用。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class BeanReference {
  private final String beanName;

  public BeanReference(String beanName) {
    this.beanName = beanName;
  }

  public String getBeanName() {
    return beanName;
  }

}
