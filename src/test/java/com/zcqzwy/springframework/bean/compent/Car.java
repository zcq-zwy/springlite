package com.zcqzwy.springframework.bean.compent;

import com.zcqzwy.springframework.beans.factory.Value;
import com.zcqzwy.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: Car </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
@Component
public class Car {

  private long date;

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  private int price;

  private LocalDate produceDate;

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public LocalDate getProduceDate() {
    return produceDate;
  }

  public void setProduceDate(LocalDate produceDate) {
    this.produceDate = produceDate;
  }

  @Override
  public String toString() {
    return "Car{" +
        "price=" + price +
        ", produceDate=" + produceDate +
        ", brand='" + brand + '\'' +
        '}';
  }

  @Value("${brand}")
  private String brand;

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public void init(){
    date=System.currentTimeMillis();
  }
  public void showTime(){
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
    System.out.println(date+":bean create");
  }

}
