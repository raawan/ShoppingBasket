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

        BigDecimal discountedApplePrice = applyAppleSpecialOffer();
        BigDecimal discountedBreadPrice = applySoupAndBreadSpecialOffer();
        BigDecimal allTotalWithoutBreadAndApple = getTotalExcludingSpecialOfferItem();
        BigDecimal totalAfterDiscount = discountedBreadPrice.add(allTotalWithoutBreadAndApple).add(discountedApplePrice);
        return totalAfterDiscount.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal applyAppleSpecialOffer() {
        return items.stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Apple"))
                .map(item -> ((Item) item).getPrice().multiply(new BigDecimal("0.90")))
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
    }

    private BigDecimal getTotalExcludingSpecialOfferItem() {
        return items.stream()
                .filter(item -> !((Item) item).getName().equalsIgnoreCase("Bread"))
                .filter(item -> !((Item) item).getName().equalsIgnoreCase("Apple"))
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
    }

    private BigDecimal applySoupAndBreadSpecialOffer() {
        long countSoup = getItemCount("Soup");
        long countBread = getItemCount("Bread");
        BigDecimal totalBread = getBreadTotal();
        BigDecimal discountedBreadPrice;
        if (countSoup >= 2 && countBread >= 1) {
            long totaldiscountToApply = countSoup / 2;
            totaldiscountToApply = totaldiscountToApply == 1 ? 2 : totaldiscountToApply;
            discountedBreadPrice = totalBread.divide(new BigDecimal(totaldiscountToApply));
            return discountedBreadPrice;
        }
        return totalBread;
    }

    private BigDecimal getBreadTotal() {
        return  items.stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Bread"))
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
    }

    private long getItemCount(String itemName) {
        return  items.stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(itemName))
                .count();
    }


}
