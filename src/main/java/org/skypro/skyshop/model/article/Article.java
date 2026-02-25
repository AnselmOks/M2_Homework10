package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {

    private final String header;
    private final String text;
    private final UUID id;

    public Article(String header, String text, UUID id) {
        this.header = header;
        this.text = text;
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return header + System.lineSeparator() + text;
    }

    @Override
    @JsonIgnore
    public String getSearchItem() {
        return toString();
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getObjectName() {
        return getHeader();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Searchable article)) return false;
        return Objects.equals(header, article.getObjectName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(header);
    }

    @Override
    public UUID getId() {
        return id;
    }
}
