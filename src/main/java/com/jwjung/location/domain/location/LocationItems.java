package com.jwjung.location.domain.location;

import com.jwjung.location.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.remote.naver.dto.NaverMapItemV1;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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
        // mutable한 list로 copy하여 계산용 list로 선언
        List<LocationItem> mutableKakaoList = new ArrayList<>(kakaoList);
        List<LocationItem> mutableNaverList = new ArrayList<>(naverList);

        List<LocationItem> resultList = new ArrayList<>();

        orderingDuplicateItems(kakaoList, naverList,
                mutableKakaoList, mutableNaverList,
                resultList);

        if (resultList.size() <= 10) {
            addRemainedItems(mutableKakaoList, mutableNaverList, resultList);
        }

        return resultList;
    }

    private static void orderingDuplicateItems(List<LocationItem> kakaoList,
                                               List<LocationItem> naverList,
                                               List<LocationItem> mutableKakaoList,
                                               List<LocationItem> mutableNaverList,
                                               List<LocationItem> resultList) {
        for (LocationItem kakaoItem : kakaoList) {
            for (LocationItem naverItem : naverList) {
                if (kakaoItem.isEqualItem(naverItem)) {
                    resultList.add(kakaoItem);

                    mutableKakaoList.remove(kakaoItem);
                    mutableNaverList.remove(naverItem);
                }
            }
        }
    }

    private static void addRemainedItems(List<LocationItem> kakaoLocationItems,
                                         List<LocationItem> naverLocationItems,
                                         List<LocationItem> resultList) {
        if (CollectionUtils.isNotEmpty(kakaoLocationItems)) {
            addResultList(kakaoLocationItems, resultList);
        }

        if (resultList.size() <= 10
                && CollectionUtils.isNotEmpty(naverLocationItems)) {
            addResultList(naverLocationItems, resultList);
        }
    }

    private static void addResultList(List<LocationItem> locationItems,
                                      List<LocationItem> resultList) {
        IntStream.range(0, locationItems.size())
                .forEach(i -> {
                    if (resultList.size() > 10) {
                        return;
                    }

                    resultList.add(locationItems.get(i));
                });
    }
}
