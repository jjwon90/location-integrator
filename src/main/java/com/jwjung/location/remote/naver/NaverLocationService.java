package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapItemV1;

import java.util.List;

public interface NaverLocationService {
    List<NaverMapItemV1> getNaverItems(String query);
}
