package br.com.maykon.reversetdd.core.parser;

import br.com.maykon.reversetdd.core.creator.Command;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;

public class ReverseTDDAnalyzerImpl implements ReverseTDDAnalyzer {
  @Override
  public Command createCommand(
      MethodSignature signature,
      List<SimpleImmutableEntry<Class<?>, Object>> argsMap,
      Object returns) {
    throw new UnsupportedOperationException();
  }
}
