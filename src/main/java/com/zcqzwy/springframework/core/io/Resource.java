package com.zcqzwy.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;


/**
 * <p>作者： zcq</p>
 * <p>文件名称: Resource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * Resource接口可以根据资源的不同类型，或者资源所处的不同场合，给出相应的具体实现
 * 比如字节数组，比如classpath，比如filesystem，比如url，比如inputStream等
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface Resource extends InputStreamSource {

  final String ROOT_PATH = "/";

/**
   * 返回此资源的描述，
   * 用于使用资源时的错误输出。
   * <p>还鼓励 implementations 返回此值
   * 从他们的 {@code toString} 方法。
   * @see Object#toString（）
   * @return 此资源的描述
   */
  String getDescription();

/**
   * 确定此资源是否实际以物理形式存在。
   * <p>此方法执行确定性存在检查，而
   * 存在 {@code Resource} 句柄仅保证描述符句柄。
  * @return 如果此资源存在，则为true，否则为false
   */
  boolean exists();




}
