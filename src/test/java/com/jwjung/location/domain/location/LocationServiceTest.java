package com.jwjung.location.domain.location;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

class LocationServiceTest {
    @Spy
    ExecutorService asyncLocationSearcher = newFixedThreadPool(30);

    @Test
    void getLocationItems() {
    }
}