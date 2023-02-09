package com.jwjung.location.item;

import com.jwjung.location.domain.item.MapItem;
import com.jwjung.location.domain.item.MapItems;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapItemsTest {

    @Test
    void reorderingList() {
        Map<Boolean, List<MapItem>> testData = Map.of(true, new ArrayList<>(List.of(
                        new MapItem("신한은행", true),
                        new MapItem("카카오뱅크", true),
                        new MapItem("우리은행", true),
                        new MapItem("국민은행", true),
                        new MapItem("부산은행", true))),
                false, new ArrayList<>(List.of(
                        new MapItem("새마을금고", false),
                        new MapItem("카카오뱅크", false),
                        new MapItem("광주은행", false),
                        new MapItem("국민은행", false),
                        new MapItem("하나은행", false))));

        List<MapItem> mapItems = MapItems.reorderingList(testData);

        assertEquals(List.of(new MapItem("카카오뱅크", true),
                new MapItem("국민은행", true),
                new MapItem("신한은행", true),
                new MapItem("우리은행", true),
                new MapItem("부산은행", true),
                new MapItem("새마을금고", false),
                new MapItem("광주은행", false),
                new MapItem("하나은행", false)
        ), mapItems);
    }
}