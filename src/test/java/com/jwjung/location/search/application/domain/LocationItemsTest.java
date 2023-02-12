package com.jwjung.location.search.application.domain;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationResponseV1;
import com.jwjung.location.search.domain.LocationItem;
import com.jwjung.location.search.domain.LocationItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationItemsTest {
    @Test
    @DisplayName("일반적으로 중복되지 않은 경우 제대로 순서 맞춰 나오는지 테스트")
    void not_dup_ordering_test() {
        RemoteLocationItemsV1 ofKakaoItems = RemoteLocationItemsV1.ofKakaoItems(new KakaoLocationResponseV1(List.of(
                new KakaoLocationItemV1("하나은행 본점", "서울 중구 을지로 35", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 여의도본점", "서울 영등포구 국제금융로8길 26", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 영등포지점", "서울 영등포구 영등포로 208", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("우리은행 본점", "서울 중구 소공로 51", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 강남파이낸스종합금융센터", "서울 강남구 테헤란로 152", "", "", "", "", "", "", "", "", "", ""))));

        RemoteLocationItemsV1 ofNaverItems = RemoteLocationItemsV1.ofNaverItems(new NaverLocationResponseV1(5, 0, 5,
                List.of(
                        new NaverLocationItemV1("부산은행 서울금융센터", "서울특별시 중구 무교로 6 (을지로1가)", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("신한은행 서울시청금융센터", "서울특별시 중구 태평로1가 31", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("KB국민은행 동대문패션타운", "서울특별시 중구 장충단로13길 34 동화상가 2층", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("IBK기업은행 본사", "서울특별시 중구 을지로 79", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("KB국민은행 명동영업부", "서울특별시 중구 을지로 51 (을지로2가, 교원내외빌딩)", "", "", "", "", "", "", ""))));

        LocationItems locationItems = LocationItems.of(ofKakaoItems, ofNaverItems);

        String collect = locationItems.itemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(10, locationItems.itemList().size());
        assertEquals("하나은행 본점, KB국민은행 여의도본점, KB국민은행 영등포지점, 우리은행 본점, KB국민은행 강남파이낸스종합금융센터, 부산은행 서울금융센터, 신한은행 서울시청금융센터, KB국민은행 동대문패션타운, IBK기업은행 본사, KB국민은행 명동영업부",
                collect);
    }

    @Test
    @DisplayName("중복되는 경우 중복 된 것이 앞으로 오는 것인지 테스트, 나머지 것들이 제대로 맞춰나오는지 테스트")
    void dup_ordering_test() {
        RemoteLocationItemsV1 ofKakaoItems = RemoteLocationItemsV1.ofKakaoItems(new KakaoLocationResponseV1(List.of(
                new KakaoLocationItemV1("하나은행 평촌꿈마을지점", "경기 안양시 동안구 신기대로 147", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("우리은행 인덕원금융센터", "경기 안양시 동안구 흥안대로 416", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("IBK기업은행 평촌남지점", "경기 안양시 동안구 흥안대로 415", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("NH농협은행 평촌지점", "경기 안양시 동안구 귀인로 206", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("우리은행 한림대학교성심병원", "경기 안양시 동안구 관평로170번길 22", "", "", "", "", "", "", "", "", "", ""))));

        RemoteLocationItemsV1 ofNaverItems = RemoteLocationItemsV1.ofNaverItems(new NaverLocationResponseV1(5, 0, 5,
                List.of(
                        new NaverLocationItemV1("KB국민은행 평촌범계종합금융센터", "경기도 안양시 동안구 시민대로 196", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("하나은행 평촌범계역지점", "경기도 안양시 동안구 시민대로 210", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("IBK기업은행 평촌아크로타워", "경기도 안양시 동안구 시민대로 230", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("하나은행 평촌역금융센터", "경기도 안양시 동안구 시민대로 286", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("NH농협은행 평촌지점", "경기도 안양시 동안구 귀인로 206", "", "", "", "", "", "", ""))));
        LocationItems locationItems = LocationItems.of(ofKakaoItems, ofNaverItems);
        assertEquals(9, locationItems.itemList().size());

        String collect = locationItems.itemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals("NH농협은행 평촌지점, 하나은행 평촌꿈마을지점, 우리은행 인덕원금융센터, IBK기업은행 평촌남지점, 우리은행 한림대학교성심병원, KB국민은행 평촌범계종합금융센터, 하나은행 평촌범계역지점, IBK기업은행 평촌아크로타워, 하나은행 평촌역금융센터",
                collect);
    }
}