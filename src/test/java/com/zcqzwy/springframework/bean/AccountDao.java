package com.zcqzwy.springframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AccountDao </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AccountDao {


  private String dbName;

  private  String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  private static Map<String, String> hashMap = new HashMap<>();

  public void initDataMethod(){
    System.out.println("accountDao执行：init-method");
    hashMap.put("10001", "测试");
    hashMap.put("10002", "用户");
    hashMap.put("10003", "代码");
  }

  public void destroyDataMethod(){
    System.out.println("执行：destroy-method");
    hashMap.clear();
  }

  public String queryAccountName(String aId) {
    return hashMap.get(aId) + ":" + dbName + ":" + password;
  }


}
