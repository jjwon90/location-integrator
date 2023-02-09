package com.jwjung.location.remote.kakao;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoLocationClientConfiguration {
    private static final String KAKAO_AUTH_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "KakaoAK ";
    @Value("${kakao.client-secret}")
    private String API_KEY_VALUE;

    @Bean
    public KakaoRequestInterceptor kakaoRequestInterceptor() {
        return new KakaoRequestInterceptor();
    }

    public class KakaoRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            template.header(KAKAO_AUTH_HEADER, HEADER_PREFIX + API_KEY_VALUE);
        }
    }
}
