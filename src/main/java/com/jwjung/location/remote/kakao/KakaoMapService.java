package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoMapItemV1;

import java.util.List;

public interface KakaoMapService {
    List<KakaoMapItemV1> getKakaoMapItems(String query);
}
