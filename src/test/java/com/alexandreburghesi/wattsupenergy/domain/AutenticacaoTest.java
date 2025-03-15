package com.alexandreburghesi.wattsupenergy.domain;

import static com.alexandreburghesi.wattsupenergy.domain.AutenticacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.wattsupenergy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutenticacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autenticacao.class);
        Autenticacao autenticacao1 = getAutenticacaoSample1();
        Autenticacao autenticacao2 = new Autenticacao();
        assertThat(autenticacao1).isNotEqualTo(autenticacao2);

        autenticacao2.setId(autenticacao1.getId());
        assertThat(autenticacao1).isEqualTo(autenticacao2);

        autenticacao2 = getAutenticacaoSample2();
        assertThat(autenticacao1).isNotEqualTo(autenticacao2);
    }
}
