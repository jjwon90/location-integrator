package com.jwjung.location.search.domain;

import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Builder
public record LocationItems(List<LocationItem> itemList) {
    public static LocationItems of(RemoteLocationItemsV1 kakaoItems,
                                   RemoteLocationItemsV1 naverItems) {
        if (naverItems.isEmpty() && kakaoItems.isEmpty()) {
            return emptyOf();
        } else if (kakaoItems.isEmpty()) {
            return naverItemsOf(naverItems);
        } else if (naverItems.isEmpty()) {
            return kakaoItemsOf(kakaoItems);
        }
        return bothOf(kakaoItems, naverItems);
    }

    private static LocationItems emptyOf() {
        return LocationItems.builder()
                .itemList(Collections.emptyList())
                .build();
    }

    private static LocationItems bothOf(RemoteLocationItemsV1 kakaoItems,
                                        RemoteLocationItemsV1 naverItems) {
        List<LocationItem> kakaoList = kakaoItems.remoteLocationItemList().stream()
                .map(k -> LocationItem.of(k.placeName(), k.address()))
                .toList();

        List<LocationItem> naverList = naverItems.remoteLocationItemList().stream()
                .map(n -> LocationItem.of(n.placeName(), n.address()))
                .toList();

        return LocationItems.builder()
                .itemList(reorderingList(kakaoList, naverList))
                .build();
    }

    private static LocationItems naverItemsOf(RemoteLocationItemsV1 naverItems) {
        List<LocationItem> naverMapList = naverItems.remoteLocationItemList()
                .stream()
                .map(n -> LocationItem.of(n.placeName(), n.address()))
                .toList();

        return LocationItems.builder()
                .itemList(naverMapList)
                .build();
    }

    private static LocationItems kakaoItemsOf(RemoteLocationItemsV1 kakaoItems) {
        List<LocationItem> kakaoList = kakaoItems.remoteLocationItemList().stream()
                .map(k -> LocationItem.of(k.placeName(), k.address()))
                .toList();

        return LocationItems.builder()
                .itemList(kakaoList)
                .build();
    }

    private static List<LocationItem> reorderingList(List<LocationItem> kakaoList,
                                                     List<LocationItem> naverList) {
        // mutable??? list??? copy?????? ????????? list??? ??????
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
                if (kakaoItem.isEqualLocation(naverItem)) {
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
