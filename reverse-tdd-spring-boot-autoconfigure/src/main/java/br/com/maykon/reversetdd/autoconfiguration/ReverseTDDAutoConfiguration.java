package br.com.maykon.reversetdd.autoconfiguration;

import br.com.maykon.reversetdd.core.config.ReverseTDDConfig;
import br.com.maykon.reversetdd.core.creator.ReverseTDDCreator;
import br.com.maykon.reversetdd.core.creator.ReverseTDDCreatorImpl;
import br.com.maykon.reversetdd.core.parser.ReverseTDDAnalyzer;
import br.com.maykon.reversetdd.core.parser.ReverseTDDAnalyzerImpl;
import br.com.maykon.reversetdd.core.producer.ReverseTDDProducer;
import br.com.maykon.reversetdd.core.producer.ReverseTDDProducerImpl;
import br.com.maykon.reversetdd.core.queue.CommandQueue;
import br.com.maykon.reversetdd.core.queue.CommandQueueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ReverseTDDProducerImpl.class)
@EnableConfigurationProperties(ReverseTDDProperties.class)
public class ReverseTDDAutoConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(ReverseTDDAutoConfiguration.class);
  private final ReverseTDDProperties properties;

  public ReverseTDDAutoConfiguration(ReverseTDDProperties properties) {
    logger.debug("new ReverseTDDAutoConfiguration()");
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public ReverseTDDConfig reverseTDDConfig() {
    logger.debug("new ReverseTDDConfig()");
    return new ReverseTDDConfig(properties.getQueueMaxSize());
  }

  @Bean
  @ConditionalOnMissingBean
  public CommandQueue commandQueue(ReverseTDDConfig config) {
    logger.debug("new CommandQueueImpl()");
    return new CommandQueueImpl(config);
  }

  @Bean
  @ConditionalOnMissingBean
  public ReverseTDDCreator reverseTDDCreator(CommandQueue commandQueue) {
    logger.debug("new ReverseTDDCreatorImpl()");
    return new ReverseTDDCreatorImpl(commandQueue);
  }

  @Bean
  @ConditionalOnMissingBean
  public ReverseTDDAnalyzer reverseTDDParser() {
    logger.debug("new ReverseTDDAnalyzerImpl()");
    return new ReverseTDDAnalyzerImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public ReverseTDDProducer reverseTDD(
      ReverseTDDConfig config, ReverseTDDAnalyzer parser, CommandQueue commandQueue) {
    logger.debug("new ReverseTDDProducerImpl()");
    return new ReverseTDDProducerImpl(config, parser, commandQueue);
  }
}
