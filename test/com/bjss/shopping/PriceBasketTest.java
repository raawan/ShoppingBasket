package com.bjss.shopping;


import com.bjss.shopping.goods.ItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PriceBasketTest {

    private PriceBasket priceBasket;

    @BeforeEach
    public void setUp() {

        priceBasket = new PriceBasket();
    }

    @Test
    public void GIVEN__OneBread__THEN__Total_80p() {


        priceBasket.addItem(ItemFactory.addItem("Bread"));
        assertEquals(new BigDecimal("0.80"), priceBasket.getTotal());
    }


}
