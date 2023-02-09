package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapResponseV1;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NaverLocationClientFallbackFactory implements FallbackFactory<NaverLocationClient> {
    @Override
    public NaverLocationClient create(Throwable cause) {
        return new NaverLocationClient() {
            @Override
            public Optional<NaverMapResponseV1> getNaverLocationSearch(String query) {
                return Optional.empty();
            }
        };
    }
}