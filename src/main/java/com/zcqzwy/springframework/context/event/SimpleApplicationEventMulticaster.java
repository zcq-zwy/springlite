package com.zcqzwy.springframework.context.event;

import com.zcqzwy.springframework.beans.factory.BeanFactory;
import com.zcqzwy.springframework.context.ApplicationEvent;
import com.zcqzwy.springframework.context.ApplicationListener;
import java.util.concurrent.Executor;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: SimpleApplicationEventMulticaster </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

  private Executor taskExecutor;


 /**
   * 创建一个新的 SimpleApplicationEventMulticaster。
   */
  public SimpleApplicationEventMulticaster() {
  }

/**
   * 为给定的 BeanFactory 创建一个新的 SimpleApplicationEventMulticaster。
   */
  public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
    setBeanFactory(beanFactory);
  }


/**
   * 设置 TaskExecutor 来执行应用程序监听器。
   * <p>默认是 SyncTaskExecutor，同步执行监听器
   * 在调用线程中。
   * <p>考虑在此处指定一个异步 TaskExecutor，
   * 以免阻塞调用者，直到执行完所有侦听器。但是，请注意，异步
   * 执行不会参与调用方的线程上下文（类加载器、
   * transaction 关联），除非 TaskExecutor 明确支持此功能。
   */
  public void setTaskExecutor(Executor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  /**
   * Return the current TaskExecutor for this multicaster.
   */
  protected Executor getTaskExecutor() {
    return this.taskExecutor;
  }


  @Override
  @SuppressWarnings("unchecked")
  public void multicastEvent(final ApplicationEvent event) {
    for (final ApplicationListener listener : getApplicationListeners(event)) {
      Executor executor = getTaskExecutor();
      if (executor != null) {
        executor.execute(() -> listener.onApplicationEvent(event));
      }
      else {
        listener.onApplicationEvent(event);
      }
    }
  }

}
