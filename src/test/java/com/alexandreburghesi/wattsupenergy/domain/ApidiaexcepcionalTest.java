package com.alexandreburghesi.wattsupenergy.domain;

import static com.alexandreburghesi.wattsupenergy.domain.ApidiaexcepcionalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.wattsupenergy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApidiaexcepcionalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apidiaexcepcional.class);
        Apidiaexcepcional apidiaexcepcional1 = getApidiaexcepcionalSample1();
        Apidiaexcepcional apidiaexcepcional2 = new Apidiaexcepcional();
        assertThat(apidiaexcepcional1).isNotEqualTo(apidiaexcepcional2);

        apidiaexcepcional2.setId(apidiaexcepcional1.getId());
        assertThat(apidiaexcepcional1).isEqualTo(apidiaexcepcional2);

        apidiaexcepcional2 = getApidiaexcepcionalSample2();
        assertThat(apidiaexcepcional1).isNotEqualTo(apidiaexcepcional2);
    }
}
