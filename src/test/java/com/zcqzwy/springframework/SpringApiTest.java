package com.zcqzwy.springframework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zcqzwy.springframework.aop.TargetSource;
import com.zcqzwy.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.zcqzwy.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.zcqzwy.springframework.aop.framework.AdvisedSupport;
import com.zcqzwy.springframework.aop.framework.CglibAopProxy;
import com.zcqzwy.springframework.aop.framework.JdkDynamicAopProxy;
import com.zcqzwy.springframework.aop.framework.ProxyFactory;
import com.zcqzwy.springframework.aop.framework.SimpleTargetSource;
import com.zcqzwy.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import com.zcqzwy.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.zcqzwy.springframework.bean.ABean;
import com.zcqzwy.springframework.bean.AccountDao;
import com.zcqzwy.springframework.bean.AccountService;
import com.zcqzwy.springframework.bean.BBean;
import com.zcqzwy.springframework.bean.UserService;
import com.zcqzwy.springframework.bean.UserServiceBeforeAdvice;
import com.zcqzwy.springframework.bean.UserServiceInterceptor;
import com.zcqzwy.springframework.bean.WorldServiceAfterReturnAdvice;
import com.zcqzwy.springframework.bean.compent.Car;
import com.zcqzwy.springframework.bean.compent.Person;
import com.zcqzwy.springframework.bean.interfece.IAccountDao;
import com.zcqzwy.springframework.bean.interfece.IUserService;
import com.zcqzwy.springframework.beans.PropertyValue;
import com.zcqzwy.springframework.beans.PropertyValues;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.BeanReference;
import com.zcqzwy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.zcqzwy.springframework.context.support.ClassPathXmlApplicationContext;
import com.zcqzwy.springframework.event.CustomEvent;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: SpringApiTest </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class SpringApiTest {

  @Test
  public void test_BeanFactory() {
    // 1.初始化 BeanFactory
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 2.注册 bean
    BeanDefinition beanDefinition = new BeanDefinition(AccountService.class);
    beanFactory.registerBeanDefinition("AccountService", beanDefinition);
    // 3.第一次获取 bean
    AccountService accountService = (AccountService) beanFactory.getBean("AccountService", "zcq");
    accountService.queryAccountInfo();
    // 4.第二次获取 bean from Singleton
    AccountService accountService_singleton = (AccountService) beanFactory.getBean(
        "AccountService");
    assertEquals(accountService, accountService_singleton, "不是同一个 bean");
    accountService_singleton.queryAccountInfo();
  }


  @Test
  public void test_BeanFactory_2() {
    // 1.初始化 BeanFactory
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    // 2. AccountDao 注册
    beanFactory.registerBeanDefinition("AccountDao", new BeanDefinition(AccountDao.class));

    // 3. AccountService 设置属性[aId、AccountDao]
    PropertyValues propertyValues = new PropertyValues();
    propertyValues.addPropertyValue(new PropertyValue("aId", "10001"));
    propertyValues.addPropertyValue(
        new PropertyValue("accountDao", new BeanReference("AccountDao")));

    // 4. AccountService 注入bean
    BeanDefinition beanDefinition = new BeanDefinition(AccountService.class, propertyValues);
    beanFactory.registerBeanDefinition("AccountService", beanDefinition);

    // 5. AccountService 获取bean
    AccountService accountService = (AccountService) beanFactory.getBean("AccountService");
    accountService.queryAccountInfo();

    String s = accountService.getAccountDao().queryAccountName("10001");

    System.out.println(s);

    assertEquals("测试", s, "错误，没有注入accountDao属性");


  }

  @Test
  public void test_prototype() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");
    applicationContext.registerShutdownHook();

    // 2. 获取Bean对象调用方法
    AccountService AccountService01 = applicationContext.getBean("accountService",
        AccountService.class);
    AccountService AccountService02 = applicationContext.getBean("accountService",
        AccountService.class);

    // 3. 配置 scope="prototype/singleton"
    System.out.println(AccountService01);
    System.out.println(AccountService02);

    // 4. 打印十六进制哈希
    System.out.println(
        AccountService01 + " 十六进制哈希：" + Integer.toHexString(AccountService01.hashCode()));
    System.out.println(ClassLayout.parseInstance(AccountService01).toPrintable());

    IAccountDao accountDao = applicationContext.getBean("proxyAccountDaoFactory",
        IAccountDao.class);

    System.out.println(accountDao.queryAccountName("10001"));

    Object bean = applicationContext.getBean("&proxyAccountDaoFactory");
    System.out.println(bean);


  }

  @Test
  public void test_event() {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springpostprocessor.xml");
    applicationContext.registerShutdownHook();
    applicationContext.publishEvent(new CustomEvent(applicationContext, 2928235428L, "成功！"));

  }

  @Test
  public void test_dynamic() throws Exception {
    // 目标对象
    IUserService userService = new UserService();

    // 组装代理信息
    AdvisedSupport advisedSupport = new AdvisedSupport();
    advisedSupport.setTargetSource(new SimpleTargetSource(userService));
    advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
    advisedSupport.setMethodMatcher(new AspectJExpressionPointcut(
        "execution(* com.zcqzwy.springframework.bean.interfece.IUserService.*(..))"));

    // 代理对象(JdkDynamicAopProxy)
    IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
    // 测试调用
    System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

    // 代理对象(Cglib2AopProxy)
    IUserService proxy_cglib = (IUserService) new CglibAopProxy(advisedSupport).getProxy();
    // 测试调用
    System.out.println("测试结果：" + proxy_cglib.register("花花"));
  }

  @Test
  public void test_aop() {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");

    applicationContext.registerShutdownHook();
    applicationContext.publishEvent(new CustomEvent(applicationContext, 2928235428L, "成功！"));

    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService.queryUserInfo());
  }


  @Test
  public void testScanPackage() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");

    Car car = applicationContext.getBean("car", Car.class);
    System.out.println(car);
    Car car1 = applicationContext.getBean("car1", Car.class);
    System.out.println(car1);
  }

  @Test
  public void testValueAnnotation() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");

    Car car = applicationContext.getBean("car", Car.class);
    System.out.println(car);
    Car car1 = applicationContext.getBean("car1", Car.class);
    System.out.println(car1);
  }


  @Test
  public void testAutowiredAnnotation() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");
    applicationContext.registerShutdownHook();
    applicationContext.publishEvent(new CustomEvent(applicationContext, 2928235428L, "成功！"));
    Person person = applicationContext.getBean(Person.class);
    System.out.println(person);

    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService.queryUserInfo());
    System.out.println(userService);
  }

  @Test
  public void testConversionService() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");

    Car car = applicationContext.getBean("car1", Car.class);

    System.out.println(car.getPrice());
    System.out.println(car.getProduceDate());

    System.out.println(car);
  }



  @Test
  public void testCircularReferenceProxy() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springproxy.xml");
    ABean a = applicationContext.getBean("a", ABean.class);
    BBean b = applicationContext.getBean("b", BBean.class);
    System.out.println(b.getA() != a);
  }

  @Test
  public void test_this(){
    AdvisedSupport advisedSupport = new AdvisedSupport();
    System.out.println(advisedSupport.isProxyTargetClass());
    ABean aBean = new ABean();
    Class<?>[] interfaces = aBean.getClass().getInterfaces();
    System.out.println(interfaces.length);
  }

  @Test
  public void testAdvisor() throws Exception {
    IUserService worldService = new UserService();

    //Advisor是Pointcut和Advice的组合
    String expression = "execution(* com.zcqzwy.springframework.bean.interfece.IUserService.queryUserInfo(..))";
    AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
    advisor.setExpression(expression);
    MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new UserServiceBeforeAdvice());
    advisor.setAdvice(methodInterceptor);
    AspectJExpressionPointcutAdvisor advisor1=new AspectJExpressionPointcutAdvisor();
    advisor1.setExpression(expression);
    AfterReturningAdviceInterceptor afterReturningAdviceInterceptor=new AfterReturningAdviceInterceptor(new WorldServiceAfterReturnAdvice());
    advisor1.setAdvice(afterReturningAdviceInterceptor);
    ProxyFactory factory = new ProxyFactory();
    TargetSource targetSource = new SimpleTargetSource(worldService);
    factory.setTargetSource(targetSource);
    factory.setProxyTargetClass(true);
    factory.addAdvisor(advisor);
    factory.addAdvisor(advisor1);
    IUserService proxy = (IUserService) factory.getProxy();
    proxy.queryUserInfo();
  }


  @Test
  public void testLazyInit() throws InterruptedException {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:springproxy.xml");
    System.out.println(System.currentTimeMillis()+":applicationContext-over");
    applicationContext.registerShutdownHook();
    applicationContext.publishEvent(new CustomEvent(applicationContext, 2928235428L, "成功！"));
    TimeUnit.SECONDS.sleep(1);
    Car car= (Car) applicationContext.getBean("car1");
    System.out.println(car.getPrice());
    System.out.println(car.getProduceDate());

    System.out.println(car);
    car.showTime();
  }


}









