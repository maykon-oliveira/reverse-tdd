package br.com.maykon.reversetdd.core.aspect;

import br.com.maykon.reversetdd.core.annotation.GenerateTests;
import br.com.maykon.reversetdd.core.producer.ReverseTDDProducer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReverseTDDAspect {
  private static final Logger logger = LoggerFactory.getLogger(ReverseTDDAspect.class);

  private final ReverseTDDProducer reverseTDDProducer;

  public ReverseTDDAspect(ReverseTDDProducer reverseTDDProducer) {
    logger.debug("ReverseTDDAspect instantiated");
    this.reverseTDDProducer = reverseTDDProducer;
  }

  @Around(value = "@annotation(generateTests)")
  public Object around(ProceedingJoinPoint pjp, GenerateTests generateTests) throws Throwable {
    Object returns = pjp.proceed();

    final var signature = pjp.getSignature();

    if (signature instanceof MethodSignature && !generateTests.isDisabled()) {
      reverseTDDProducer.produce((MethodSignature) signature, pjp.getArgs(), returns);
    } else {
      logger.warn("Annotated element isn't a method [{}]", signature);
    }

    return returns;
  }
}
