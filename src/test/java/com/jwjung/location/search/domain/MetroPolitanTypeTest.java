package com.jwjung.location.search.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class MetroPolitanTypeTest {

    @Test
    @DisplayName("제주특별자치도, 제주 테스트")
    void getMetroPolitanEnum() {
        MetroPolitanType shortJeju = MetroPolitanType.getMetroPolitanEnum("제주");
        MetroPolitanType longJeju = MetroPolitanType.getMetroPolitanEnum("제주특별자치도");

        assertSame(shortJeju, longJeju);
    }
}