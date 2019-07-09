package io.gtrain.properties;

import io.gtrain.config.PropertiesConfiguration;
import io.gtrain.config.properties.KeyStoreProperties;
import io.gtrain.config.properties.MongoProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PropertiesConfiguration.class)
public class ConfigurationPropertiesTest {

	private ApplicationContextRunner runner = new ApplicationContextRunner();

	@Test
	void keyStoreProperties_ShouldBePresent() {
		runner.withUserConfiguration(PropertiesConfiguration.class).run(context -> {
			assertThat(context).hasSingleBean(KeyStoreProperties.class);
			final KeyStoreProperties keyStoreProperties = context.getBean(KeyStoreProperties.class);
			assertThat(keyStoreProperties.getAlias()).isNotBlank();
			assertThat(keyStoreProperties.getPassword()).isNotEmpty();
			assertThat(keyStoreProperties.getPath()).isNotBlank();
			assertThat(keyStoreProperties.getType()).isNotBlank();
		});
	}

	@Test
	void mongoProperties_ShouldBePresent() {
		runner.withUserConfiguration(PropertiesConfiguration.class).run(context -> {
			assertThat(context).hasSingleBean(MongoProperties.class);
			final MongoProperties mongoProperties = context.getBean(MongoProperties.class);
			assertThat(mongoProperties.getUsername()).isNotBlank();
			assertThat(mongoProperties.getPassword()).isNotEmpty();
			assertThat(mongoProperties.getDatabase()).isNotBlank();
			assertThat(mongoProperties.getPort()).isNotZero();
			assertThat(mongoProperties.getAuthenticationDatabase()).isNotBlank();
		});
	}
}
