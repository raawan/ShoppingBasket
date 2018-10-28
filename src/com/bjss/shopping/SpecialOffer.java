package com.bjss.shopping;

import com.bjss.shopping.goods.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SpecialOffer {

    private final PriceBasket basket;

    private final List<String> offerStatements = new ArrayList<>();

    public List<String> getOfferStatements() {
        return offerStatements;
    }

    SpecialOffer(PriceBasket basket) {
        this.basket = basket;
    }

    public BigDecimal getTotalAfterOffers() {

        BigDecimal discountedApplePrice = applyAppleSpecialOffer();
        BigDecimal discountedBreadPrice = applySoupAndBreadSpecialOffer();
        BigDecimal allTotalWithoutBreadAndApple = getTotalExcludingSpecialOfferItem();
        BigDecimal totalAfterDiscount = allTotalWithoutBreadAndApple
                .add(discountedBreadPrice)
                .add(discountedApplePrice);
        return totalAfterDiscount.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal applyAppleSpecialOffer() {
        BigDecimal discountedPrice = basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase("Apple"))
                .map(item -> ((Item) item).getPrice().multiply(new BigDecimal("0.90")))
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));

        if (!(discountedPrice.compareTo(new BigDecimal("0")) == 0)) {
            addOfferStatement(getItemTotal("Apple"), discountedPrice, "10", "Apple");
        }

        return discountedPrice;
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
        BigDecimal totalBread = getItemTotal("Bread");
        if (countSoup >= 2 && countBread >= 1) {
            long totaldiscountToApply = countSoup / 2;
            totaldiscountToApply = totaldiscountToApply == 1 ? 2 : totaldiscountToApply;
            BigDecimal discountedBreadPrice = totalBread.divide(new BigDecimal(totaldiscountToApply));
            addOfferStatement(totalBread, discountedBreadPrice, "50", "Bread");
            return discountedBreadPrice;
        }
        return totalBread;
    }

    private void addOfferStatement(BigDecimal totalPrice, BigDecimal discountedPrice, String percentageOff, String itemName) {

        BigDecimal totalDiscount = totalPrice.subtract(discountedPrice).setScale(2, RoundingMode.HALF_UP);
        StringBuffer offerStatement = new StringBuffer(itemName).append(" " + percentageOff + "%").append(" off:-").append(totalDiscount.toString()).append(" p");
        offerStatements.add(offerStatement.toString());
    }

    private BigDecimal getItemTotal(String itemName) {
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(itemName))
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
    }

    private long getItemCount(String itemName) {
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(itemName))
                .count();
    }

}
