package io.gtrain.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author William Gentry
 */
@Component
@ConfigurationProperties(prefix = "token")
@PropertySource("classpath:token.properties")
public class TokenProperties {

	private long expiry;

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}
}
