package com.bjss.shopping.goods;

import java.math.BigDecimal;

public class ItemFactory {

    /*
    Assuming the name of the items are unique
     */
    public static Item createItem(String name) {
        switch (name) {
            case "Apple" : return new Apple("Apple",new BigDecimal(0.65));
            case "Bread" : return new Bread("Bread",new BigDecimal(0.80));
            case "Milk" : return new Milk("Milk",new BigDecimal(1.30));
            case "Soup" : return new Soup("Soup",new BigDecimal(1.00));
            default:throw new RuntimeException("No such item");
        }
    }
}
