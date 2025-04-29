package com.zcqzwy.springframework.util;

import static com.zcqzwy.springframework.util.ClassUtils.classPackageAsResourcePath;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public  class ClassUtilsTest {

  @Test
  public void testClassPackageAsResourcePath() {
    // 标准类
    System.out.println(classPackageAsResourcePath(String.class));     // java/lang

    // 内部类
    class InnerClass {}
    System.out.println(classPackageAsResourcePath(InnerClass.class)); // 输出当前包路径，

    // 数组类型
    System.out.println(classPackageAsResourcePath(String[].class));  // java/lang

  }
  }

