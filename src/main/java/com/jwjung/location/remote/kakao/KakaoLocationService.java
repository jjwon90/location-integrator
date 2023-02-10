package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.model.RemoteLocationItemsV1;

public interface KakaoLocationService {
    RemoteLocationItemsV1 getKakaoItems(String query);
}
