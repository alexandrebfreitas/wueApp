package com.alexandreburghesi.wattsupenergy.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApiperiodoassistenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Apiperiodoassistencia getApiperiodoassistenciaSample1() {
        return new Apiperiodoassistencia().id(1L).chave("chave1");
    }

    public static Apiperiodoassistencia getApiperiodoassistenciaSample2() {
        return new Apiperiodoassistencia().id(2L).chave("chave2");
    }

    public static Apiperiodoassistencia getApiperiodoassistenciaRandomSampleGenerator() {
        return new Apiperiodoassistencia().id(longCount.incrementAndGet()).chave(UUID.randomUUID().toString());
    }
}
