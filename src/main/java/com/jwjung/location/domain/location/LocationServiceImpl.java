package com.jwjung.location.domain.location;

import com.jwjung.location.remote.kakao.KakaoLocationService;
import com.jwjung.location.remote.naver.NaverLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final KakaoLocationService kakaoLocationService;
    private final NaverLocationService naverLocationService;

    @Override
    public LocationItems getLocationItems(String query) {
        return LocationItems.builder().build();
    }
}
