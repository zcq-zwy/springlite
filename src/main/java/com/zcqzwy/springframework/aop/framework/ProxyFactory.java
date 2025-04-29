package com.zcqzwy.springframework.aop.framework;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ProxyFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ProxyFactory extends  AdvisedSupport {

  public ProxyFactory() {
  }

  public Object getProxy() throws Exception {
    return createAopProxy().getProxy();
  }

  private AopProxy createAopProxy() {
    Class<?>[] targetClass = this.getTargetSource().getTargetClass();
    int interfaceslength = 0;
    if(targetClass.length > 0) {
      interfaceslength = targetClass.getClass().getInterfaces().length;
    }

    if (this.isProxyTargetClass() || interfaceslength == 0 ) {
      return new CglibAopProxy(this);
    }

    return new JdkDynamicAopProxy(this);
  }
}
