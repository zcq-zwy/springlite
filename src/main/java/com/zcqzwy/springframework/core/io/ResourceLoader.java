package com.zcqzwy.springframework.core.io;




/**
 * <p>作者： zcq</p>
 * <p>文件名称: ResourceLoader </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * ResourceLoader接口是资源查找定位策略的统一抽象，具体的资源查找定
 * 位策略则由相应的ResourceLoader实现类给出 也就是ResourceLoader是1个统一资源定位器
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ResourceLoader {

  /**
   * 用于从类路径加载的伪 URL 前缀：“classpath：”
   */
  String CLASSPATH_URL_PREFIX = "classpath:";

  String ROOT_PATH = "/";


  ClassLoader getClassLoader();

  Resource getResource(String location);

}
