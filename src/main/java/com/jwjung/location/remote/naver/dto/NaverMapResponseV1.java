package com.jwjung.location.remote.naver.dto;

import java.util.List;

public record NaverMapResponseV1(long total, long start, long display,
                                 List<NaverMapItemV1> items) {
}
