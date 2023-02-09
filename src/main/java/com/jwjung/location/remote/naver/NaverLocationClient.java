package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapResponseV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "naverlocation",
        url = "${naver.host}",
        fallbackFactory = NaverLocationClientFallbackFactory.class,
        configuration = NaverLocationClientConfiguration.class)
public interface NaverLocationClient {
    @RequestMapping("/v1/search/local.json?display=5&start=1")
    Optional<NaverMapResponseV1> getNaverLocationSearch(@RequestParam("query") String query);
}
