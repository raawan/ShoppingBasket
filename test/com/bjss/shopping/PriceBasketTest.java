package com.bjss.shopping;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PriceBasketTest {


    @Test
    public void test() {
        assertEquals(new PriceBasket().returnVal(),1);
    }
}
