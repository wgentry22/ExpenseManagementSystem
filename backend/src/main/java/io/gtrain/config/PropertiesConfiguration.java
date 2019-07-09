package io.gtrain.config;

import io.gtrain.config.properties.KeyStoreProperties;
import io.gtrain.config.properties.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author William Gentry
 */
@Configuration
@EnableConfigurationProperties({KeyStoreProperties.class, MongoProperties.class})
public class PropertiesConfiguration {
}
