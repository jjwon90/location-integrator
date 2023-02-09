package com.jwjung.location.remote.naver.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NaverLocationItemsV1Test {

    @Test
    void getEscapingTitle() {
        NaverMapItemV1 naverMapItemV1 = new NaverMapItemV1("종가댁양<b>곱창</b>&amp;마늘<b>곱창</b>",
                null, null, null, null, null, null, null, null);

        String escapingTitle = naverMapItemV1.getEscapingTitle();
        assertEquals("종가댁양곱창&마늘곱창", escapingTitle);
    }
}