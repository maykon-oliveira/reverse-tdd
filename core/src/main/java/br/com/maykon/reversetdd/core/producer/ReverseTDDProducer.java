package br.com.maykon.reversetdd.core.producer;

import org.aspectj.lang.reflect.MethodSignature;

public interface ReverseTDDProducer {
  void produce(MethodSignature signature, Object[] args, Object returns);
}
