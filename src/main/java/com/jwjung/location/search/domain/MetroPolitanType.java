package com.jwjung.location.search.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MetroPolitanType {
    서울("서울", "서울특별시"),
    부산("부산", "부산광역시"),
    대구("대구", "대구광역시"),
    인천("인천", "인천광역시"),
    광주("광주", "광주광역시"),
    대전("대전", "대전광역시"),
    울산("울산", "울산광역시"),

    경기("경기", "경기도"),
    강원("강원", "강원도"),
    충북("충북", "충청북도"),
    충남("충남", "충청남도"),
    전북("전북", "전라북도"),
    전남("전남", "전라남도"),
    경북("경북", "경상북도"),
    경남("경남", "경상남도"),
    제주("제주", "제주특별자치도"),
    NONE("", "");

    private final String shortName;
    private final String longName;

    public static MetroPolitanType getMetroPolitanEnum(String s) {
        if (StringUtils.isEmpty(s)) {
            return NONE;
        }

        return Arrays.stream(values())
                .filter(m -> StringUtils.equals(m.getShortName(), s) || StringUtils.equals(m.getLongName(), s))
                .findAny()
                .orElse(NONE);
    }
}
