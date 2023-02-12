package com.jwjung.location.popular.adapter.in.event;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.application.port.in.UpdatePopularUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularEventAdapter {
    private final UpdatePopularUseCase updatePopularUseCase;

    @Async("asyncPopularAdder")
    public void updatePopularCount(String query) {
        updatePopularUseCase.updatePopularCount(new PopularCommand(query));
    }
}
