package com.zcqzwy.springframework.core.io;

import com.sun.istack.internal.Nullable;
import com.zcqzwy.springframework.util.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: UrlResource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class UrlResource implements Resource{

  private final URL url;



  public UrlResource(URL url) throws MalformedURLException {
    Assert.notNull(url,"URL must not be null");
    this.url = url;
  }




  @Override
  public InputStream getInputStream() throws IOException {
    URLConnection con = this.url.openConnection();
    try {
      return con.getInputStream();
    }
    catch (IOException ex){
      if (con instanceof HttpURLConnection){
        ((HttpURLConnection) con).disconnect();
      }
      throw ex;
    }
  }

  @Override
  public String getDescription() {
    return "URL [" + this.url + "]";
  }

  @Override
  public boolean exists() {
    return true;
  }
}
