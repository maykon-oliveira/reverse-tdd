package br.com.maykon.reversetdd.usage;

import br.com.maykon.reversetdd.core.ReverseTDD;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
  private final ReverseTDD reverseTDD;

  public Main(ReverseTDD reverseTDD) {
    this.reverseTDD = reverseTDD;
  }

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Subiu");
  }
}
