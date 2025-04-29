package com.zcqzwy.springframework.core.io;


import com.zcqzwy.springframework.util.Assert;
import com.zcqzwy.springframework.util.ClassUtils;
import com.zcqzwy.springframework.util.ResourceUtils;
import java.net.MalformedURLException;
import java.net.URL;
import org.jspecify.annotations.Nullable;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultResourceLoader </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class DefaultResourceLoader implements ResourceLoader {

  private @Nullable ClassLoader classLoader;




  public DefaultResourceLoader() {
  }

  public DefaultResourceLoader(@Nullable ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  public void setClassLoader(@Nullable ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public @Nullable ClassLoader getClassLoader() {
    return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
  }


  @Override
  public Resource getResource(String location) {
    Assert.notNull(location, "Location must not be null");

    if (location.startsWith(CLASSPATH_URL_PREFIX)) {
      return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()),
          getClassLoader());
    } else {
      URL url = ResourceUtils.toURL(location);
      try {
        return  new UrlResource(url);
      } catch (MalformedURLException e) {
        return getResourceByPath(location);
      }
    }
  }

  protected Resource getResourceByPath(String path) {
    return new ClassPathResource(path, getClassLoader());
  }


}
