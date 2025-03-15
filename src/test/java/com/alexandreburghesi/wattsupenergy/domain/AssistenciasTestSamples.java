package com.alexandreburghesi.wattsupenergy.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AssistenciasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Assistencias getAssistenciasSample1() {
        return new Assistencias().id(1L);
    }

    public static Assistencias getAssistenciasSample2() {
        return new Assistencias().id(2L);
    }

    public static Assistencias getAssistenciasRandomSampleGenerator() {
        return new Assistencias().id(longCount.incrementAndGet());
    }
}
