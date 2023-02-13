package com.jwjung.location.search.adapter.out.event;

import com.jwjung.location.popular.adapter.in.event.PopularEventAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@MockitoSettings
class PopularAdapterTest {
    @InjectMocks
    PopularAdapter popularAdapter;
    @Mock
    PopularEventAdapter popularEventAdapter;

    @Test
    @DisplayName("병렬 호출 시에도 제대로 다 호출이 되었는지 확인")
    void producePopularEvent__Test() throws InterruptedException {
        ExecutorService e = Executors.newFixedThreadPool(50);
        CountDownLatch countDownLatch = new CountDownLatch(50);

        doNothing().when(popularEventAdapter).updatePopularCount(anyString());
        for (int i = 0; i < 50; i++) {
            e.execute(() -> {
                popularAdapter.producePopularEvent("test1");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        verify(popularEventAdapter, Mockito.timeout(100).atLeast(50))
                .updatePopularCount(anyString());
    }
}