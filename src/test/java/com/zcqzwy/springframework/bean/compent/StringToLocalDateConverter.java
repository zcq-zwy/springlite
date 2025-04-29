package com.zcqzwy.springframework.bean.compent;

import com.zcqzwy.springframework.core.converter.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: StringToLocalDateConverter </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

  private final DateTimeFormatter DATE_TIME_FORMATTER;

  public StringToLocalDateConverter(String pattern) {
    DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);
  }

  @Override
  public LocalDate convert(String source) {
    return LocalDate.parse(source, DATE_TIME_FORMATTER);
  }
}
