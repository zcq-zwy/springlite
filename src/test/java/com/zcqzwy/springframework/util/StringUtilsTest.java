package com.zcqzwy.springframework.util;

import static com.zcqzwy.springframework.util.StringUtils.cleanPath;

import org.junit.jupiter.api.Test;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: StringUtilTest </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class StringUtilsTest {

  @Test
  public void testCleanPath(){
    System.out.println(cleanPath("a/b/../c"));        // a/c
    System.out.println(cleanPath("a//b"));            // a/b
    System.out.println(cleanPath("a/./b"));            // a/b
    System.out.println(cleanPath("/../a/b"));         // /a/b
    System.out.println(cleanPath("../../a"));          // ../../a
    System.out.println(cleanPath("C:\\Users\\test"));  // C:/Users/test
    System.out.println(cleanPath("//server///file"));  // /server/file
    System.out.println(cleanPath("path/with/../trailing/")); // path/trailing/
  }

}
