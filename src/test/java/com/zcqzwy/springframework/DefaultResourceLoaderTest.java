package com.zcqzwy.springframework;

import com.zcqzwy.springframework.bean.AccountService;
import com.zcqzwy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.zcqzwy.springframework.context.support.ClassPathXmlApplicationContext;
import com.zcqzwy.springframework.core.io.DefaultResourceLoader;
import com.zcqzwy.springframework.core.io.Resource;
import com.zcqzwy.springframework.util.IoUtils;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultResourceLoaderTest </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class DefaultResourceLoaderTest {

  private DefaultResourceLoader resourceLoader;

  @BeforeEach
  public void init() {
    resourceLoader = new DefaultResourceLoader();
  }

  @Test
  public void test_classpath() throws IOException {
    Resource resource = resourceLoader.getResource("classpath:important.properties");
    InputStream inputStream = resource.getInputStream();
    String content = IoUtils.readUtf8(inputStream);
    System.out.println(content);
  }

  @Test
  public void test_file() throws IOException {
    Resource resource = resourceLoader.getResource("src/test/resource/important.properties");
    InputStream inputStream = resource.getInputStream();
    String content = IoUtils.readUtf8(inputStream);
    System.out.println(content);
  }

  @Test
  public void test_url() throws IOException {
    Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/blob/main/small-spring-step-05/src/test/resources/important.properties");
    InputStream inputStream = resource.getInputStream();
    String content = IoUtils.readUtf8(inputStream);
    System.out.println(content);
  }

  @Test
  public void test_xml() {
    // 1.初始化 BeanFactory
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    // 2. 读取配置文件&注册Bean
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions("classpath:spring.xml");

    // 3. 获取Bean对象调用方法
    AccountService accountService = beanFactory.getBean("accountService", AccountService.class);
    String result = accountService.queryAccountInfo();
    System.out.println("测试结果：" + result);
  }

  @Test
  public void test_xml1() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");
    applicationContext.registerShutdownHook();

    // 3. 获取Bean对象调用方法
    AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
    String result = accountService.queryAccountInfo();
    System.out.println("测试结果：" + result);

    System.out.println("ApplicationContextAware：" + accountService.getApplicationContext());
    System.out.println("BeanFactoryAware：" + accountService.getBeanFactory());
  }

}
