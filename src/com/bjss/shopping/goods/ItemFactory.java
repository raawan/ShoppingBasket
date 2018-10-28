package com.bjss.shopping.goods;

import com.bjss.shopping.exception.NoSuchItemException;

import java.math.BigDecimal;

public class ItemFactory {

    /*
    Assuming the name of the items are unique
     */
    public static Item addItem(String name) {
        switch (name.toLowerCase()) {
            case "apple" : return new Apple("Apple",new BigDecimal(1.00));
            case "bread" : return new Bread("Bread",new BigDecimal(0.80));
            case "milk" : return new Milk("Milk",new BigDecimal(1.30));
            case "soup" : return new Soup("Soup",new BigDecimal(0.65));
            default:throw new NoSuchItemException("No such item:"+name+"!" +"Valid items are Apple, Bread, Milk, Soup");
        }
    }
}
