package com.jwjung.location.domain.item;

import com.jwjung.location.remote.kakao.dto.KakaoMapItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@Builder
public class MapItems {
    List<MapItem> itemList;

    public static MapItems of(List<NaverMapItemV1> naverMapItems,
                              List<KakaoMapItemV1> kakaoMapItems) {
        List<MapItem> kakaoList = kakaoMapItems.stream()
                .map(k -> new MapItem(k.placeName(), true))
                .filter(distinctByKey(MapItem::getFirstWordOfName))
                .toList();

        List<MapItem> naverList = naverMapItems.stream()
                .map(n -> new MapItem(n.title(), false))
                .filter(distinctByKey(MapItem::getFirstWordOfName))
                .toList();

        Map<Boolean, List<MapItem>> mappedByFromKakao = Stream.concat(kakaoList.stream(), naverList.stream())
                .collect(Collectors.groupingBy(MapItem::kakao));

        return MapItems.builder()
                .itemList(reorderingList(mappedByFromKakao))
                .build();
    }

    public static MapItems naverMapItemsOf(List<NaverMapItemV1> naverMapItems) {
        List<MapItem> naverMapList = naverMapItems.stream()
                .map(n -> new MapItem(n.title(), false))
                .filter(distinctByKey(MapItem::getFirstWordOfName))
                .toList();

        return MapItems.builder()
                .itemList(naverMapList)
                .build();
    }

    public static MapItems kakaoMapItemsOf(List<KakaoMapItemV1> kakaoMapItemV1List) {
        List<MapItem> kakaoList = kakaoMapItemV1List.stream()
                .map(k -> new MapItem(k.placeName(), true))
                .filter(distinctByKey(MapItem::getFirstWordOfName))
                .toList();

        return MapItems.builder()
                .itemList(kakaoList)
                .build();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new HashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static List<MapItem> reorderingList(Map<Boolean, List<MapItem>> mappedByFromKakao) {
        List<MapItem> kakaoMapItems = new ArrayList<>(mappedByFromKakao.get(true));
        List<MapItem> naverMapItems = new ArrayList<>(mappedByFromKakao.get(false));

        Iterator<MapItem> kakaoIterator = kakaoMapItems.iterator();
        Iterator<MapItem> naverIterator = naverMapItems.iterator();

        List<MapItem> resultList = new ArrayList<>();

        while (kakaoIterator.hasNext()) {
            MapItem kakaoItem = kakaoIterator.next();
            while (naverIterator.hasNext()) {
                MapItem naverItem = naverIterator.next();

                if (StringUtils.equals(kakaoItem.getFirstWordOfName(), naverItem.getFirstWordOfName())) {
                    resultList.add(kakaoItem);

                    kakaoIterator.remove();
                    naverIterator.remove();
                    break;
                }
            }

            if (kakaoIterator.hasNext()) {
                naverIterator = naverMapItems.listIterator();
            }
        }

        if (resultList.size() <= 10) {
            if (CollectionUtils.isNotEmpty(kakaoMapItems)) {
                resultList.addAll(kakaoMapItems);
            }

            if (resultList.size() <= 10 && CollectionUtils.isNotEmpty(naverMapItems)) {
                resultList.addAll(naverMapItems);
            }
        }

        return resultList;
    }
}
