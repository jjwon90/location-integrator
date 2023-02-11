package com.jwjung.location.popular.data;

import com.jwjung.location.popular.application.port.in.PopularCommand;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Popular {
    private static final ConcurrentHashMap<String, Long> POPULAR_QUERY_DATA = new ConcurrentHashMap<>();

    public static void addQueryCount(PopularCommand command) {
        POPULAR_QUERY_DATA.merge(command.query(), 1L, Long::sum);
    }

    public static List<Map.Entry<String, Long>> getTopTenQuery() {
        if (POPULAR_QUERY_DATA.isEmpty()) {
            return Collections.emptyList();
        }

        return POPULAR_QUERY_DATA.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .toList();
    }
}
