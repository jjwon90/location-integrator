package com.jwjung.location.search.adapter.in.web.dto;

import com.jwjung.location.search.domain.LocationItems;

import java.util.List;

public record LocationResultsDTO(List<LocationResultItemDTO> resultList) {
    public static LocationResultsDTO of(LocationItems items) {
        return new LocationResultsDTO(items.itemList()
                .stream()
                .map(i -> new LocationResultItemDTO(i.name(), i.address()))
                .toList());
    }
}
