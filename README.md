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

