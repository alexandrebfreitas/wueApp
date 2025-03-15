package com.alexandreburghesi.wattsupenergy.domain;

import static com.alexandreburghesi.wattsupenergy.domain.ApiassistenciaTestSamples.*;
import static com.alexandreburghesi.wattsupenergy.domain.AssistenciasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.wattsupenergy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssistenciasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assistencias.class);
        Assistencias assistencias1 = getAssistenciasSample1();
        Assistencias assistencias2 = new Assistencias();
        assertThat(assistencias1).isNotEqualTo(assistencias2);

        assistencias2.setId(assistencias1.getId());
        assertThat(assistencias1).isEqualTo(assistencias2);

        assistencias2 = getAssistenciasSample2();
        assertThat(assistencias1).isNotEqualTo(assistencias2);
    }

    @Test
    void apiassistenciaTest() {
        Assistencias assistencias = getAssistenciasRandomSampleGenerator();
        Apiassistencia apiassistenciaBack = getApiassistenciaRandomSampleGenerator();

        assistencias.setApiassistencia(apiassistenciaBack);
        assertThat(assistencias.getApiassistencia()).isEqualTo(apiassistenciaBack);

        assistencias.apiassistencia(null);
        assertThat(assistencias.getApiassistencia()).isNull();
    }
}
