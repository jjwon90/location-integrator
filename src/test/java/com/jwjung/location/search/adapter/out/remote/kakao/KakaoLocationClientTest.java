package com.jwjung.location.search.adapter.out.remote.kakao;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
class KakaoLocationClientTest {
    @Autowired
    private KakaoLocationClient kakaoLocationClient;

    @Test
    void getKakaoLocationSearch() {
        Optional<KakaoLocationResponseV1> 은행 =
                kakaoLocationClient.getKakaoLocationSearch("천안 은행");
        assertTrue(!은행.get().documents().isEmpty());
    }
}