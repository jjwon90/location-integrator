package com.jwjung.location.search.adapter.out.remote.naver;

import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationResponseV1;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NaverLocationClientFallbackFactory implements FallbackFactory<NaverLocationClient> {
    @Override
    public NaverLocationClient create(Throwable cause) {
        return new NaverLocationClient() {
            @Override
            public Optional<NaverLocationResponseV1> getNaverLocationSearch(String query) {
                return Optional.empty();
            }
        };
    }
}
