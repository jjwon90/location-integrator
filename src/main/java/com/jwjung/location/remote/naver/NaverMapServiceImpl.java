package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverMapServiceImpl implements NaverMapService {
    private final NaverMapClient naverMapClient;

    @Override
    public List<NaverMapItemV1> getNaverItems(String query) {
        return naverMapClient.getNaverLocationSearch(query)
                .map(NaverMapResponseV1::items)
                .orElseGet(Collections::emptyList);
    }
}
