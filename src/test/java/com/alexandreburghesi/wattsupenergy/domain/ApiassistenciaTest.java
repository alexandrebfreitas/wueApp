package com.alexandreburghesi.wattsupenergy.domain;

import static com.alexandreburghesi.wattsupenergy.domain.ApiassistenciaTestSamples.*;
import static com.alexandreburghesi.wattsupenergy.domain.AssistenciasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.wattsupenergy.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApiassistenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apiassistencia.class);
        Apiassistencia apiassistencia1 = getApiassistenciaSample1();
        Apiassistencia apiassistencia2 = new Apiassistencia();
        assertThat(apiassistencia1).isNotEqualTo(apiassistencia2);

        apiassistencia2.setId(apiassistencia1.getId());
        assertThat(apiassistencia1).isEqualTo(apiassistencia2);

        apiassistencia2 = getApiassistenciaSample2();
        assertThat(apiassistencia1).isNotEqualTo(apiassistencia2);
    }

    @Test
    void assistenciasTest() {
        Apiassistencia apiassistencia = getApiassistenciaRandomSampleGenerator();
        Assistencias assistenciasBack = getAssistenciasRandomSampleGenerator();

        apiassistencia.addAssistencias(assistenciasBack);
        assertThat(apiassistencia.getAssistencias()).containsOnly(assistenciasBack);
        assertThat(assistenciasBack.getApiassistencia()).isEqualTo(apiassistencia);

        apiassistencia.removeAssistencias(assistenciasBack);
        assertThat(apiassistencia.getAssistencias()).doesNotContain(assistenciasBack);
        assertThat(assistenciasBack.getApiassistencia()).isNull();

        apiassistencia.assistencias(new HashSet<>(Set.of(assistenciasBack)));
        assertThat(apiassistencia.getAssistencias()).containsOnly(assistenciasBack);
        assertThat(assistenciasBack.getApiassistencia()).isEqualTo(apiassistencia);

        apiassistencia.setAssistencias(new HashSet<>());
        assertThat(apiassistencia.getAssistencias()).doesNotContain(assistenciasBack);
        assertThat(assistenciasBack.getApiassistencia()).isNull();
    }
}
