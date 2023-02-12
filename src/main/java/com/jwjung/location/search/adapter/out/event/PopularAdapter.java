package com.jwjung.location.search.adapter.out.event;

import com.jwjung.location.popular.adapter.in.event.PopularEventAdapter;
import com.jwjung.location.search.application.port.out.PopularPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularAdapter implements PopularPort {
    private final PopularEventAdapter popularEventAdapter;

    @Override
    public void producePopularEvent(String query) {
        popularEventAdapter.updatePopularCount(query);
    }
}
