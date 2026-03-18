package org.skypro.skyshop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    StorageService storageService;

    @InjectMocks
    SearchService searchService;

    private List<Searchable> getTestData() {
        List<Searchable> testData = new ArrayList<>();
        testData.add(new SimpleProduct("Помидор", 300, UUID.randomUUID()));
        testData.add(new FixPriceProduct("Груша", UUID.randomUUID()));
        testData.add(new FixPriceProduct("Мандарин", UUID.randomUUID()));
        testData.add(new DiscountedProduct("Лук", 100, 20, UUID.randomUUID()));
        testData.add(new DiscountedProduct("Арбуз", 450, 10, UUID.randomUUID()));
        testData.add(new SimpleProduct("Морковь", 300, UUID.randomUUID()));
        return testData;
    }

    @DisplayName("Хранилище пустое")
    @Test
    public void whenStorageIsEmpty() {
        List<SearchResult> results = searchService.search("Помидор");
        //assertEquals(results, Collections.emptyList());
        assertThat(results).isEmpty();
    }

    @DisplayName("Хранилище не пустое, но товар не найден")
    @Test
    public void whenStorageIsNotEmptyButNotFound() {
        List<Searchable> testData = getTestData();
        when(storageService.getAllData()).thenReturn(testData);
        List<SearchResult> results = searchService.search("Нет такой строки");
        assertEquals(results, Collections.emptyList());
    }

    @DisplayName("Хранилище не пустое, и товар найден")
    @Test
    public void whenStorageIsNotEmptyAndFound() {
        List<Searchable> testData = getTestData();
        when(storageService.getAllData()).thenReturn(testData);
        List<SearchResult> results = searchService.search("Помидор");
        assertEquals(results.size(), 1);
    }

}
