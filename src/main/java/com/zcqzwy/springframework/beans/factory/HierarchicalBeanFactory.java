package com.zcqzwy.springframework.beans.factory;

import com.zcqzwy.springframework.beans.factory.BeanFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: HierarchicalBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 支持容器层次结构，允许子容器委托父容器查找Bean
 * 应用场景：Spring MVC中父子容器结构（如父容器管理Service/Dao，子容器管理Controller）
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface HierarchicalBeanFactory extends BeanFactory {
}
