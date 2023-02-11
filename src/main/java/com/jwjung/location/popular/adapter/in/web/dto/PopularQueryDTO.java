package com.jwjung.location.popular.adapter.in.web.dto;

import com.jwjung.location.popular.domain.PopularItem;

public record PopularQueryDTO(String query, long count) {
    static PopularQueryDTO of(PopularItem item) {
        return new PopularQueryDTO(item.query(), item.count());
    }
}
