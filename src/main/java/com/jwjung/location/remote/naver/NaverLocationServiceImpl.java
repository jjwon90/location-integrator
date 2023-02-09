package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverLocationServiceImpl implements NaverLocationService {
    private final NaverLocationClient naverLocationClient;

    @Override
    public List<NaverMapItemV1> getNaverItems(String query) {
        return naverLocationClient.getNaverLocationSearch(query)
                .map(NaverMapResponseV1::getAdjustItems)
                .orElseGet(Collections::emptyList);
    }
}
