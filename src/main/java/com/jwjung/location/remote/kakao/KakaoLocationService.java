package com.jwjung.location.remote.kakao;

import com.jwjung.location.remote.kakao.dto.KakaoLocationItemV1;

import java.util.List;

public interface KakaoLocationService {
    List<KakaoLocationItemV1> getKakaoMapItems(String query);
}
