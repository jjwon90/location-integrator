package com.jwjung.location.item;

import com.jwjung.location.domain.item.MapItem;
import com.jwjung.location.domain.item.MapItems;
import com.jwjung.location.remote.kakao.dto.KakaoMapItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapItemsTest {
    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 없고 두 api에도 중복 없는 경우 테스트")
    void testByConstruct__normal_input() {
        MapItems mapItems = MapItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("우리은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("신한은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoMapItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "경남은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "제주은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "광주은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = mapItems.getItemList()
                .stream()
                .map(MapItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("부산은행 본점, 경남은행 본점, 제주은행 본점, 광주은행 본점, 씨티은행 본점, 국민은행 본점, 카카오은행 본점, 우리은행 본점, 신한은행 본점, 하나은행 본점",
                collect);
    }

    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 있고 두 api에는 중복 없는 경우 테스트")
    void testByConstruct__each_has_dup__both_has_not_dup() {
        MapItems mapItems = MapItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("국민은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("신한은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoMapItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "경남은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "부산은행 울산점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "광주은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = mapItems.getItemList()
                .stream()
                .map(MapItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("부산은행 본점, 경남은행 본점, 광주은행 본점, 씨티은행 본점, 국민은행 본점, 카카오은행 본점, 신한은행 본점, 하나은행 본점",
                collect);
    }

    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 없고 두 api에는 중복 있는 경우 테스트")
    void testByConstruct__each_has_not_dup__both_has_dup() {
        MapItems mapItems = MapItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("우리은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("신한은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoMapItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "경남은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "우리은행 판교역점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "하나은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = mapItems.getItemList()
                .stream()
                .map(MapItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("우리은행 판교역점, 하나은행 본점, 부산은행 본점, 경남은행 본점, 씨티은행 본점, 국민은행 본점, 카카오은행 본점, 신한은행 본점",
                collect);
    }

    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 있고 두 api에는 중복 있는 경우 테스트")
    void testByConstruct__each_has_dup__both_has_dup() {
        MapItems mapItems = MapItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("우리은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("국민은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoMapItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "국민은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "우리은행 판교역점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "우리은행 본점", "", "", "", ""),
                        new KakaoMapItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = mapItems.getItemList()
                .stream()
                .map(MapItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("국민은행 본점, 우리은행 판교역점, 부산은행 본점, 씨티은행 본점, 카카오은행 본점, 하나은행 본점",
                collect);
    }
}