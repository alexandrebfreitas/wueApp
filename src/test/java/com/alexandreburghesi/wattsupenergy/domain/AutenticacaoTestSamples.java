package com.alexandreburghesi.wattsupenergy.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AutenticacaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Autenticacao getAutenticacaoSample1() {
        return new Autenticacao().id(1L).usuario("usuario1").senha("senha1").accessToken("accessToken1").tokenType("tokenType1");
    }

    public static Autenticacao getAutenticacaoSample2() {
        return new Autenticacao().id(2L).usuario("usuario2").senha("senha2").accessToken("accessToken2").tokenType("tokenType2");
    }

    public static Autenticacao getAutenticacaoRandomSampleGenerator() {
        return new Autenticacao()
            .id(longCount.incrementAndGet())
            .usuario(UUID.randomUUID().toString())
            .senha(UUID.randomUUID().toString())
            .accessToken(UUID.randomUUID().toString())
            .tokenType(UUID.randomUUID().toString());
    }
}
