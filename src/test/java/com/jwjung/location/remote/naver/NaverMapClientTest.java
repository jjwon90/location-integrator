package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.naver.dto.NaverMapResponseV1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Disabled
class NaverMapClientTest {
    @Autowired
    private NaverMapClient naverMapClient;

    @Test
    void getNaverLocationSearch() {
        Optional<NaverMapResponseV1> 은행 = naverMapClient.getNaverLocationSearch("은행");
        은행.get().items()
                .forEach(System.out::println);
    }
}