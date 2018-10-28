package com.bjss.shopping;

import com.bjss.shopping.goods.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal getSubTotal() {

        BigDecimal total = items.stream()
                    .map(item -> ((Item) item).getPrice())
                    .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {

        BigDecimal discountedApplePrice = items.stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Apple"))
                .map(item -> ((Item) item).getPrice().multiply(new BigDecimal("0.90")))
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));

        long countSoup = items.stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Soup"))
                .count();
        long countBread = items.stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Bread"))
                .count();

        BigDecimal discountedBreadPrice=new BigDecimal("0");
        if( countSoup >= 2 && countBread >= 1) {
            long totaldiscountToApply = countSoup/2;
            BigDecimal totalBread = items.stream()
                    .filter(item -> ((Item) item).getName().equalsIgnoreCase("Bread"))
                    .map(item -> ((Item) item).getPrice())
                    .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
            discountedBreadPrice  = totalBread.divide(new BigDecimal(totaldiscountToApply*2));


        }

        BigDecimal allTotalWithoutBreadAndApple = items.stream()
                .filter(item -> !((Item) item).getName().equalsIgnoreCase("Bread"))
                .filter(item -> !((Item) item).getName().equalsIgnoreCase("Apple"))
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));

        BigDecimal totalAfterDiscount = discountedBreadPrice.add(allTotalWithoutBreadAndApple).add(discountedApplePrice);
        return totalAfterDiscount.setScale(2, RoundingMode.HALF_UP);
    }
}
