package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final ProductBasket basket;
    private final StorageService storageService;

    public BasketService(ProductBasket basket, StorageService storageService) {
        this.basket = basket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Optional<Product> availaibleProduct = storageService.getProductById(id);
        basket.addProduct(availaibleProduct.orElseThrow(() -> new NoSuchProductException(String.valueOf(id))).getId());
    }

    public UserBasket getUserBasket() {
        List<BasketItem> basketItems = basket.getAllProducts().entrySet()
                .stream()
                .map(entry -> new BasketItem(storageService.getProductById(entry.getKey()).get(), entry.getValue()))
                .collect(Collectors.toList());
        return new UserBasket(basketItems);
    }
}
