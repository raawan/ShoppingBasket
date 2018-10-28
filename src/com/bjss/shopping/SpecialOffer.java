package com.bjss.shopping;

import com.bjss.shopping.goods.Item;
import com.bjss.shopping.goods.promotion.Discount;
import com.bjss.shopping.goods.promotion.WeeklyDiscounts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Discount> allDiscounts = WeeklyDiscounts.getAllDiscounts();
        List<Discount> itemsWithIndependentDiscount = allDiscounts.stream()
                .filter(discount -> !discount.getDependentItem().isPresent())
                .collect(Collectors.toList());
        List<Discount> itemsWithDependentDiscount = allDiscounts.stream()
                .filter(discount -> discount.getDependentItem().isPresent())
                .collect(Collectors.toList());

        BigDecimal sumOfIndependentSpecialOffers = getSumOfIndependentSpecialOfferItems(itemsWithIndependentDiscount);
        BigDecimal sumOfDependentSpecialOffers = getSumOfDependentSpecialOfferItems(itemsWithDependentDiscount);
        BigDecimal allNonSpecialOfferItemTotalPrice = getTotalExcludingSpecialOfferItem(itemsWithIndependentDiscount, itemsWithDependentDiscount);

        return allNonSpecialOfferItemTotalPrice
                .add(sumOfIndependentSpecialOffers)
                .add(sumOfDependentSpecialOffers)
                .setScale(2, RoundingMode.HALF_UP);

    }

    private BigDecimal getSumOfDependentSpecialOfferItems(List<Discount> itemsWithDependentDiscount) {

        BigDecimal sumOfIndependentSpecialOffers = new BigDecimal("0");
        for (Discount discount : itemsWithDependentDiscount) {
            sumOfIndependentSpecialOffers = sumOfIndependentSpecialOffers.add(applyDependentSpecialOffer(discount));
        }
        return sumOfIndependentSpecialOffers;
    }

    private BigDecimal getSumOfIndependentSpecialOfferItems(List<Discount> itemsWithIndependentDiscount) {

        BigDecimal sumOfIndependentSpecialOffers = new BigDecimal("0");
        for (Discount discount : itemsWithIndependentDiscount) {
            sumOfIndependentSpecialOffers = sumOfIndependentSpecialOffers.add(applyIndependentSpecialOffer(discount));
        }
        return sumOfIndependentSpecialOffers;
    }

    private BigDecimal getTotalExcludingSpecialOfferItem(List<Discount> itemsWithIndependentDiscount, List<Discount> itemsWithDependentDiscount) {
        return basket.getItems().stream()
                .filter(
                        item -> !this.containInList(itemsWithIndependentDiscount, ((Item) item))
                )
                .filter(
                        item -> !this.containInList(itemsWithDependentDiscount, ((Item) item))
                )
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), BigDecimal::add);
    }

    private boolean containInList(List<Discount> discountedItems, Item item) {
        List<Discount> items = discountedItems.stream().filter(discount -> discount.getDiscountItem().getName().equalsIgnoreCase(item.getName())).collect(Collectors.toList());
        if (items.size() > 0) {
            return true;
        }
        return false;
    }

    /*
    These are the offers of kind where a price of offer depends on the number of other objects bought
    ex: on buying 2 soup , bread is 50 percent off
     */
    private BigDecimal applyDependentSpecialOffer(Discount discount) {
        long countDependentItem = getItemCount(discount.getDependentItem().get().getName());
        long countItemToBeDiscounted = getItemCount(discount.getDiscountItem().getName());
        BigDecimal totalItemCost = getItemTotal(discount.getDiscountItem().getName());

        if (countDependentItem >= discount.getCountOfDependentItem() && countItemToBeDiscounted >= 1) {
            long totaldiscountToApply = countDependentItem / discount.getCountOfDependentItem();
            totaldiscountToApply = totaldiscountToApply == 1 ? discount.getCountOfDependentItem() : totaldiscountToApply;
            BigDecimal discountedItemPrice = totalItemCost.divide(new BigDecimal(totaldiscountToApply));
            addOfferStatement(totalItemCost, discountedItemPrice, String.valueOf(100 - discount.getDiscountPercentage()), discount.getDiscountItem().getName());
            return discountedItemPrice;
        }
        return totalItemCost;
    }

    /*
    These are kind of offer where there is a reduction of price on buying the item
     */
    private BigDecimal applyIndependentSpecialOffer(Discount discount) {
        BigDecimal discountedPrice = basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(discount.getDiscountItem().getName()))
                .map(item -> ((Item) item).getPrice().multiply(new BigDecimal(discount.getDiscountPercentage()).multiply(new BigDecimal("0.01"))))
                .reduce(new BigDecimal("0"), BigDecimal::add);

        if (!(discountedPrice.compareTo(new BigDecimal("0")) == 0)) {
            addOfferStatement(getItemTotal(discount.getDiscountItem().getName()), discountedPrice, String.valueOf(100 - discount.getDiscountPercentage()), discount.getDiscountItem().getName());
        }

        return discountedPrice;
    }

    private void addOfferStatement(BigDecimal totalPrice, BigDecimal discountedPrice, String percentageOff, String itemName) {

        BigDecimal totalDiscount = totalPrice.subtract(discountedPrice).setScale(2, RoundingMode.HALF_UP);
        StringBuilder offerStatement = new StringBuilder(itemName).append(" " + percentageOff + "%").append(" off:-").append(totalDiscount.toString()).append(" p");
        offerStatements.add(offerStatement.toString());
    }

    private BigDecimal getItemTotal(String itemName) {
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(itemName))
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), BigDecimal::add);
    }

    private long getItemCount(String itemName) {
        return basket.getItems().stream()
                .filter(item -> ((Item) item).getName().equalsIgnoreCase(itemName))
                .count();
    }

}
