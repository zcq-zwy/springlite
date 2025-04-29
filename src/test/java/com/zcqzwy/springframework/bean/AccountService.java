package com.zcqzwy.springframework.bean;

import com.zcqzwy.springframework.bean.interfece.IAccountDao;
import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.BeanClassLoaderAware;
import com.zcqzwy.springframework.beans.factory.BeanFactory;
import com.zcqzwy.springframework.beans.factory.BeanFactoryAware;
import com.zcqzwy.springframework.beans.factory.BeanNameAware;
import com.zcqzwy.springframework.beans.factory.DisposableBean;
import com.zcqzwy.springframework.beans.factory.InitializingBean;
import com.zcqzwy.springframework.context.ApplicationContext;
import com.zcqzwy.springframework.context.ApplicationContextAware;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AccountService </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AccountService implements InitializingBean, DisposableBean, BeanNameAware,
    BeanClassLoaderAware,
    ApplicationContextAware, BeanFactoryAware {


  private ApplicationContext applicationContext;
  private BeanFactory beanFactory;

  private String name;

  private String aId;

  private String company;
  private String location;

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }



  public String getaId() {
    return aId;
  }

  public void setaId(String aId) {
    this.aId = aId;
  }

  private IAccountDao accountDao;

  public IAccountDao getAccountDao() {
    return accountDao;
  }

  public void setAccountDao(IAccountDao accountDao) {
    this.accountDao = accountDao;
  }

  public AccountService() {
  }

  public AccountService(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String queryAccountInfo(){

    return accountDao.queryAccountName(aId) + "-" + location + "-" + company;
  }

  public void test(){
    System.out.println("执行：Service.test");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("执行：Service.afterPropertiesSet");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("执行：Service.destroy");
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    System.out.println("ClassLoader：" + classLoader);
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public void setBeanName(String name) {
    System.out.println("Bean Name is：" + name);
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
