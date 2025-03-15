package com.alexandreburghesi.wattsupenergy.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApidiaexcepcionalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Apidiaexcepcional getApidiaexcepcionalSample1() {
        return new Apidiaexcepcional().id(1L).chave("chave1");
    }

    public static Apidiaexcepcional getApidiaexcepcionalSample2() {
        return new Apidiaexcepcional().id(2L).chave("chave2");
    }

    public static Apidiaexcepcional getApidiaexcepcionalRandomSampleGenerator() {
        return new Apidiaexcepcional().id(longCount.incrementAndGet()).chave(UUID.randomUUID().toString());
    }
}
