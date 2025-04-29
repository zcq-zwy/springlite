## 1.功能架构与核心模块
## 1.1 IoC 容器核心
### 1.1.1 Bean 定义体系

1.BeanDefinition 元数据模型：封装类名、作用域、初始化/销毁方法等核心元数据，构建了Bean

BeanDefinitionRegistry 注册中心：实现 Bean 定义的存储、查询与管理，是对Bean 元数据操作。

### 1.1.2 对象实例化策略

InstantiationStrategy 实例化策略：支持 Cglib 动态增强与 JDK 标准反射两种实例化模式，可扩展策略应对复杂对象构建场景。

### 1.1.3 依赖注入体系

1.属性填充与 Bean 注入:递归处理基础类型属性注入与对象间依赖关系，模拟 Spring 属性解析器的多层级注入逻辑。

2.循环依赖防御机制通过三级缓存（singletonFactories、earlySingletonObjects、singletonObjects）破解循环依赖，还原 Spring 经典解决方案。

### 1.1.4 资源抽象层

1.Resource 资源协议化统一抽象类路径资源、文件系统资源、URL 资源的访问接口，实现资源定位标准化。

2.XmlBeanDefinitionReader XML 解析器基于 DOM4J 的 Bean 定义解析器，支持 <bean>、<property> 等基础标签的元数据提取。

### 1.1.5 容器扩展机制

1.工厂后置处理器:BeanFactoryPostProcessor 容器级扩展在 Bean 定义加载后、实例化前对元数据进行全局修改（如占位符替换）还要aop处理，代理对象间的循环
依赖就是在这里处理的。

2.BeanPostProcessor Bean 级增强拦截 Bean 初始化过程，支持前置处理（postProcessBeforeInitialization）与后置增强（postProcessAfterInitialization）。

3.生命周期回调：init-method/destroy-method 生命周期钩子通过反射机制执行自定义初始化/销毁方法，支持 XML 配置

4.Aware 体系感知接口实现 BeanNameAware、BeanFactoryAware 等感知接口

### 1.1.6 作用域扩展

1.Singleton/Prototype 双作用域内置单例池（singletonObjects）管理单例 Bean，原型作用域实现按需创建的全新实例。

2.工厂模式支持FactoryBean 工厂扩展点。通过 getObject() 实现复杂对象的延迟构建，区分工厂 Bean 与普通 Bean 的获取逻辑。

### 1.1.7 应用上下文（ApplicationContext）

1.实现了上下文核心职责，整合资源加载、Bean 定义解析、依赖注入流程，提供 refresh() 核心启动入口。

2.实现 ListableBeanFactory、HierarchicalBeanFactory 等容器接口。

### 1.1.8 事件驱动模型

1.ApplicationEvent 事件体系定义容器启动事件、Bean 生命周期事件等标准事件类型。

2.ApplicationListener 监听机制基于观察者模式实现事件的发布-订阅，支持同步/异步事件处理策略。

### 1.2 AOP 框架

### 1.2.1 支持springaop核心切面组件

1.切点（Pointcut）：通过表达式定义方法级拦截规则（暂支持 execution 表达式）。

2.通知（Advice）实现前置通知（Before）、后置通知（After）基础增强类型。

3.代理链执行：用责任链模式，将多个切面按优先级排序，形成可扩展的拦截器链，精准还原 Spring AOP 的链式执行逻辑。


### 3.spring-lite的bean生命周期蓝图

![spring bean 生命周期](https://github.com/user-attachments/assets/2d95527e-765d-40b5-8ae6-4865794df1db)



![Spring Bean 的生命周期](https://github.com/user-attachments/assets/a11df858-6989-4855-b3e2-f459a88d257b)

### 4.springlite的aop责任链

![image](https://github.com/user-attachments/assets/7541096d-fdd8-4bee-a232-7e8c4d5dc0ea)


### 5. spring-lite如何解决循环依赖

### 5.1 getbean流程

1。singletonObjects	一级缓存，key为Bean名称，value为Bean实例。这里的Bean实例指的是已经完全创建好的，即已经经历实例化->属性填充->初始化以及各种后置处理过程的Bean，可直接使用。
2.earlySingletonObjects	二级缓存，key为Bean名称，value为Bean实例。这里的Bean实例指的是仅完成实例化的Bean，还未进行属性填充等后续操作。用于提前曝光，供别的Bean引用，解决循环依赖。这里存储的就是提前暴露的bean，如果需要提前引用，也就是aop。那么这里存的就是提前aop的对象，否则就是普通对象bean
3.singletonFactories	三级缓存，key为Bean名称，value为Bean工厂。在Bean实例化后，属性填充之前，如果允许提前曝光，Spring会把该Bean转换成Bean工厂并加入到三级缓存。在需要引用提前曝光对象时再通过工厂对象的getObject()方法获取。这里通过后置处理器方法可以让它提前aop


![image](https://github.com/user-attachments/assets/a8bd0a34-5b94-48b3-b3af-78653149f361)



### 5.2 循环依赖的解决


![image](https://github.com/user-attachments/assets/4f8809ab-5a14-4eca-998f-4fbed0c2a3b7)






