package com.bjss.shopping.goods;

import java.math.BigDecimal;

public abstract class Item {

    private String name;
    private BigDecimal price;

    public Item(String name, BigDecimal price) {
        this.name=name;
        this.price=price;
    }
}
