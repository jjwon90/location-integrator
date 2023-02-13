package com.jwjung.location.popular.data;

import com.jwjung.location.popular.application.port.in.PopularCommand;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockPopular {
    private static ConcurrentHashMap<String, Long> MOCK_MAP = new ConcurrentHashMap<>();

    public static void addQueryCount(PopularCommand command) {
        MOCK_MAP.merge(command.query(), 1L, Long::sum);
    }

    public static List<Map.Entry<String, Long>> getTopTenQuery() {
        if (MOCK_MAP.isEmpty()) {
            return Collections.emptyList();
        }

        return MOCK_MAP.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .toList();
    }

    public static void reset() {
        MOCK_MAP = new ConcurrentHashMap<>();
    }
}