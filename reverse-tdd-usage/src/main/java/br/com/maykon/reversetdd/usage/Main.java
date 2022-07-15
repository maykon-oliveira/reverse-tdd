package br.com.maykon.reversetdd.usage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.maykon.reversetdd.*")
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
