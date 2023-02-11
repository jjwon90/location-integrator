package com.jwjung.location.search.adapter.out.remote.model;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationItemV1;
import org.apache.commons.lang3.StringUtils;

public record RemoteLocationItemV1(String placeName,
                                   String address) {
    public static RemoteLocationItemV1 convertKakaoLocationItem(KakaoLocationItemV1 v1) {
        String address = StringUtils.isEmpty(v1.roadAddressName()) ? v1.addressName() : v1.roadAddressName();
        return new RemoteLocationItemV1(v1.placeName(), address);
    }

    public static RemoteLocationItemV1 convertNaverLocationItem(NaverLocationItemV1 v1) {
        String address = StringUtils.isEmpty(v1.roadAddress()) ? v1.address() : v1.roadAddress();
        return new RemoteLocationItemV1(v1.getEscapingTitle(), address);
    }
}
