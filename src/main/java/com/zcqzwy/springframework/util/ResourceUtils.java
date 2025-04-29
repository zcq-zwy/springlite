package com.zcqzwy.springframework.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ResourceUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ResourceUtils {

  /**
   * 将资源位置字符串转换为URL（支持classpath:、file:、http:等协议）
   * @param location 资源位置（如 "classpath:config.xml", "file:/data/app.conf"）
   * @return 对应的URL对象
   * @throws IllegalArgumentException 如果位置无效或资源不存在
   */
  public static URL toURL(String location) {
    if (location == null || location.isEmpty()) {
      throw new IllegalArgumentException("资源位置不能为空");
    }

    // 处理classpath:协议
    if (location.startsWith("classpath:")) {
      String path = location.substring("classpath:".length());
      return getClassLoader().getResource(path);
    }

    try {
      // 尝试直接解析为标准URL（如http://、file:、jar:等）
      return new URL(location);
    } catch (MalformedURLException ex) {
      // 如果失败，尝试作为文件系统路径处理
      try {
        return new File(location).getAbsoluteFile().toURI().toURL();
      } catch (MalformedURLException e) {
        throw new IllegalArgumentException("无效的资源位置: " + location, e);
      }
    }
  }

  /**
   * 获取当前线程的ClassLoader（安全方式）
   */
  private static ClassLoader getClassLoader() {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    if (cl == null) {
      cl = ResourceUtils.class.getClassLoader();
    }
    return cl;
  }

}
