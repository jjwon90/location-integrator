package com.jwjung.location.remote.naver.dto;

import java.util.List;

public record NaverMapResponseV1(long total, long start, long display,
                                 List<NaverMapItemV1> items) {

    public List<NaverMapItemV1> getAdjustItems() {
        return this.items.stream()
                .map(n -> n.withTitle(n.getEscapingTitle()))
                .toList();
    }
}
