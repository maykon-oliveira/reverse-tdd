package br.com.maykon.reversetdd.usage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CalculatorTest {

    @Autowired
    private Calculator calculator;

    @Test
    void sum() {
        final var sum = calculator.sum(2, 4);

        assertEquals(6, sum);
    }
}