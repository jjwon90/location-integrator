package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoMapResponseV1;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KakaoMapClientFallbackFactory implements FallbackFactory<KakaoMapClient> {
    @Override
    public KakaoMapClient create(Throwable cause) {
        return new KakaoMapClient() {
            @Override
            public Optional<KakaoMapResponseV1> getKakaoLocationSearch(String query) {
                return Optional.empty();
            }
        };
    }
}
