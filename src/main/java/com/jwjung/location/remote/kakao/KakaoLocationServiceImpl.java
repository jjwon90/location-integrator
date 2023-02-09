package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.remote.kakao.dto.KakaoLocationResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoLocationServiceImpl implements KakaoLocationService {
    private final KakaoLocationClient kakaoLocationClient;

    @Override
    public List<KakaoLocationItemV1> getKakaoMapItems(String query) {
        return kakaoLocationClient.getKakaoLocationSearch(query)
                .map(KakaoLocationResponseV1::documents)
                .orElseGet(Collections::emptyList);
    }
}
