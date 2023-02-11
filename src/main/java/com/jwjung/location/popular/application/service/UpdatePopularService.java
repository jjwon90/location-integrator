package com.jwjung.location.popular.application.service;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.application.port.in.UpdatePopularUseCase;
import com.jwjung.location.popular.application.port.out.UpdatePopularPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePopularService implements UpdatePopularUseCase {
    private final UpdatePopularPort updatePopularPort;

    @Override
    public void updatePopularCount(PopularCommand popularCommand) {
        updatePopularPort.updatePopularCount(popularCommand);
    }
}
