package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.model.RemoteLocationItemsV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoLocationServiceImpl implements KakaoLocationService {
    private final KakaoLocationClient kakaoLocationClient;

    @Override
    public RemoteLocationItemsV1 getKakaoItems(String query) {
        return kakaoLocationClient.getKakaoLocationSearch(query)
                .map(RemoteLocationItemsV1::ofKakaoItems)
                .orElseGet(RemoteLocationItemsV1::emptyOf);
    }
}
