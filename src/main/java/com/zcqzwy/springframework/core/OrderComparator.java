package com.zcqzwy.springframework.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: OrderComparator </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class OrderComparator implements Comparator<Object> {

  /**
   * 共享 OrderComparator 的默认实例。
   */
  public static final OrderComparator INSTANCE = new OrderComparator();


  @Override
  public int compare(Object o1, Object o2) {
    boolean p1 = (o1 instanceof PriorityOrdered);
    boolean p2 = (o2 instanceof PriorityOrdered);
    if (p1 && !p2) {
      return -1;
    }
    else if (p2 && !p1) {
      return 1;
    }

    // Direct evaluation instead of Integer.compareTo to avoid unnecessary object creation.
    int i1 = getOrder(o1);
    int i2 = getOrder(o2);
    return Integer.compare(i1, i2);
  }

/**
   * 确定给定对象的 order 值。
   * <p>默认实现会根据 {@link Ordered} 进行检查
   *接口。可以在子类中覆盖。
   * @param obj 要检查的对象
   * @return order 值，或 {@code Ordered.LOWEST_PRECEDENCE} 作为后备
   */
  protected int getOrder(Object obj) {
    return (obj instanceof Ordered ? ((Ordered) obj).getOrder() : Ordered.LOWEST_PRECEDENCE);
  }


/**
   * 使用默认的 OrderComparator 对给定的 List 进行排序。
   * <p>优化跳过大小为 0 或 1 的列表的排序，
   * 以避免不必要的数组提取。
   * @param  list 列出要排序的列表
   * @see java.util.Collections#sort（java.util.List， java.util.Comparator）
   */
  public static void sort(List<?> list) {
    if (list.size() > 1) {
      list.sort(INSTANCE);
    }
  }

  /**
   * Sort the given array with a default OrderComparator.
   * <p>Optimized to skip sorting for lists with size 0 or 1,
   * in order to avoid unnecessary array extraction.
   * @param array the array to sort
   * @see java.util.Arrays#sort(Object[], java.util.Comparator)
   */
  public static void sort(Object[] array) {
    if (array.length > 1) {
      Arrays.sort(array, INSTANCE);
    }
  }

}
