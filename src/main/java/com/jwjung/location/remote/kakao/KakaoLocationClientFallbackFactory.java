package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoLocationResponseV1;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KakaoLocationClientFallbackFactory implements FallbackFactory<KakaoLocationClient> {
    @Override
    public KakaoLocationClient create(Throwable cause) {
        return new KakaoLocationClient() {
            @Override
            public Optional<KakaoLocationResponseV1> getKakaoLocationSearch(String query) {
                return Optional.empty();
            }
        };
    }
}
