package com.zcqzwy.springframework.context;

import com.zcqzwy.springframework.beans.factory.HierarchicalBeanFactory;
import com.zcqzwy.springframework.beans.factory.ListableBeanFactory;
import com.zcqzwy.springframework.core.io.ResourceLoader;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationContext </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 * 中央接口为应用程序提供配置Spring 应用上下文，仅可读
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory,
    ResourceLoader, ApplicationEventPublisher {
}
