package org.skypro.skyshop.service;

import java.util.List;

public class UserBasket {

    private final List<BasketItem> basketItems;
    private final int total;

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public int getTotal() {
        return total;
    }

    public UserBasket(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
        total = basketItems.stream()
                .mapToInt(BasketItem::getItemSum)
                .sum();
    }
}
