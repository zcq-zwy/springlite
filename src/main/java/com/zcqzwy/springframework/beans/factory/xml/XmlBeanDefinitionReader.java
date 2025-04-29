package com.zcqzwy.springframework.beans.factory.xml;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValue;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.BeanReference;
import com.zcqzwy.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.zcqzwy.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.zcqzwy.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.zcqzwy.springframework.core.io.Resource;
import com.zcqzwy.springframework.core.io.ResourceLoader;
import com.zcqzwy.springframework.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * <p>作者： zcq</p>
 * <p>文件名称: XmlBeanDefinitionReader </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

  public static final String BEAN_ELEMENT = "bean";
  public static final String PROPERTY_ELEMENT = "property";
  public static final String ID_ATTRIBUTE = "id";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String CLASS_ATTRIBUTE = "class";
  public static final String VALUE_ATTRIBUTE = "value";
  public static final String REF_ATTRIBUTE = "ref";
  public static final String INIT_METHOD_ATTRIBUTE = "init-method";
  public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
  public static final String SCOPE_ATTRIBUTE = "scope";
  public static final String LAZYINIT_ATTRIBUTE = "lazyInit";
  public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
  public static final String COMPONENT_SCAN_ELEMENT = "component-scan";
  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
    super(registry);
  }

  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
    super(registry, resourceLoader);
  }

  @Override
  public void loadBeanDefinitions(Resource resource) throws BeansException {
    try (InputStream inputStream = resource.getInputStream()){
      doLoadBeanDefinitions(inputStream);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      throw new BeansException("IOException parsing XML document from " + resource, e);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void loadBeanDefinitions(Resource... resources) throws BeansException {
    for (Resource resource : resources) {
      loadBeanDefinitions(resource);
    }
  }

  @Override
  public void loadBeanDefinitions(String location) throws BeansException {
    ResourceLoader resourceLoader = getResourceLoader();
    Resource resource = resourceLoader.getResource(location);
    loadBeanDefinitions(resource);
  }

  @Override
  public void loadBeanDefinitions(String... locations) throws BeansException {
    for (String location : locations) {
      loadBeanDefinitions(location);
    }
  }

  private void scanPackage(String scanPath) {
    String[] basePackages = StringUtils.splitToArray(scanPath, ',');
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
    scanner.doScan(basePackages);
  }

  protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
    SAXReader reader = new SAXReader();
    Document document = reader.read(inputStream);
    Element root = document.getRootElement();

    // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
    Element componentScan = root.element(COMPONENT_SCAN_ELEMENT);
    if (null != componentScan) {
      String scanPath = componentScan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
      if (StringUtils.isEmpty(scanPath)) {
        throw new BeansException("The value of base-package attribute can not be empty or null");
      }
      scanPackage(scanPath);
    }

    List<Element> beanList = root.elements(BEAN_ELEMENT);
    for (Element bean : beanList) {

      String id = bean.attributeValue(ID_ATTRIBUTE);
      String name = bean.attributeValue(NAME_ATTRIBUTE);
      String className = bean.attributeValue(CLASS_ATTRIBUTE);
      String initMethod = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
      String destroyMethodName = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
      String beanScope = bean.attributeValue(SCOPE_ATTRIBUTE);


      String lazyInit=bean.attributeValue(LAZYINIT_ATTRIBUTE);

      // 获取 Class，方便获取类中的名称
      Class<?> clazz = Class.forName(className);
      // 优先级 id > name
      String beanName = StringUtils.isNotEmpty(id) ? id : name;
      if (StringUtils.isEmpty(beanName)) {
        beanName = StringUtils.lowerFirst(clazz.getSimpleName());
      }

      // 定义Bean
      BeanDefinition beanDefinition = new BeanDefinition(clazz);
      beanDefinition.setInitMethodName(initMethod);
      beanDefinition.setDestroyMethodName(destroyMethodName);

      beanDefinition.setLazyInit("true".equals(lazyInit));

      if (StringUtils.isNotEmpty(beanScope)) {
        beanDefinition.setScope(beanScope);
      }

      List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);
      // 读取属性并填充
      for (Element property : propertyList) {
        // 解析标签：property
        String attrName = property.attributeValue(NAME_ATTRIBUTE);
        String attrValue = property.attributeValue(VALUE_ATTRIBUTE);
        String attrRef = property.attributeValue(REF_ATTRIBUTE);
        // 获取属性值：引入对象、值对象
        Object value = StringUtils.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
        // 创建属性信息
        PropertyValue propertyValue = new PropertyValue(attrName, value);
        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
      }
      if (getRegistry().containsBeanDefinition(beanName)) {
        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
      }
      // 注册 BeanDefinition
      getRegistry().registerBeanDefinition(beanName, beanDefinition);
    }
  }

//  protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
//    Document doc = XmlUtils.readXML(inputStream);
//    Element root = doc.getDocumentElement();
//    NodeList childNodes = root.getChildNodes();
//
//
//
//
//    for (int i = 0; i < childNodes.getLength(); i++) {
//      // 判断元素
//      if (!(childNodes.item(i) instanceof Element)) {
//        continue;
//      }
//      // 判断对象
//      if (!"bean".equals(childNodes.item(i).getNodeName())) {
//        continue;
//      }
//
//      // 解析标签
//      Element bean = (Element) childNodes.item(i);
//      String id = bean.getAttribute("id");
//      String name = bean.getAttribute("name");
//      String className = bean.getAttribute("class");
//
//      // 回调方法
//      String initMethod = bean.getAttribute("init-method");
//      String destroyMethodName = bean.getAttribute("destroy-method");
//
//      //解析scope标签
//      String beanScope = bean.getAttribute("scope");
//
//
//      // 获取 Class，方便获取类中的名称
//      Class<?> clazz = Class.forName(className);
//      // 优先级 id > name
//      String beanName = StringUtils.isNotEmpty(id) ? id : name;
//      if (StringUtils.isEmpty(beanName)) {
//        beanName = StringUtils.lowerFirst(clazz.getSimpleName());
//      }
//
//      // 定义Bean
//      BeanDefinition beanDefinition = new BeanDefinition(clazz);
//
//      beanDefinition.setInitMethodName(initMethod);
//      beanDefinition.setDestroyMethodName(destroyMethodName);
//      if(StringUtils.isNotEmpty(beanScope)){
//        beanDefinition.setScope(beanScope);
//      }
//
//
//      // 读取属性并填充
//      for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
//        if (!(bean.getChildNodes().item(j) instanceof Element)) {
//          continue;
//        }
//        if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) {
//          continue;
//        }
//        // 解析标签：property
//        Element property = (Element) bean.getChildNodes().item(j);
//        String attrName = property.getAttribute("name");
//        String attrValue = property.getAttribute("value");
//        String attrRef = property.getAttribute("ref");
//        // 获取属性值：引入对象、值对象
//        Object value = StringUtils.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
//        // 创建属性信息
//        PropertyValue propertyValue = new PropertyValue(attrName, value);
//        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
//      }
//      if (getRegistry().containsBeanDefinition(beanName)) {
//        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
//      }
//      // 注册 BeanDefinition
//      getRegistry().registerBeanDefinition(beanName, beanDefinition);
//    }
//  }

}
