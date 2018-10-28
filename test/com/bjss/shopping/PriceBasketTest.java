package com.bjss.shopping;


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
    public void GIVEN__OneBread__THEN__SubTotal_80p() {


        priceBasket.addItem(addItem("Bread"));
        assertEquals(new BigDecimal("0.80"), priceBasket.getSubTotal());
    }

    @Test
    public void GIVEN__2Bread__THEN__SubTotal_1pound60p() {

        priceBasket.addItem(addItem("Bread"));
        priceBasket.addItem(addItem("Bread"));
        assertEquals(new BigDecimal("1.60"), priceBasket.getSubTotal());

    }

    @Test
    public void GIVEN__1Bread_AND_1Milk__THEN__SubTotal__2pound10p() {
        priceBasket.addItem(addItem("Bread"));
        priceBasket.addItem(addItem("Milk"));
        assertEquals(new BigDecimal("2.10"), priceBasket.getSubTotal());
    }

    @Test
    public void GIVEN__1Bread_1Soup_1Milk_1Apples__THEN__Subtotal() {
        priceBasket.addItem(addItem("Bread"));
        priceBasket.addItem(addItem("Soup"));
        priceBasket.addItem(addItem("Milk"));
        priceBasket.addItem(addItem("Apple"));
        assertEquals(new BigDecimal("3.75"), priceBasket.getSubTotal());
    }

    @Test
    public void GIVEN__1Apples__THEN__DiscountedPrice_0pound90pence() {
        priceBasket.addItem(addItem("Apple"));
        assertEquals(new BigDecimal("0.90"), priceBasket.getTotal());
    }

    @Test
    public void GIVEN__2Apples__THEN__DiscountedTotal_1pound80p() {
        priceBasket.addItem(addItem("Apple"));
        priceBasket.addItem(addItem("Apple"));
        assertEquals(new BigDecimal("1.80"), priceBasket.getTotal());
    }

    @Test
    public void GIVEN__2Soups_1Bread__THEN__DiscountedTotal_1pound70pence() {
        priceBasket.addItem(addItem("Soup"));
        priceBasket.addItem(addItem("Soup"));
        priceBasket.addItem(addItem("Bread"));
        assertEquals(new BigDecimal("1.70"), priceBasket.getTotal());
    }
}
