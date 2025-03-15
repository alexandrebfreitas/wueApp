package com.alexandreburghesi.wattsupenergy.domain;

import static com.alexandreburghesi.wattsupenergy.domain.ApiperiodoassistenciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.wattsupenergy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiperiodoassistenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apiperiodoassistencia.class);
        Apiperiodoassistencia apiperiodoassistencia1 = getApiperiodoassistenciaSample1();
        Apiperiodoassistencia apiperiodoassistencia2 = new Apiperiodoassistencia();
        assertThat(apiperiodoassistencia1).isNotEqualTo(apiperiodoassistencia2);

        apiperiodoassistencia2.setId(apiperiodoassistencia1.getId());
        assertThat(apiperiodoassistencia1).isEqualTo(apiperiodoassistencia2);

        apiperiodoassistencia2 = getApiperiodoassistenciaSample2();
        assertThat(apiperiodoassistencia1).isNotEqualTo(apiperiodoassistencia2);
    }
}
