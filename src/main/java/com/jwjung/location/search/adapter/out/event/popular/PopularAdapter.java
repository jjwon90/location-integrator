package com.jwjung.location.search.adapter.out.event.popular;

import com.jwjung.location.popular.adapter.in.event.PopularEventConsumer;
import com.jwjung.location.search.application.port.out.PopularPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularAdapter implements PopularPort {
    private final PopularEventConsumer popularEventConsumer;

    @Override
    public void producePopularEvent(String query) {
        popularEventConsumer.updatePopularCount(query);
    }
}
