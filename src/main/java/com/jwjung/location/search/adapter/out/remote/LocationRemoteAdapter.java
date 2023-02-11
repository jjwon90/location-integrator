package com.jwjung.location.search.adapter.out.remote;

import com.jwjung.location.search.adapter.out.remote.kakao.KakaoLocationService;
import com.jwjung.location.search.adapter.out.remote.naver.NaverLocationService;
import com.jwjung.location.search.domain.LocationItems;
import com.jwjung.location.search.application.port.out.GetLocationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LocationRemoteAdapter implements GetLocationPort {
    private final KakaoLocationService kakaoLocationService;
    private final NaverLocationService naverLocationService;
    private final ExecutorService asyncLocationSearcher;

    @Override
    public LocationItems getLocationItems(String query) {
        var kakaoFuture = CompletableFuture.supplyAsync(() -> kakaoLocationService.getKakaoItems(query),
                        asyncLocationSearcher)
                .orTimeout(300, TimeUnit.MILLISECONDS);

        var naverFuture = CompletableFuture.supplyAsync(() -> naverLocationService.getNaverItems(query),
                        asyncLocationSearcher)
                .orTimeout(300, TimeUnit.MILLISECONDS);

        return CompletableFuture.allOf(kakaoFuture, naverFuture)
                .thenApply(Void -> LocationItems.of(kakaoFuture.join(), naverFuture.join()))
                .join();
    }
}
