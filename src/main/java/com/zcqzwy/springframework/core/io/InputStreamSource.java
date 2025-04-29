package com.zcqzwy.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: InputStreamSource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
@FunctionalInterface
public interface InputStreamSource {

/**
   * 返回底层资源内容的 {@link InputStream}。
   * <p>通常预计每个此类调用都会创建一个新的流。<i></i>
   * <p>当您将 API 视为
   * 作为 JavaMail，它需要能够在创建邮件附件。对于此类用例，<i>它是必需的</i>
   * 每个 {@code getInputStream（）} 调用都会返回一个新鲜的流。
   * @return 底层资源的输入流（不得为 {@code null}）
   * 如果底层资源不存在，则@throws java.io.FileNotFoundException
   * 如果无法打开内容流，则@throws IOException
   */
  InputStream getInputStream() throws IOException;

}
