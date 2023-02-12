package com.jwjung.location.popular.data;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopularTest {

    @Test
    @DisplayName("동시에 들어왔을 때, 카운트 보장 여부 확인")
    void addQueryCount() throws InterruptedException {
        ExecutorService e = Executors.newFixedThreadPool(50);
        CountDownLatch countDownLatch = new CountDownLatch(50);

        for (int i = 0; i < 50; i++) {
            e.execute(() -> {
                Popular.addQueryCount(new PopularCommand("test"));
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        List<Map.Entry<String, Long>> topTenQuery = Popular.getTopTenQuery();

        assertEquals(50, topTenQuery.get(0).getValue());
    }

    @Test
    @DisplayName("top 10 list 제대로 보이는지 확인")
    void getTopTenQuery() {
        setup_topTen();

        List<Map.Entry<String, Long>> topTenQuery = Popular.getTopTenQuery();
        assertEquals(10, topTenQuery.size());

        String collect = topTenQuery.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));

        assertEquals("test1, test2, test3, test4, test5, test6, test7, test8, test9, test10", collect);
    }

    void setup_topTen() {
        for (int i = 0; i < 19; i++) {
            Popular.addQueryCount(new PopularCommand("test1"));
        }

        for (int i = 0; i < 11; i++) {
            Popular.addQueryCount(new PopularCommand("test2"));
        }

        for (int i = 0; i< 10; i++) {
            Popular.addQueryCount(new PopularCommand("test3"));
        }

        for (int i = 0; i< 9; i++) {
            Popular.addQueryCount(new PopularCommand("test4"));
        }

        for (int i = 0; i< 8; i++) {
            Popular.addQueryCount(new PopularCommand("test5"));
        }

        for (int i = 0; i< 7; i++) {
            Popular.addQueryCount(new PopularCommand("test6"));
        }

        for (int i = 0; i< 6; i++) {
            Popular.addQueryCount(new PopularCommand("test7"));
        }

        for (int i = 0; i< 5; i++) {
            Popular.addQueryCount(new PopularCommand("test8"));
        }

        for (int i = 0; i< 4; i++) {
            Popular.addQueryCount(new PopularCommand("test9"));
        }

        for (int i = 0; i< 3; i++) {
            Popular.addQueryCount(new PopularCommand("test10"));
        }

        for (int i = 0; i< 2; i++) {
            Popular.addQueryCount(new PopularCommand("test11"));
        }

        for (int i = 0; i< 1; i++) {
            Popular.addQueryCount(new PopularCommand("test12"));
        }
    }
}