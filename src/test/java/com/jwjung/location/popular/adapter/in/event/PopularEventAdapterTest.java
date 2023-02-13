package com.jwjung.location.popular.adapter.in.event;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.application.port.in.UpdatePopularUseCase;
import com.jwjung.location.popular.data.MockPopular;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PopularEventAdapterTest {
    PopularEventAdapter popularEventAdapter;
    UpdatePopularUseCase updatePopularUseCase = new MockUpdatePopularUseCase();

    static class MockAsyncPopularEventAdapter extends PopularEventAdapter {
        public MockAsyncPopularEventAdapter(UpdatePopularUseCase updatePopularUseCase) {
            super(updatePopularUseCase);
        }

        @Override
        public void updatePopularCount(String query) {
            new Thread(() -> {
                try {
                    Thread.sleep(200L);
                    super.updatePopularCount(query);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    static class MockUpdatePopularUseCase implements UpdatePopularUseCase {
        @Override
        public void updatePopularCount(PopularCommand popularCommand) {
            MockPopular.addQueryCount(popularCommand);
        }
    }

    @Nested
    class Sync_test {
        @Test
        void updatePopularCommand() {
            popularEventAdapter = new PopularEventAdapter(updatePopularUseCase);
            popularEventAdapter.updatePopularCount("test1");

            assertEquals("test1", MockPopular.getTopTenQuery().get(0).getKey());
            assertEquals(1, MockPopular.getTopTenQuery().get(0).getValue());
        }

        @AfterEach
        void reset() {
            MockPopular.reset();
        }
    }

    @Nested
    class Async_TEST {
        @Test
        @DisplayName("병렬 호출 시 update 제대로 되는지 확인")
        void updatePopularCommand__ASYNC() throws InterruptedException {
            UpdatePopularUseCase spy = spy(updatePopularUseCase);
            PopularEventAdapter mockAsyncPopularEventAdapter = new MockAsyncPopularEventAdapter(spy);


            ExecutorService e = Executors.newFixedThreadPool(50);
            CountDownLatch countDownLatch = new CountDownLatch(50);

            for (int i = 0; i < 50; i++) {
                e.execute(() -> {
                    mockAsyncPopularEventAdapter.updatePopularCount("test2");
                    countDownLatch.countDown();
                });
            }

            countDownLatch.await();
            verify(spy, Mockito.timeout(1100L).atLeast(5)).updatePopularCount(any());
        }

        @AfterEach
        void reset() {
            MockPopular.reset();
        }
    }
}