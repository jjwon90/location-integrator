package com.jwjung.location.search.adapter.out.remote.model;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationResponseV1;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

public record RemoteLocationItemsV1(List<RemoteLocationItemV1> remoteLocationItemList) {
    public static RemoteLocationItemsV1 emptyOf() {
        return new RemoteLocationItemsV1(Collections.emptyList());
    }

    public static RemoteLocationItemsV1 ofKakaoItems(KakaoLocationResponseV1 v1) {
        if (v1 == null || CollectionUtils.isEmpty(v1.documents())) {
            return new RemoteLocationItemsV1(Collections.emptyList());
        }
        List<RemoteLocationItemV1> kakaoList = v1.documents().stream()
                .map(RemoteLocationItemV1::convertKakaoLocationItem)
                .toList();

        return new RemoteLocationItemsV1(kakaoList);
    }

    public static RemoteLocationItemsV1 ofNaverItems(NaverLocationResponseV1 v1) {
        if (v1 == null || CollectionUtils.isEmpty(v1.items())) {
            return new RemoteLocationItemsV1(Collections.emptyList());
        }
        List<RemoteLocationItemV1> naverList = v1.items().stream()
                .map(RemoteLocationItemV1::convertNaverLocationItem)
                .toList();

        return new RemoteLocationItemsV1(naverList);
    }

    public boolean isEmpty() {
        return this.remoteLocationItemList().isEmpty();
    }
}
