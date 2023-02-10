package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverLocationResponseV1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Disabled
class NaverLocationClientTest {
    @Autowired
    private NaverLocationClient naverLocationClient;

    @Test
    void getNaverLocationSearch() {
        Optional<NaverLocationResponseV1> 은행 = naverLocationClient.getNaverLocationSearch("은행");
        은행.get().items()
                .forEach(System.out::println);
    }
}