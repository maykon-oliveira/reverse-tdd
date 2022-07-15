package br.com.maykon.reversetdd.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reversetdd")
public class ReverseTDDProperties {
    private Integer queueMaxSize;

    public Integer getQueueMaxSize() {
        return queueMaxSize;
    }

    public void setQueueMaxSize(Integer queueMaxSize) {
        this.queueMaxSize = queueMaxSize;
    }
}
