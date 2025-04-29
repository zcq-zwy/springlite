package com.zcqzwy.springframework.bean;

import com.zcqzwy.springframework.bean.interfece.IUserService;
import java.util.Random;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: UserService </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class UserService implements IUserService {

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String queryUserInfo() {
    try {
      Thread.sleep(new Random(1).nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("查询用户信息");
    return "test，100001，深圳";
  }

  public String register(String userName) {
    try {
      Thread.sleep(new Random(1).nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "注册用户：" + userName + " success！";
  }

  @Override
  public String toString() {
    return "UserService{" +
        "token='" + token + '\'' +
        '}';
  }
}
