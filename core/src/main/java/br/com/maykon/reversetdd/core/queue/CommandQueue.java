package br.com.maykon.reversetdd.core.queue;

import br.com.maykon.reversetdd.core.creator.Command;

public interface CommandQueue {
  void put(Command command) throws InterruptedException;

  Command take() throws InterruptedException;
}
