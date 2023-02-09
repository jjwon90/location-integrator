package com.jwjung.location.remote.naver;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverMapClientConfiguration {
    private static final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    private static final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

    @Value("${naver.client-id}")
    private String X_API_CLIENT_ID_VALUE;
    @Value("${naver.client-secret}")
    private String X_API_CLIENT_SECRET_VALUE;

    @Bean
    public NaverMapRequestInterceptor naverMapRequestInterceptor() {
        return new NaverMapRequestInterceptor();
    }

    public class NaverMapRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            template.header(X_NAVER_CLIENT_ID, X_API_CLIENT_ID_VALUE);
            template.header(X_NAVER_CLIENT_SECRET, X_API_CLIENT_SECRET_VALUE);
        }
    }
}
