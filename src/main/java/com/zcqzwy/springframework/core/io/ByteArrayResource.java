package com.zcqzwy.springframework.core.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ByteArrayResource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ByteArrayResource implements Resource{


  private final byte[] byteArray;

  private final String description;

/**
   * 创建一个新的 ByteArrayResource。
   * @param byteArray 要包装的字节数组
   */
  public ByteArrayResource(byte[] byteArray) {
    this(byteArray, "resource loaded from byte array");
  }

/**
   * 创建一个新的 ByteArrayResource。
   * @param byteArray 要包装的字节数组
   * @param  description 资源的描述信息
   */
  public ByteArrayResource(byte[] byteArray, String description) {
    if (byteArray == null) {
      throw new IllegalArgumentException("Byte array must not be null");
    }
    this.byteArray = byteArray;
    this.description = (description != null ? description : "");
  }

/**
   * 返回底层字节数组。
   */
  public final byte[] getByteArray() {
    return this.byteArray;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(this.byteArray);
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public boolean exists() {
    return true;
  }
}
