package com.jwjung.location.remote.naver.dto;

import java.util.List;

public record NaverLocationResponseV1(long total, long start, long display,
                                      List<NaverLocationItemV1> items) {

}
