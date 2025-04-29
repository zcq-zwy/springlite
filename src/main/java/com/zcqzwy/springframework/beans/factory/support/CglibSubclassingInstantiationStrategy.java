package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import java.lang.reflect.Constructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: CglibSubclassingInstantiationStrategy </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/


public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

  @Override
  public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(beanDefinition.getBeanClass());
    //NoOp.class 是 Cglib 中提供的一个空类，它的唯一方法是无操作，不执行任何操作。
    //所以 这里设置回调为 NoOp.class，表示不执行任何操作。
    enhancer.setCallback(new NoOp() {
      @Override
      public int hashCode() {
        return super.hashCode();
      }
    });
    if (null == ctor) {
      return enhancer.create();
    }
    return enhancer.create(ctor.getParameterTypes(), args);
  }

}
