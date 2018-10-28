package com.bjss.shopping;

import com.bjss.shopping.goods.Item;
import com.bjss.shopping.goods.ItemFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PriceBasket {

    private static final String SUBTOTAL = "Subtotal";
    private static final String TOTAL = "Total";
    private static final String NO_OFFERS = "(no offers available)";

    private List<? super Item> items;


    public static void main(String[] args) {
        if(args.length==0 || args.length==1) {
            System.out.println("Arguments Required");
            System.out.println("Format: PriceBasket Apples Milk Bread");
            return;
        }
        if(!args[0].equalsIgnoreCase("pricebasket")) {
            System.out.println("First Argument is invalid");
            System.out.println("Format: PriceBasket");
            return;
        }
        PriceBasket priceBasket = new PriceBasket();
        for(int i=1;i<args.length;i++) {
            priceBasket.addItem(ItemFactory.addItem(args[i]));
        }
        priceBasket.print();
    }

    public List<? super Item> getItems() {
        return items;
    }

    public <T extends Item> void addItem(T... basketItems) {
        if (items == null) {
            items = new ArrayList<>();
        }
        for (T item : basketItems) {
            items.add(item);
        }
    }

    public BigDecimal getSubTotal() {

        BigDecimal total = items.stream()
                .map(item -> ((Item) item).getPrice())
                .reduce(new BigDecimal("0"), (price1, price2) -> price1.add(price2));
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {

        return new SpecialOffer(this).getTotalAfterOffers();
    }

    public void print() {
        SpecialOffer specialOffer = new SpecialOffer(this);
        BigDecimal totalAfterOffers = specialOffer.getTotalAfterOffers();
        System.out.println(SUBTOTAL + ":" + " " + "£" + getSubTotal().toString());
        printOffers(specialOffer);
        System.out.println(TOTAL + ":" + " " + "£" + totalAfterOffers.toString());
    }

    private void printOffers(SpecialOffer specialOffer) {
        if (specialOffer.getOfferStatements().size() == 0) {
            System.out.println(NO_OFFERS);
        } else {
            for (String stmt : specialOffer.getOfferStatements()) {
                System.out.println(stmt);
            }
        }
    }

}
