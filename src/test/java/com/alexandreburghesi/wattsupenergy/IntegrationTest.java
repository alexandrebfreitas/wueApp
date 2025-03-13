package com.alexandreburghesi.wattsupenergy;

import com.alexandreburghesi.wattsupenergy.config.AsyncSyncConfiguration;
import com.alexandreburghesi.wattsupenergy.config.EmbeddedSQL;
import com.alexandreburghesi.wattsupenergy.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { WattsUpEnergyApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
