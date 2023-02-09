package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapResponseV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "navermap",
        url = "${naver.host}",
        fallbackFactory = NaverMapClientFallbackFactory.class,
        configuration = NaverMapClientConfiguration.class)
public interface NaverMapClient {
    @RequestMapping("/v1/search/local.json?display=5&start=1")
    Optional<NaverMapResponseV1> getNaverLocationSearch(@RequestParam("query") String query);
}
