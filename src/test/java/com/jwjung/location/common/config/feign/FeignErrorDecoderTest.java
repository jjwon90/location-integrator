package com.jwjung.location.common.config.feign;

import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FeignErrorDecoderTest {
    private FeignErrorDecoder errorDecoder;
    private Map<String, Collection<String>> header;

    @BeforeEach
    void setup() {
        header = new HashMap<>();
        header.put("fake", Collections.singletonList("header"));
        this.errorDecoder = new FeignErrorDecoder(new ErrorDecoder.Default());
    }

    @Test
    void decode4XX_TEST() {
        Response response = Response.builder()
                .request(Request.create(Request.HttpMethod.GET, "/fake", header, Request.Body.empty(), null))
                .status(400)
                .reason("Not Found")
                .headers(Collections.emptyMap())
                .body("Not Found Resource", StandardCharsets.UTF_8)
                .build();

        Exception e = errorDecoder.decode("400-Error-Method", response);
        assertThat(e).isInstanceOf(FeignException.FeignClientException.class);
        assertThat(((FeignException.FeignClientException) e).status()).isEqualTo(400);
    }

    @Test
    void decode5XX_TEST() {
        Response response = Response.builder()
                .request(Request.create(Request.HttpMethod.GET, "/fake", header, Request.Body.empty(), null))
                .status(500)
                .reason("Internal Server Error")
                .headers(Collections.emptyMap())
                .body("Internal Server Error", StandardCharsets.UTF_8)
                .build();

        Exception e = errorDecoder.decode("500-Error-Method", response);
        assertThat(e).isInstanceOf(FeignException.class);
        assertThat(((FeignException) e).status()).isEqualTo(500);
    }
}