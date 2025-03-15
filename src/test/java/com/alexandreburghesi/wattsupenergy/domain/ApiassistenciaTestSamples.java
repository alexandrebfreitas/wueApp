package com.alexandreburghesi.wattsupenergy.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApiassistenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Apiassistencia getApiassistenciaSample1() {
        return new Apiassistencia().id(1L).chave("chave1");
    }

    public static Apiassistencia getApiassistenciaSample2() {
        return new Apiassistencia().id(2L).chave("chave2");
    }

    public static Apiassistencia getApiassistenciaRandomSampleGenerator() {
        return new Apiassistencia().id(longCount.incrementAndGet()).chave(UUID.randomUUID().toString());
    }
}
