package com.jwjung.location.common.config.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CircuitBreakerTest.CircuitBreakerApplication.class)
@Execution(ExecutionMode.SAME_THREAD)
public class CircuitBreakerTest {
    public static final String TEST_BREAKER = "TestBreaker";
    @Autowired
    CircuitBreakerApplication.TestService testService;
    @MockBean
    CircuitBreakerApplication.TestAnotherComponent testAnotherComponent;

    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Test
    void normalCaseTest() throws Exception {
        circuitBreakerRegistry.circuitBreaker(TEST_BREAKER)
                .transitionToClosedState();

        when(testAnotherComponent.testAnother())
                .thenReturn("test");
        assertEquals("test", testService.testBreaker());
    }

    @Test
    void getException__WhenCircuitBreakerIsClosed__ButBackendTimesOut() throws Exception {
        circuitBreakerRegistry.circuitBreaker(TEST_BREAKER)
                .transitionToClosedState();
        when(testAnotherComponent.testAnother()).thenThrow(new TimeoutException(""));

        try {
            testService.testBreaker();
            fail("expect Exception");
        } catch (Exception e) {
            assertEquals(TimeoutException.class, e.getClass());
            verify(testAnotherComponent, times(1)).testAnother();
        }
    }

    @Test
    void getException__WhenCircuitBreakerIsOpen() throws Exception {
        circuitBreakerRegistry.circuitBreaker(TEST_BREAKER)
                .transitionToOpenState();
        when(testAnotherComponent.testAnother()).thenThrow(new TimeoutException(""));

        try {
            testService.testBreaker();
            fail("expect exception");
        } catch (Exception e) {
            assertEquals(CallNotPermittedException.class, e.getClass());
            verifyNoInteractions(testAnotherComponent);
        }
    }

    @SpringBootApplication
    static
    class CircuitBreakerApplication {
        public static void main(String[] args) {
            SpringApplication.run(CircuitBreakerApplication.class, args);
        }

        @Service
        @RequiredArgsConstructor
        static class TestService {
            private final TestAnotherComponent testAnotherComponent;

            @CircuitBreaker(name = TEST_BREAKER)
            public String testBreaker() throws Exception {
                return testAnotherComponent.testAnother();
            }
        }

        @Component
        static class TestAnotherComponent {
            public String testAnother() throws Exception {
                return "test";
            }
        }
    }
}


