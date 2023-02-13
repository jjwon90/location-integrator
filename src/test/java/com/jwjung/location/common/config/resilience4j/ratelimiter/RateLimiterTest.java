package com.jwjung.location.common.config.resilience4j.ratelimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class RateLimiterTest {
    private static final int LIMIT = 50;
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final Duration REFRESH_PERIOD = Duration.ofNanos(500);

    private RateLimiterConfig config;
    private RateLimiter limit;

    @BeforeEach
    void init() {
        this.config = RateLimiterConfig.custom()
                .timeoutDuration(TIMEOUT)
                .limitRefreshPeriod(REFRESH_PERIOD)
                .limitForPeriod(LIMIT)
                .writableStackTraceEnabled(true)
                .build();
        limit = mock(RateLimiter.class);
        given(limit.getRateLimiterConfig()).willReturn(config);
    }

    @Test
    @DisplayName("RateLimiter에 의해 request안되도록 제한")
    public void decorateFutureFailure() {
        Supplier<String> supplier = mock(Supplier.class);
        given(supplier.get()).willReturn("Resource");
        Supplier<Future<String>> decoratedFuture =
                RateLimiter.decorateFuture(limit, () -> supplyAsync(supplier));
        given(limit.acquirePermission(1)).willReturn(false);

        assertThrows(RequestNotPermitted.class, () -> decoratedFuture.get().get(2, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("정상 적으로 호출되는지 확인")
    public void decorateFutureSuccess()
            throws ExecutionException, InterruptedException, TimeoutException {
        Supplier<String> supplier = mock(Supplier.class);
        given(supplier.get()).willReturn("Resource");
        Supplier<Future<String>> decoratedFuture =
                RateLimiter.decorateFuture(limit, () -> supplyAsync(supplier));
        given(limit.acquirePermission(1)).willReturn(true);

        String result = decoratedFuture.get().get(2, TimeUnit.SECONDS);

        then(supplier).should().get();
        assertThat(result).isEqualTo("Resource");
    }

    @Test
    @DisplayName("permission wait 확인")
    public void waitForPermissionWithOne() {
        given(limit.acquirePermission(1)).willReturn(true);

        RateLimiter.waitForPermission(limit);

        then(limit).should().acquirePermission(1);
    }

    @Test
    @DisplayName("permission 얻는 것을 실패하는 경우 확인")
    public void waitForPermissionWithoutOne() {
        given(limit.acquirePermission(1)).willReturn(false);

        assertThrows(RequestNotPermitted.class, () -> {
            RateLimiter.waitForPermission(limit);
            then(limit).should().acquirePermission(1);
        });
    }
}
