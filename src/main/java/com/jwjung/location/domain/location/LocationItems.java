package com.jwjung.location.domain.location;

import com.jwjung.location.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Value
@Builder
public class LocationItems {
    List<LocationItem> itemList;

    public static LocationItems of(List<NaverMapItemV1> naverMapItems,
                                   List<KakaoLocationItemV1> kakaoMapItems) {
        List<LocationItem> kakaoList = kakaoMapItems.stream()
                .map(k -> new LocationItem(k.placeName()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        List<LocationItem> naverList = naverMapItems.stream()
                .map(n -> new LocationItem(n.title()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        return LocationItems.builder()
                .itemList(reorderingList(kakaoList, naverList))
                .build();
    }

    public static LocationItems naverMapItemsOf(List<NaverMapItemV1> naverMapItems) {
        List<LocationItem> naverMapList = naverMapItems.stream()
                .map(n -> new LocationItem(n.title()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        return LocationItems.builder()
                .itemList(naverMapList)
                .build();
    }

    public static LocationItems kakaoMapItemsOf(List<KakaoLocationItemV1> kakaoLocationItemV1List) {
        List<LocationItem> kakaoList = kakaoLocationItemV1List.stream()
                .map(k -> new LocationItem(k.placeName()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        return LocationItems.builder()
                .itemList(kakaoList)
                .build();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new HashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static List<LocationItem> reorderingList(List<LocationItem> kakaoList,
                                                     List<LocationItem> naverList) {
        List<LocationItem> kakaoLocationItems = new ArrayList<>(kakaoList);
        List<LocationItem> naverLocationItems = new ArrayList<>(naverList);

        Iterator<LocationItem> kakaoIterator = kakaoLocationItems.iterator();
        Iterator<LocationItem> naverIterator = naverLocationItems.iterator();

        List<LocationItem> resultList = new ArrayList<>();

        while (kakaoIterator.hasNext()) {
            LocationItem kakaoItem = kakaoIterator.next();
            while (naverIterator.hasNext()) {
                LocationItem naverItem = naverIterator.next();

                if (kakaoItem.isEqualItem(naverItem)) {
                    resultList.add(kakaoItem);

                    kakaoIterator.remove();
                    naverIterator.remove();
                    break;
                }
            }

            if (kakaoIterator.hasNext()) {
                naverIterator = naverLocationItems.listIterator();
            }
        }

        if (resultList.size() <= 10) {
            if (CollectionUtils.isNotEmpty(kakaoLocationItems)) {
                resultList.addAll(kakaoLocationItems);
            }

            if (resultList.size() <= 10 && CollectionUtils.isNotEmpty(naverLocationItems)) {
                resultList.addAll(naverLocationItems);
            }
        }

        return resultList;
    }
}
