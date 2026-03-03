package org.skypro.skyshop.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.product.Product;

public class BasketItem {

    private final Product product;
    private final Integer amount;

    public BasketItem(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    @JsonIgnore
    public int getItemSum() {
        return product.getPrice() * amount;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getAmount() {
        return amount;
    }
}
