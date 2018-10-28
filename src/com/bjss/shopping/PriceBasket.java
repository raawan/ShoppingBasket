package com.bjss.shopping;

import com.bjss.shopping.goods.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceBasket {

    List<? super Item> items;

    public <T extends Item> void addItem(T item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    public BigDecimal getTotal() {
        return null;
    }
}
