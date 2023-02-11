package com.jwjung.location.popular.adapter.in.web.dto;

import com.jwjung.location.popular.domain.PopularItem;

import java.util.List;

public record PopularQueriesDTO(List<PopularQueryDTO> popularList) {
    public static PopularQueriesDTO of(List<PopularItem> popularItems) {
        return new PopularQueriesDTO(popularItems.stream()
                .map(PopularQueryDTO::of)
                .toList());
    }
}
