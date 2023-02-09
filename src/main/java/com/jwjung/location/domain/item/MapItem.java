package com.jwjung.location.domain.item;

public record MapItem(String name,
                      boolean kakao) {

    public String getFirstWordOfName() {
        return this.name.split(" ")[0];
    }
}
