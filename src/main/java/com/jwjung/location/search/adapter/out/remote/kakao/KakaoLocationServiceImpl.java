package com.jwjung.location.search.adapter.out.remote.kakao;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KakaoLocationServiceImpl implements KakaoLocationService {
    private final KakaoLocationClient kakaoLocationClient;
    private final ExecutorService asyncLocationSearcher;

    @Override
    public CompletableFuture<RemoteLocationItemsV1> getKakaoItemsFuture(String query) {
        return CompletableFuture.supplyAsync(() -> kakaoLocationClient.getKakaoLocationSearch(query)
                        .map(RemoteLocationItemsV1::ofKakaoItems)
                        .orElseGet(RemoteLocationItemsV1::emptyOf), asyncLocationSearcher)
                .completeOnTimeout(RemoteLocationItemsV1.emptyOf(), 300, TimeUnit.MILLISECONDS)
                .exceptionally((t) -> RemoteLocationItemsV1.emptyOf());
    }
}
