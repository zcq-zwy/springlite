package com.zcqzwy.springframework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: IoUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class IoUtils {

  private IoUtils() {
    // 禁止实例化
  }

  /**
   * 将输入流内容读取为UTF-8字符串（自动关闭流）
   * @param inputStream 输入流（允许为null，返回空字符串）
   * @return UTF-8编码的字符串内容
   * @throws UncheckedIOException 如果读取失败
   */
  public static String readUtf8(InputStream inputStream) {
    if (inputStream == null) {
      return "";
    }
    try (InputStream is = inputStream;
        ByteArrayOutputStream result = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = is.read(buffer)) != -1) {
        result.write(buffer, 0, bytesRead);
      }
      return result.toString(StandardCharsets.UTF_8.name());
    } catch (IOException ex) {
      throw new UncheckedIOException("Read failed", ex);
    }
  }


}
