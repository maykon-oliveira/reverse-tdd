package br.com.maykon.reversetdd.core.queue;

import br.com.maykon.reversetdd.core.config.ReverseTDDConfig;
import br.com.maykon.reversetdd.core.creator.Command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandQueueImpl implements CommandQueue {
  private final BlockingQueue<Command> queue;

  public CommandQueueImpl(ReverseTDDConfig configuration) {
    this.queue = new LinkedBlockingDeque<>(configuration.queueMaxSize);
  }

  @Override
  public void put(Command command) throws InterruptedException {
    this.queue.put(command);
  }

  @Override
  public Command take() throws InterruptedException {
    return this.queue.take();
  }
}
