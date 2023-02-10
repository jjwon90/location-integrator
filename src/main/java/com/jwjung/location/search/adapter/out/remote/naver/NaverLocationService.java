package com.jwjung.location.search.adapter.out.remote.naver;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;

public interface NaverLocationService {
    RemoteLocationItemsV1 getNaverItems(String query);
}
