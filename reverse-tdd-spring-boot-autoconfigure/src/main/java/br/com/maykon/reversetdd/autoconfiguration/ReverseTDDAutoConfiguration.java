package br.com.maykon.reversetdd.autoconfiguration;

import br.com.maykon.reversetdd.core.ReverseTDD;
import br.com.maykon.reversetdd.core.config.ReverseTDDConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ReverseTDD.class)
@EnableConfigurationProperties(ReverseTDDProperties.class)
public class ReverseTDDAutoConfiguration {
  private final ReverseTDDProperties greeterProperties;

  public ReverseTDDAutoConfiguration(ReverseTDDProperties properties) {
    this.greeterProperties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public ReverseTDDConfig reverseTDDConfig() {
    return new ReverseTDDConfig();
  }

  @Bean
  @ConditionalOnMissingBean
  public ReverseTDD greeter(ReverseTDDConfig config) {
    return new ReverseTDD(config);
  }
}
