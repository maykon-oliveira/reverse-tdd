package br.com.maykon.reversetdd.usage;

import br.com.maykon.reversetdd.core.annotation.GenerateTests;
import org.springframework.stereotype.Component;

@Component
public class Calculator {
  @GenerateTests
  public int sum(int a, Integer b) {
    return a + b;
  }
}
