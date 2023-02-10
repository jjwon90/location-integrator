package com.jwjung.location.remote.model;

import com.jwjung.location.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.remote.naver.dto.NaverLocationItemV1;

public record RemoteLocationItemV1(String placeName) {
    public static RemoteLocationItemV1 convertKakaoLocationItem(KakaoLocationItemV1 v1) {
        return new RemoteLocationItemV1(v1.placeName());
    }

    public static RemoteLocationItemV1 convertNaverLocationItem(NaverLocationItemV1 v1) {
        return new RemoteLocationItemV1(v1.getEscapingTitle());
    }
}
