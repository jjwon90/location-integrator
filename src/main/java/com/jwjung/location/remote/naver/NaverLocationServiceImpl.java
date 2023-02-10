package com.jwjung.location.remote.naver;

import com.jwjung.location.remote.model.RemoteLocationItemsV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverLocationServiceImpl implements NaverLocationService {
    private final NaverLocationClient naverLocationClient;

    @Override
    public RemoteLocationItemsV1 getNaverItems(String query) {
        return naverLocationClient.getNaverLocationSearch(query)
                .map(RemoteLocationItemsV1::ofNaverItems)
                .orElseGet(RemoteLocationItemsV1::emptyOf);
    }
}
