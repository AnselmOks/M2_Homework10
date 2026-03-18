package org.skypro.skyshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    StorageService storageService;

    @Mock
    ProductBasket productBasket;

    @InjectMocks
    BasketService basketService;

    private Map<UUID, Integer> getTestBasket(UUID testId, Integer col) {
        Map<UUID, Integer> testBasket = new HashMap<>();
        testBasket.put(testId, col);
        return testBasket;
    }

    @DisplayName("Добавляется несуществующий товар в корзину")
    @Test
    public void whenAddNotExistProductToBasket() {
        UUID testId = UUID.randomUUID();
        when(storageService.getProductById(testId)).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(NoSuchProductException.class,
                () -> basketService.addProduct(testId));
        Assertions.assertEquals("org.skypro.skyshop.exception.NoSuchProductException", exception.getClass().getName());
        verify(productBasket, Mockito.never()).addProduct(testId);
    }

    @DisplayName("В корзину добавляется существующий товар")
    @Test
    public void whenAddExistProductToBasket() {
        UUID testId = UUID.randomUUID();
        Product testProduct = new SimpleProduct("Тестовый продукт", 100, testId);
        when(storageService.getProductById(testId)).thenReturn(Optional.ofNullable(testProduct));
        basketService.addProduct(testId);
        verify(productBasket, Mockito.times(1)).addProduct(testId);
    }

    @DisplayName("Возвращается пустая корзина, если productBasket пуста")
    @Test
    public void returnEmptyUserBasketIfProductBasketIsEmpty() {
        when(productBasket.getAllProducts()).thenReturn(new HashMap<>());
//        Assertions.assertTrue(basketService.getUserBasket().getBasketItems().isEmpty() &&
//                basketService.getUserBasket().getTotal() == 0);
        UserBasket userBasket = basketService.getUserBasket();
        assertThat(userBasket.getBasketItems()).isEmpty();
        assertThat(userBasket.getTotal()).isZero();
    }

    @DisplayName("Возвращается не пустая корзина, если есть товары в productBasket")
    @Test
    public void returnNonEmptyUserBasketIfProductBasketIsNotEmpty() {
        UUID testId = UUID.randomUUID();
        Integer testCol = 2;
        Map<UUID, Integer> testProductBasket = getTestBasket(testId, testCol);
        when(productBasket.getAllProducts()).thenReturn(testProductBasket);

        Product testProduct = new SimpleProduct("Тестовый продукт", 100, testId);
        when(storageService.getProductById(testId)).thenReturn(Optional.ofNullable(testProduct));

        basketService.addProduct(testId);

        UserBasket userBasket = basketService.getUserBasket();
        Assertions.assertTrue(!userBasket.getBasketItems().isEmpty() &&
                userBasket.getTotal() == testProduct.getPrice() * testCol);
    }

}
