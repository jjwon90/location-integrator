package com.jwjung.location.search.application.domain;

import com.jwjung.location.search.domain.LocationItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationItemTest {
    // 시군구 주소 테스트 할 때, 오류없이 제대로 검사하는 지 테스트
    // - 광역단체 -> 시,군,구 -> "읍", "면", "구" 인 경우(ex. 경기도 용인시 기흥구)
    // - 광역단체 -> 시,군,구 -> "읍", "면", "구" 아닌 경우(ex. 경기도 화성시 )
    @Test
    @DisplayName("읍, 면, 구가 있는 자치단체에 지점인 경우 테스트")
    void isEqualLocation__has_over_basic() {
        LocationItem locationItem = LocationItem.of("KB국민은행 동백지점", "경기 용인시 기흥구 동백중앙로 283");
        LocationItem anotherItem = LocationItem.of("KB국민은행 동백지점", "경기도 용인시 기흥구 동백중앙로 283");

        assertTrue(locationItem.isEqualLocation(anotherItem));
    }

    @Test
    @DisplayName("읍, 면, 구가 없는 자치단체 지점인 경우 테스트")
    void isEqualLocation__has_not_basic() {
        LocationItem locationItem = LocationItem.of("우리은행 동탄역금융센터", "경기도 화성시 동탄오산로 86-3 동탄MK타워 2층");
        LocationItem anotherItem = LocationItem.of("우리은행 동탄역금융센터", "경기 화성시 동탄오산로 86-3");

        locationItem.isEqualLocation(anotherItem);
    }

    // 서울, 서울특별시
    @Test
    @DisplayName("광역자치단체가 제대로 매핑되는지 테스트")
    void metroPolitanType__logic_test() {
        LocationItem shortName = LocationItem.of("테스트1", "서울 중구 세종대로 101");
        LocationItem longName = LocationItem.of("테스트2", "서울특별시 중구 세종대로 101");

        assertSame(shortName.metroEnum(), longName.metroEnum());
    }

}