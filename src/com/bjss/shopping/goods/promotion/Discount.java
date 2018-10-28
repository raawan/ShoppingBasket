package com.bjss.shopping.goods.promotion;

import com.bjss.shopping.goods.Item;

import java.util.Optional;

public class Discount {

    private final Item discountItem;
    private int discountPercentage;
    private Optional<Item> dependentItem;
    private int countOfDependentItem;

    public Discount(Item discountItem) {
        this.discountItem = discountItem;
    }

    public Optional<Item> getDependentItem() {
        return dependentItem;
    }

    public void setDependentItem(Optional<Item> dependentItem) {
        this.dependentItem = dependentItem;
    }

    public int getCountOfDependentItem() {
        return countOfDependentItem;
    }

    public void setCountOfDependentItem(int countOfDependentItem) {
        this.countOfDependentItem = countOfDependentItem;
    }
    public Item getDiscountItem() {
        return discountItem;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
