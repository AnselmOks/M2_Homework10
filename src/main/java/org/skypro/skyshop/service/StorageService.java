package org.skypro.skyshop.service;

import java.util.*;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    private void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    private void addArticle(Article article) {
        articles.put(article.getId(), article);
    }

    private void fillData() {
        addProduct(new SimpleProduct("Помидор", 300, UUID.randomUUID()));
        addProduct(new FixPriceProduct("Груша", UUID.randomUUID()));
        addProduct(new FixPriceProduct("Мандарин", UUID.randomUUID()));
        addProduct(new DiscountedProduct("Лук", 100, 20, UUID.randomUUID()));
        addProduct(new DiscountedProduct("Арбуз", 450, 10, UUID.randomUUID()));
        addProduct(new SimpleProduct("Морковь", 300, UUID.randomUUID()));

        addArticle(new Article("Помидор",
                "Помидор (томат) — это съедобный плод растения семейства паслёновых",
                UUID.randomUUID()));
        addArticle(new Article("Польза Помидора", """
                Помидоры полезны благодаря антиоксиданту ликопину
                (профилактика рака и сердечных заболеваний), витаминам (C, A, K, группы B) для иммунитета,
                зрения и нервной системы""",
                UUID.randomUUID()));
        addArticle(new Article("Рецепт яичницы с луком", """
                Лук - 1 шт.
                Помидоры - 2–3 шт.
                Яйца - 3 шт.
                Соль по вкусу""",
                UUID.randomUUID()));
        addArticle(new Article("Какие продукты относятся к пасленовым?", """
                Зеленые плоды пасленовых. Помидоры, баклажаны, перец и так далее""",
                UUID.randomUUID()));
        addArticle(new Article("Синьор Помидор", """
                Острый и ароматный тайский суп с морепродуктами, грибами и кокосовым молоком.
                Подается с рисом, лаймом и свежей кинзой – взрыв вкусов!""",
                UUID.randomUUID()));
        addArticle(new Article("DeepSeek", """
                Семейство передовых больших языковых моделей (LLM) и
                компания-разработчик из Китая""",
                UUID.randomUUID()));
    }

    public StorageService() {
        products = new HashMap<>();
        articles = new HashMap<>();
        fillData();
    }

    public Collection<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Collection<Article> getAllArticles() {
        return new ArrayList<>(articles.values());
    }

    public List<Searchable> getAllData() {
        List<Searchable> result = new ArrayList<>();
        result.addAll(products.values());
        result.addAll(articles.values());
        return result;
    }

}
