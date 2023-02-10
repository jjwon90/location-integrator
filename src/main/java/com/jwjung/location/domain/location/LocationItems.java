package com.jwjung.location.domain.location;

import com.jwjung.location.remote.model.RemoteLocationItemsV1;
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

    public static LocationItems of(RemoteLocationItemsV1 naverItems,
                                   RemoteLocationItemsV1 kakaoItems) {
        if (naverItems.isEmpty() && kakaoItems.isEmpty()) {
            return emptyOf();
        } else if (kakaoItems.isEmpty()) {
            return naverItemsOf(naverItems);
        } else if (naverItems.isEmpty()) {
            return kakaoItemsOf(kakaoItems);
        } else {
            return bothOf(kakaoItems, naverItems);
        }
    }

    private static LocationItems emptyOf() {
        return LocationItems.builder()
                .build();
    }

    private static LocationItems bothOf(RemoteLocationItemsV1 kakaoItems,
                                        RemoteLocationItemsV1 naverItems) {
        List<LocationItem> kakaoList = kakaoItems.remoteLocationItemList().stream()
                .map(k -> new LocationItem(k.placeName()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        List<LocationItem> naverList = naverItems.remoteLocationItemList().stream()
                .map(n -> new LocationItem(n.placeName()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        return LocationItems.builder()
                .itemList(reorderingList(kakaoList, naverList))
                .build();
    }

    private static LocationItems naverItemsOf(RemoteLocationItemsV1 naverItems) {
        List<LocationItem> naverMapList = naverItems.remoteLocationItemList()
                .stream()
                .map(n -> new LocationItem(n.placeName()))
                .filter(distinctByKey(LocationItem::getFirstWordOfName))
                .toList();

        return LocationItems.builder()
                .itemList(naverMapList)
                .build();
    }

    private static LocationItems kakaoItemsOf(RemoteLocationItemsV1 kakaoItems) {
        List<LocationItem> kakaoList = kakaoItems.remoteLocationItemList().stream()
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
