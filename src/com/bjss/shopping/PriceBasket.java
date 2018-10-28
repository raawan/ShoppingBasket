package com.bjss.shopping;

import com.bjss.shopping.goods.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PriceBasket {

    private List<? super Item> items;

    public List<? super Item> getItems() {
        return items;
    }

    public <T extends Item> void addItem(T item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    public BigDecimal getSubTotal() {

        BigDecimal total = items.stream()
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {

        return new SpecialOffer(this).getTotal();
    }

}
