package io.gtrain.config;

import io.gtrain.security.authentication.EmsAuthenticationManager;
import io.gtrain.security.authentication.EmsTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;

/**
 * @author William Gentry
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

	@Autowired
	private EmsAuthenticationManager authenticationManager;

	@Autowired
	private EmsTokenAuthenticationFilter tokenAuthenticationFilter;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.httpBasic().disable();

		http.formLogin().disable();

		http.authenticationManager(authenticationManager);

		http.authorizeExchange()
				.pathMatchers(HttpMethod.POST, "/login").permitAll()
				.pathMatchers(HttpMethod.POST, "/register").permitAll()
				.anyExchange().authenticated();

		http.csrf().disable();

		http.addFilterAt(corsWebFilter(), SecurityWebFiltersOrder.CORS);

		http.addFilterAt(tokenAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

		return http.build();
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5000"));
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addExposedHeader("Location");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return new CorsWebFilter(source);
	}
}
