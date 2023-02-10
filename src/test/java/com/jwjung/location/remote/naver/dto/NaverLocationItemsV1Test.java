package com.jwjung.location.remote.naver.dto;

import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationItemV1;
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
}