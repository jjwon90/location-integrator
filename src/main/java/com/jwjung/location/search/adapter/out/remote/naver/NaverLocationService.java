package com.jwjung.location.search.adapter.out.remote.naver;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;

import java.util.concurrent.CompletableFuture;

public interface NaverLocationService {
    CompletableFuture<RemoteLocationItemsV1> getNaverItemsFuture(String query);
}
