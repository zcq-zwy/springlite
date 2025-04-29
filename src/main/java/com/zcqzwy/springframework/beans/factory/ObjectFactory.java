package com.zcqzwy.springframework.beans.factory;

import com.zcqzwy.springframework.beans.BeansException;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ObjectFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 对象工厂，如果允许对象提前暴露，那么就可以使用 ObjectFactory，用getobject()方法获取对象。
 * getobject触发getEarlyBeanReference方法，获取对象。==> 来解决循环引用
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ObjectFactory<T> {

  T getObject() throws BeansException;
}
