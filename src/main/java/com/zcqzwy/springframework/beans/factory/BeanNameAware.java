package com.zcqzwy.springframework.beans.factory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanNameAware </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface BeanNameAware extends Aware {

  void setBeanName(String name);

}
