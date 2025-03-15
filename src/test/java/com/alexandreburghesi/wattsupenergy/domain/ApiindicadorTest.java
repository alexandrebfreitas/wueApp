package com.alexandreburghesi.wattsupenergy.domain;

import static com.alexandreburghesi.wattsupenergy.domain.ApiindicadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.wattsupenergy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiindicadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apiindicador.class);
        Apiindicador apiindicador1 = getApiindicadorSample1();
        Apiindicador apiindicador2 = new Apiindicador();
        assertThat(apiindicador1).isNotEqualTo(apiindicador2);

        apiindicador2.setId(apiindicador1.getId());
        assertThat(apiindicador1).isEqualTo(apiindicador2);

        apiindicador2 = getApiindicadorSample2();
        assertThat(apiindicador1).isNotEqualTo(apiindicador2);
    }
}
