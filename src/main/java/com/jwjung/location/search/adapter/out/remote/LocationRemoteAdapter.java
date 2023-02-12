package com.jwjung.location.search.adapter.out.remote;

import com.jwjung.location.search.adapter.out.remote.kakao.KakaoLocationService;
import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import com.jwjung.location.search.adapter.out.remote.naver.NaverLocationService;
import com.jwjung.location.search.application.port.out.GetLocationPort;
import com.jwjung.location.search.domain.LocationItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LocationRemoteAdapter implements GetLocationPort {
    private final KakaoLocationService kakaoLocationService;
    private final NaverLocationService naverLocationService;

    @Override
    public LocationItems getLocationItems(String query) {
        var kakaoFuture = kakaoLocationService.getKakaoItemsFuture(query);

        var naverFuture = naverLocationService.getNaverItemsFuture(query);

        return CompletableFuture.allOf(kakaoFuture, naverFuture)
                .thenApply(Void -> LocationItems.of(kakaoFuture.join(), naverFuture.join()))
                .join();
    }
}
