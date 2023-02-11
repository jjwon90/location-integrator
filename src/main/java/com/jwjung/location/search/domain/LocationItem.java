package com.jwjung.location.search.domain;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public record LocationItem(String name,
                           String address,
                           MetroPolitanType metroEnum) {
    public static LocationItem of(String name, String address) {
        MetroPolitanType metro = MetroPolitanType.getMetroPolitanEnum(address.split(" ")[0]);
        return new LocationItem(name, address, metro);
    }

    public boolean isEqualLocation(LocationItem anotherItem) {
        // [2]가 읍면구일 경우 [4]까지 확인
        // 그 외의 경우는 [3] 확인
        if (!this.equalMetropolitan(anotherItem.metroEnum())) {
            return false;
        }

        int borderIndex = this.getSearchBorderIndex();

        boolean isEqualAddress = IntStream.rangeClosed(1, borderIndex)
                .mapToObj(i -> addressEqualCheck(anotherItem, i))
                .filter(b -> !b)
                .findAny()
                .orElse(true);

        if (isEqualAddress) {
            return StringUtils.equals(this.name(), anotherItem.name());
        }

        return false;
    }

    private boolean addressEqualCheck(LocationItem anotherItem, int i) {
        String[] originAddressArray = address().split(" ");
        String[] anotherAddressArray = anotherItem.address().split(" ");

        return StringUtils.equals(ArrayUtils.get(originAddressArray, i, "a"),
                ArrayUtils.get(anotherAddressArray, i, "b"));
    }

    private boolean equalMetropolitan(MetroPolitanType m2) {
        return this.metroEnum() == m2;
    }

    private int getSearchBorderIndex() {
        String basicTownName = this.getBasicTownName();

        String basicTownUnit = basicTownName.substring(basicTownName.length() - 1);

        return switch (basicTownUnit) {
            case "읍", "면", "구" -> 4;
            default -> 3;
        };
    }

    private String getBasicTownName() {
        return this.address().split(" ")[2];
    }
}
