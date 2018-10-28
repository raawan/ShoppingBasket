package com.bjss.shopping;

import com.bjss.shopping.goods.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpecialOffer {

    private PriceBasket basket;

    SpecialOffer(PriceBasket basket) {
        this.basket = basket;
    }

    public BigDecimal getTotal() {

        BigDecimal discountedApplePrice = applyAppleSpecialOffer();
        BigDecimal discountedBreadPrice = applySoupAndBreadSpecialOffer();
        BigDecimal allTotalWithoutBreadAndApple = getTotalExcludingSpecialOfferItem();
        BigDecimal totalAfterDiscount = allTotalWithoutBreadAndApple
                .add(discountedBreadPrice)
                .add(discountedApplePrice);
        return totalAfterDiscount.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal applyAppleSpecialOffer() {
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Apple"))
                .map(item -> ((Item) item).getPrice().multiply(new BigDecimal("0.90")))
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
    }

    private BigDecimal getTotalExcludingSpecialOfferItem() {
        return basket.getItems().stream()
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
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Bread"))
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
    }

    private long getItemCount(String itemName) {
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(itemName))
                .count();
    }

}
