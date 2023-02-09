package com.jwjung.location.domain.location;

import com.jwjung.location.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationItemsTest {
    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 없고 두 api에도 중복 없는 경우 테스트")
    void testByConstruct__normal_input() {
        LocationItems locationItems = LocationItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("우리은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("신한은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "경남은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "제주은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "광주은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = locationItems.getItemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(10, locationItems.getItemList().size());
        assertEquals("부산은행 본점, 경남은행 본점, 제주은행 본점, 광주은행 본점, 씨티은행 본점, 국민은행 본점, 카카오은행 본점, 우리은행 본점, 신한은행 본점, 하나은행 본점",
                collect);
    }

    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 있고 두 api에는 중복 없는 경우 테스트")
    void testByConstruct__each_has_dup__both_has_not_dup() {
        LocationItems locationItems = LocationItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("국민은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("신한은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "경남은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 울산점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "광주은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = locationItems.getItemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(8, locationItems.getItemList().size());
        assertEquals("부산은행 본점, 경남은행 본점, 광주은행 본점, 씨티은행 본점, 국민은행 본점, 카카오은행 본점, 신한은행 본점, 하나은행 본점",
                collect);
    }

    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 없고 두 api에는 중복 있는 경우 테스트")
    void testByConstruct__each_has_not_dup__both_has_dup() {
        LocationItems locationItems = LocationItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("우리은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("신한은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "경남은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "우리은행 판교역점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "하나은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = locationItems.getItemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(8, locationItems.getItemList().size());
        assertEquals("우리은행 판교역점, 하나은행 본점, 부산은행 본점, 경남은행 본점, 씨티은행 본점, 국민은행 본점, 카카오은행 본점, 신한은행 본점",
                collect);
    }

    @Test
    @DisplayName("일반적으로 두 api 들어올 경우 각각 api데이터에 중복 업체 있고 두 api에는 중복 있는 경우 테스트")
    void testByConstruct__each_has_dup__both_has_dup() {
        LocationItems locationItems = LocationItems.of(List.of(new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("우리은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("국민은행 판교역점", "", "", "", "", "", "", "", ""),
                        new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")),
                List.of(new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "국민은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "우리은행 판교역점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "우리은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        String collect = locationItems.getItemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(6, locationItems.getItemList().size());
        assertEquals("국민은행 본점, 우리은행 판교역점, 부산은행 본점, 씨티은행 본점, 카카오은행 본점, 하나은행 본점",
                collect);
    }

    @Test
    @DisplayName("카카오에서만 응답이 온 경우 데이터는 중복 상점 없는 경우")
    void kakao_only_inputs__no_dup() {
        LocationItems locationItems = LocationItems.kakaoMapItemsOf(
                List.of(
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "국민은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "하나은행 판교역점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "우리은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        List<LocationItem> itemList = locationItems.getItemList();

        assertEquals(5, itemList.size());

        String collect = itemList.stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("부산은행 본점, 국민은행 본점, 하나은행 판교역점, 우리은행 본점, 씨티은행 본점",
                collect);
    }

    @Test
    @DisplayName("네이버에서만 데이터가 있고, 중복 상점이 없는 경우")
    void naver_only_inputs__no_dup() {
        LocationItems locationItems = LocationItems.naverMapItemsOf(List.of(
                new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("우리은행 판교역점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("NH농협은행 판교역점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")));

        List<LocationItem> itemList = locationItems.getItemList();
        assertEquals(5, itemList.size());

        String collect = itemList.stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("국민은행 본점, 카카오은행 본점, 우리은행 판교역점, NH농협은행 판교역점, 하나은행 본점",
                collect);
    }

    @Test
    @DisplayName("카카오에서만 데이터가 있고, 중복상점이 있는 경우")
    void kakao_only_input__has_dup() {
        LocationItems locationItems = LocationItems.kakaoMapItemsOf(
                List.of(
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "부산은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "국민은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "우리은행 판교역점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "우리은행 본점", "", "", "", ""),
                        new KakaoLocationItemV1("", "", "", "", "", "", "", "씨티은행 본점", "", "", "", "")));

        List<LocationItem> itemList = locationItems.getItemList();
        assertEquals(4, itemList.size());

        String collect = itemList.stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("부산은행 본점, 국민은행 본점, 우리은행 판교역점, 씨티은행 본점", collect);
    }

    @Test
    @DisplayName("네이버에서만 데이터 있고, 중복 상점이 있는 경우")
    void naver_only_input__has_dup() {
        LocationItems locationItems = LocationItems.naverMapItemsOf(List.of(
                new NaverMapItemV1("국민은행 본점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("카카오은행 본점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("우리은행 판교역점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("하나은행 판교역점", "", "", "", "", "", "", "", ""),
                new NaverMapItemV1("하나은행 본점", "", "", "", "", "", "", "", "")));

        List<LocationItem> itemList = locationItems.getItemList();

        assertEquals(4, itemList.size());

        String collect = itemList.stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("국민은행 본점, 카카오은행 본점, 우리은행 판교역점, 하나은행 판교역점", collect);
    }
}