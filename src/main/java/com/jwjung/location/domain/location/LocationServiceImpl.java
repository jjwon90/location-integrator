package com.jwjung.location.domain.location;

import com.jwjung.location.remote.kakao.KakaoLocationService;
import com.jwjung.location.remote.naver.NaverLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
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
