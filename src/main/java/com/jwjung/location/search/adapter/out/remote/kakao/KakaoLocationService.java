package com.jwjung.location.search.adapter.out.remote.kakao;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;

import java.util.concurrent.CompletableFuture;

public interface KakaoLocationService {
    CompletableFuture<RemoteLocationItemsV1> getKakaoItemsFuture(String query);
}
