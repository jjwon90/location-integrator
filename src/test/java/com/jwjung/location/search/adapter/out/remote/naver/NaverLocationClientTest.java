package com.jwjung.location.search.adapter.out.remote.naver;

import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationResponseV1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
class NaverLocationClientTest {
    @Autowired
    private NaverLocationClient naverLocationClient;

    @Test
    void getNaverLocationSearch() {
        Optional<NaverLocationResponseV1> 은행 = naverLocationClient.getNaverLocationSearch("동탄하나은행");
        assertTrue(!은행.get().items().isEmpty());
    }
}