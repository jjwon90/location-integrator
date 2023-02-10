package com.jwjung.location.search.domain;

import org.apache.commons.lang3.StringUtils;

public record LocationItem(String name) {

    public String getFirstWordOfName() {
        return this.name.split(" ")[0];
    }

    public boolean isEqualItem(LocationItem anotherItem) {
        return StringUtils.equals(this.getFirstWordOfName(), anotherItem.getFirstWordOfName());
    }
}
