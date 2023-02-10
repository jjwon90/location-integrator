package com.jwjung.location.search.adapter.out.remote.kakao;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;

public interface KakaoLocationService {
    RemoteLocationItemsV1 getKakaoItems(String query);
}
