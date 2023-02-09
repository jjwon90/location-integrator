package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoMapResponseV1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Disabled
class KakaoMapClientTest {
    @Autowired
    private KakaoMapClient kakaoMapClient;

    @Test
    void getKakaoLocationSearch() {
        Optional<KakaoMapResponseV1> 은행 =
                kakaoMapClient.getKakaoLocationSearch("은행");
        System.out.println(은행);
    }
}