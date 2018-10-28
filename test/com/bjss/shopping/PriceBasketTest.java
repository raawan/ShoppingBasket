package com.bjss.shopping;


import com.bjss.shopping.goods.ItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  com.bjss.shopping.goods.ItemFactory.addItem;


public class PriceBasketTest {

    private PriceBasket priceBasket;

    @BeforeEach
    public void setUp() {

        priceBasket = new PriceBasket();
    }

    @Test
    public void GIVEN__OneBread__THEN__Total_80p() {


        priceBasket.addItem(addItem("Bread"));
        assertEquals(new BigDecimal("0.80"), priceBasket.getTotal());
    }

    @Test
    public void GIVEN_2Bread__THEN__Total_1pound60p() {

        priceBasket.addItem(addItem("Bread"));
        priceBasket.addItem(addItem("Bread"));
        assertEquals(new BigDecimal("1.60"), priceBasket.getTotal());

    }

    @Test
    public void GIVEN_1Bread_AND_1Milk__THEN__2pound10p() {
        priceBasket.addItem(addItem("Bread"));
        priceBasket.addItem(addItem("Milk"));
        assertEquals(new BigDecimal("2.10"), priceBasket.getTotal());
    }
}
