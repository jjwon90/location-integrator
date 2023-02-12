package com.jwjung.location.search.adapter.out.remote.naver.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NaverLocationItemsV1Test {

    @Test
    void getEscapingTitle() {
        NaverLocationItemV1 naverLocationItemV1 = new NaverLocationItemV1("종가댁양<b>곱창</b>&amp;마늘<b>곱창</b>",
                null, null, null, null, null, null, null, null);

        String escapingTitle = naverLocationItemV1.getEscapingTitle();
        assertEquals("종가댁양곱창&마늘곱창", escapingTitle);
    }

    @Test
    void getEscapingTitle2() {
        NaverLocationItemV1 naverLocationItemV1 = new NaverLocationItemV1("한국<b>씨티은행</b> 영업부             신세계 본점 지하1층 ATM",
                null, null, null, null, null, null, null, null);
        assertEquals("한국씨티은행 영업부 신세계 본점 지하1층 ATM", naverLocationItemV1.getEscapingTitle());
    }
}