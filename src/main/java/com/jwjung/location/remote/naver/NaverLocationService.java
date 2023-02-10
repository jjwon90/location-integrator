package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.model.RemoteLocationItemsV1;

public interface NaverLocationService {
    RemoteLocationItemsV1 getNaverItems(String query);
}
