package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.DisposableBean;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.util.StringUtils;
import java.lang.reflect.Method;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DisposableBeanAdapter </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 * 这一个适配器，是因为销毁方法有两种甚至多种方式，目前有实现接口 DisposableBean、配置信息 destroy-method，两种方式。
 * 而这两种方式的销毁动作是由 AbstractApplicationContext 在注册虚拟机钩子后看，虚拟机关闭前执行的操作动作。
 * 那么在销毁执行时不太希望还得关注都销毁那些类型的方法，它的使用上更希望是有一个统一的接口进行销毁，
 * 所以这里就新增了适配类，做统一处理。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
class DisposableBeanAdapter implements DisposableBean {

  private static final String DESTROY_METHOD_NAME = "destroy";
  private final Object bean;
  private final String beanName;
  private String destroyMethodName;

  public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
    this.bean = bean;
    this.beanName = beanName;
    this.destroyMethodName = beanDefinition.getDestroyMethodName();
  }

  @Override
  public void destroy() throws Exception {
    boolean isDisposeBean = bean instanceof DisposableBean;
    // 1. 实现接口 DisposableBean
    if (isDisposeBean) {
      ((DisposableBean) bean).destroy();
    }
    // 2. 配置信息 destroy-method {判断是为了避免二次执行销毁}
    if (StringUtils.isNotEmpty(destroyMethodName) && !(isDisposeBean && DESTROY_METHOD_NAME.equals(this.destroyMethodName))) {
      Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
      if (null == destroyMethod) {
        throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
      }
      destroyMethod.invoke(bean);
    }

  }
}
