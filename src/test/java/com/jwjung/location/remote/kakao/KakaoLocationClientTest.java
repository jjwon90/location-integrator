package com.jwjung.location.remote.kakao;

import com.jwjung.location.search.adapter.out.remote.kakao.KakaoLocationClient;
import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Disabled
class KakaoLocationClientTest {
    @Autowired
    private KakaoLocationClient kakaoLocationClient;

    @Test
    void getKakaoLocationSearch() {
        Optional<KakaoLocationResponseV1> 은행 =
                kakaoLocationClient.getKakaoLocationSearch("은행");
        System.out.println(은행);
    }
}