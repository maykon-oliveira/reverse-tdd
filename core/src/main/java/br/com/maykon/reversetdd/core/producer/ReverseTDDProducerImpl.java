package br.com.maykon.reversetdd.core.producer;

import br.com.maykon.reversetdd.core.config.ReverseTDDConfig;
import br.com.maykon.reversetdd.core.parser.ReverseTDDAnalyzer;
import br.com.maykon.reversetdd.core.queue.CommandQueue;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReverseTDDProducerImpl implements ReverseTDDProducer {
  private final ReverseTDDConfig configuration;
  private final ReverseTDDAnalyzer analyzer;
  private final CommandQueue commandQueue;

  public ReverseTDDProducerImpl(
      ReverseTDDConfig configuration, ReverseTDDAnalyzer analyzer, CommandQueue commandQueue) {
    this.configuration = configuration;
    this.analyzer = analyzer;
    this.commandQueue = commandQueue;
  }

  @Override
  public void produce(MethodSignature signature, Object[] args, Object returns) {
    final var argsList =
        Stream.of(args)
            .map(o -> new SimpleImmutableEntry<Class<?>, Object>(o.getClass(), o))
            .collect(Collectors.toUnmodifiableList());

    final var command = analyzer.createCommand(signature, argsList, returns);

    try {
      commandQueue.put(command);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
