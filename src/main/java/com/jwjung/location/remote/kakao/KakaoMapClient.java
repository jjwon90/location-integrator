package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoMapResponseV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "kakaomap",
        url = "${kakao.host}",
        configuration = KakaoMapClientConfiguration.class,
        fallbackFactory = KakaoMapClientFallbackFactory.class)
public interface KakaoMapClient {
    @RequestMapping("/v2/local/search/keyword.json?page=1&size=5")
    Optional<KakaoMapResponseV1> getKakaoLocationSearch(@RequestParam("query") String query);
}
