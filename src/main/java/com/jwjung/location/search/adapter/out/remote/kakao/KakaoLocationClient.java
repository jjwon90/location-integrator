package com.jwjung.location.search.adapter.out.remote.kakao;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "kakaolocation",
        url = "${kakao.host}",
        configuration = KakaoLocationClientConfiguration.class,
        fallbackFactory = KakaoLocationClientFallbackFactory.class)
public interface KakaoLocationClient {
    @RequestMapping("/v2/local/search/keyword.json?page=1&size=5")
    Optional<KakaoLocationResponseV1> getKakaoLocationSearch(@RequestParam("query") String query);
}
