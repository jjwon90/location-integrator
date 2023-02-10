package com.jwjung.location.api.location.dto;

import com.jwjung.location.domain.location.LocationItems;

import java.util.List;

public record LocationResultsDTO(List<LocationResultItemDTO> resultList) {
    public static LocationResultsDTO of(LocationItems items) {
        return new LocationResultsDTO(items.getItemList()
                .stream()
                .map(i -> new LocationResultItemDTO(i.name()))
                .toList());
    }
}
