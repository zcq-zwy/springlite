package com.zcqzwy.springframework.core.io;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: FileSystemResourceLoader </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class FileSystemResourceLoader extends DefaultResourceLoader{

  @Override
  protected Resource getResourceByPath(String path) {
    if (path.startsWith(ROOT_PATH)) {
      path = path.substring(1);
    }
    return new FileSystemResource(path);
  }

}
