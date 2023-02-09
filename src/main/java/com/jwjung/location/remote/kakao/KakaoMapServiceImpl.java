package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoMapItemV1;
import com.jwjung.location.remote.kakao.dto.KakaoMapResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoMapServiceImpl implements KakaoMapService {
    private final KakaoMapClient kakaoMapClient;

    @Override
    public List<KakaoMapItemV1> getKakaoMapItems(String query) {
        return kakaoMapClient.getKakaoLocationSearch(query)
                .map(KakaoMapResponseV1::documents)
                .orElseGet(Collections::emptyList);
    }
}
