package com.zcqzwy.springframework.bean;

import com.zcqzwy.springframework.bean.interfece.IAccountDao;
import com.zcqzwy.springframework.beans.factory.FactoryBean;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationHandler;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ProxyBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ProxyBeanFactory implements FactoryBean<IAccountDao> {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ProxyBeanFactory{}";
  }

  @Override
  public IAccountDao getObject() throws Exception {
    InvocationHandler handler = (proxy, method, args) -> {

      Map<String, String> hashMap = new HashMap<>();
      hashMap.put("10001", "测试");
      hashMap.put("10002", "用户");
      hashMap.put("10003", "代码");

      return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
    };
    return (IAccountDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IAccountDao.class}, handler);
  }

  @Override
  public Class<?> getObjectType() {
    return IAccountDao.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}