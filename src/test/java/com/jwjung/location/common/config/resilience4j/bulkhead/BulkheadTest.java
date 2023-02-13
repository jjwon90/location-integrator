package com.jwjung.location.common.config.resilience4j.bulkhead;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

public class BulkheadTest {
    private ThreadPoolBulkheadConfig config;
    private TestService testService;

    @BeforeEach
    void setup() {
        testService = Mockito.mock(TestService.class);
        config = ThreadPoolBulkheadConfig.custom()
                .coreThreadPoolSize(1)
                .maxThreadPoolSize(1)
                .queueCapacity(1)
                .build();
    }

    @Test
    @DisplayName("bulkhead가 꽉 찼을 때 확인")
    public void fail__BulkHeadFull() throws InterruptedException {
        ThreadPoolBulkhead bulkhead = ThreadPoolBulkhead.of("test", config);
        given(testService.returnHelloWorld()).willReturn("Hello world");
        final AtomicReference<Exception> exception = new AtomicReference<>();

        Thread first = new Thread(() -> {
            try {
                bulkhead.executeRunnable(() -> Try.run(() -> Thread.sleep(200)));
            } catch (Exception e) {
                exception.set(e);
            }

        });
        first.start();

        Thread second = new Thread(() -> {
            try {
                bulkhead.executeRunnable(testService::returnHelloWorld);
            } catch (Exception e) {
                exception.set(e);
            }
        });
        second.start();

        Thread third = new Thread(() -> {
            try {
                bulkhead.executeRunnable(testService::returnHelloWorld);
            } catch (Exception e) {
                exception.set(e);
            }
        });
        third.start();

        first.join(100);
        second.join(100);
        third.join(100);

        assertThat(exception.get()).isInstanceOf(BulkheadFullException.class);
    }


    static class TestService {
        String returnHelloWorld() {
            return "tt";
        }
    }
}


