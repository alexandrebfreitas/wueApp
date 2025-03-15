package com.alexandreburghesi.wattsupenergy.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApiindicadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Apiindicador getApiindicadorSample1() {
        return new Apiindicador().id(1L).chave("chave1");
    }

    public static Apiindicador getApiindicadorSample2() {
        return new Apiindicador().id(2L).chave("chave2");
    }

    public static Apiindicador getApiindicadorRandomSampleGenerator() {
        return new Apiindicador().id(longCount.incrementAndGet()).chave(UUID.randomUUID().toString());
    }
}
