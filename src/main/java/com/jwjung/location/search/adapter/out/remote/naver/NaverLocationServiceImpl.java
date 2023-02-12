package com.jwjung.location.search.adapter.out.remote.naver;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class NaverLocationServiceImpl implements NaverLocationService {
    private final NaverLocationClient naverLocationClient;
    private final ExecutorService asyncLocationSearcher;

    @Override
    public CompletableFuture<RemoteLocationItemsV1> getNaverItemsFuture(String query) {
        return CompletableFuture.supplyAsync(() -> naverLocationClient.getNaverLocationSearch(query)
                        .map(RemoteLocationItemsV1::ofNaverItems)
                        .orElseGet(RemoteLocationItemsV1::emptyOf), asyncLocationSearcher)
                .completeOnTimeout(RemoteLocationItemsV1.emptyOf(), 300, TimeUnit.MILLISECONDS)
                .exceptionally((t) -> RemoteLocationItemsV1.emptyOf());
    }
}
