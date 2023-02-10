package com.jwjung.location.search.adapter.out.remote.model;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationItemV1;

public record RemoteLocationItemV1(String placeName,
                                   String roadAddress) {
    public static RemoteLocationItemV1 convertKakaoLocationItem(KakaoLocationItemV1 v1) {
        return new RemoteLocationItemV1(v1.placeName(), v1.roadAddressName());
    }

    public static RemoteLocationItemV1 convertNaverLocationItem(NaverLocationItemV1 v1) {
        return new RemoteLocationItemV1(v1.getEscapingTitle(), v1.roadAddress());
    }
}
