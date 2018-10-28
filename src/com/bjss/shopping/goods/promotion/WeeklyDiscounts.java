package com.bjss.shopping.goods.promotion;

import com.bjss.shopping.goods.ItemFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeeklyDiscounts {


    public static List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        Discount appleDiscount = new Discount(ItemFactory.addItem("Apple"));
        appleDiscount.setDiscountPercentage(90);
        appleDiscount.setDependentItem(Optional.empty());
        discounts.add(appleDiscount);

        Discount breadDiscount = new Discount(ItemFactory.addItem("Bread"));
        breadDiscount.setDiscountPercentage(50);
        breadDiscount.setDependentItem(Optional.of(ItemFactory.addItem("Soup")));
        breadDiscount.setCountOfDependentItem(2);
        discounts.add(breadDiscount);

        return discounts;
    }
}
