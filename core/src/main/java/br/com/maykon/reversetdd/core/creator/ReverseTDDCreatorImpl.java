package br.com.maykon.reversetdd.core.creator;

import br.com.maykon.reversetdd.core.queue.CommandQueue;
import org.springframework.beans.factory.DisposableBean;

public class ReverseTDDCreatorImpl implements ReverseTDDCreator, Runnable, DisposableBean {
  private final Thread thread;
  private final CommandQueue commandQueue;
  private volatile boolean runFlag = true;

  public ReverseTDDCreatorImpl(CommandQueue commandQueue) {
    this.commandQueue = commandQueue;
    this.thread = new Thread(this);
    this.thread.start();
  }

  @Override
  public void run() {
    this.consume();
  }

  private void consume() {
    while (runFlag) {
      Command command;
      try {
        command = commandQueue.take();
      } catch (InterruptedException e) {
        break;
      }

      System.out.println(command);
    }
  }

  @Override
  public void destroy() {
    this.runFlag = false;
  }
}
