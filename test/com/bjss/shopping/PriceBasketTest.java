package com.bjss.shopping;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.bjss.shopping.goods.ItemFactory.addItem;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PriceBasketTest {

    private PriceBasket priceBasket;

    @BeforeEach
    public void setUp() {

        priceBasket = new PriceBasket();
    }

    @Test
    public void GIVEN__OneBread__THEN__SubTotal_80p() {

        addItemInBasket("Bread");
        assertEquals(new BigDecimal("0.80"), priceBasket.getSubTotal());
    }


    @Test
    public void GIVEN__2Bread__THEN__SubTotal_1pound60p() {

        addItemInBasket("Bread", "Bread");
        assertEquals(new BigDecimal("1.60"), priceBasket.getSubTotal());
    }

    @Test
    public void GIVEN__1Bread_AND_1Milk__THEN__SubTotal__2pound10p() {

        addItemInBasket("Bread", "Milk");
        assertEquals(new BigDecimal("2.10"), priceBasket.getSubTotal());
    }

    @Test
    public void GIVEN__1Bread_1Soup_1Milk_1Apples__THEN__Subtotal() {

        addItemInBasket("Bread", "Soup", "Milk", "Apple");
        assertEquals(new BigDecimal("3.75"), priceBasket.getSubTotal());
    }

    @Test
    public void GIVEN__1Apples__THEN__DiscountedPrice_0pound90pence() {

        addItemInBasket("Apple");
        assertEquals(new BigDecimal("0.90"), priceBasket.getTotal());
    }

    @Test
    public void GIVEN__2Apples__THEN__DiscountedTotal_1pound80p() {

        addItemInBasket("Apple", "Apple");
        assertEquals(new BigDecimal("1.80"), priceBasket.getTotal());
    }

    @Test
    public void GIVEN__2Soups_1Bread__THEN__DiscountedTotal_1pound70pence() {

        addItemInBasket("Soup", "Soup", "Bread");
        assertEquals(new BigDecimal("1.70"), priceBasket.getTotal());
    }

    @Test
    public void GIVEN__5Soups_2Bread__THEN__DiscountedTotal_4pound5p() {

        addItemInBasket("Soup", "Soup", "Soup", "Bread", "Soup", "Soup", "Bread");
        assertEquals(new BigDecimal("4.05"), priceBasket.getTotal());
        priceBasket.print();
    }

    @Test
    public void GIVEN__1Apple__2Soup_1Bread__THEN_DiscountedTotal__2pound60p() {

        addItemInBasket("Apple", "Soup", "Soup", "Bread");
        assertEquals(new BigDecimal("2.60"), priceBasket.getTotal());
        priceBasket.print();
    }

    @Test
    public void GIVE__1Apple_1Milk_1Bread__THEN__SubTotal_3pounds10__DiscountedTotal_3pounds() {

        addItemInBasket("Apple", "Milk", "Bread");
        assertEquals(new BigDecimal("3.00"), priceBasket.getTotal());
        assertEquals(new BigDecimal("3.10"), priceBasket.getSubTotal());

    }

    private void addItemInBasket(String... itemNames) {
        for (String itemName : itemNames) {
            priceBasket.addItem(addItem(itemName));
        }
    }
}
