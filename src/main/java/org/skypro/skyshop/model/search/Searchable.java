package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String getSearchItem();

    String getContentType();

    String getObjectName();

    UUID getId();

    default String getStringRepresentation() {
        return getObjectName() + " - " + getContentType();
    }

}
