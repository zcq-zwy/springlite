package com.zcqzwy.springframework.core.io;

import com.sun.istack.internal.Nullable;
import com.zcqzwy.springframework.util.Assert;
import com.zcqzwy.springframework.util.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: FileSystemResource </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/

public class FileSystemResource implements Resource {

  private final String path;

  private final @Nullable File file;

  private final Path filePath;


  public FileSystemResource(File file) {
    Assert.notNull(file, "File file 路径不能为空");

    this.path = StringUtils.cleanPath(file.getPath());
    this.file = file;
    this.filePath = file.toPath();
  }

  public FileSystemResource(String path) {
    Assert.notEmpty(path, "String path 路径不能为空");
    this.path = StringUtils.cleanPath(path);
    this.file = new File(path);
    this.filePath = this.file.toPath();
  }

  public FileSystemResource(FileSystem fileSystem, String path) {
     Assert.notEmpty(path, "String path 路径不能为空");
     Assert.notNull(fileSystem, "FileSystem fileSystem 路径不能为空");

    this.path = StringUtils.cleanPath(path);
    this.file = null;
    this.filePath = fileSystem.getPath(this.path).normalize();
  }

  public FileSystemResource(Path filePath) {
    Assert.notNull(filePath, "Path filePath 路径不能为空");
    this.path = StringUtils.cleanPath(filePath.toString());
    this.file = null;
    this.filePath = filePath;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    try {
      return Files.newInputStream(this.filePath);
    }
    catch (NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  public final String getPath() {
    return this.path;
  }

  @Override
  public String getDescription() {
    return "file [" + this.file.getAbsolutePath() + "]";
  }

/**
   * 此 实现返回底层文件是否存在。
   * @see java.io.File#exists（）
   */
  @Override
  public boolean exists() {
    return this.file.exists();
  }



}




