package io.gtrain.security.token;

import io.gtrain.config.properties.KeyStoreProperties;
import io.gtrain.config.properties.TokenProperties;
import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.model.interfaces.EmsUserDetails;
import io.gtrain.security.token.interfaces.TokenGenerator;
import io.gtrain.security.token.interfaces.TokenParser;
import io.gtrain.security.token.interfaces.TokenValidator;
import io.jsonwebtoken.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
@Service
public class TokenFactory implements TokenGenerator, TokenParser, TokenValidator {

	private final long EXPIRES_IN;
	private final JwtBuilder builder;
	private final JwtParser parser;
	private final Logger logger = Loggers.getLogger(getClass());

	public TokenFactory(KeyStoreProperties keyStoreProperties, TokenProperties tokenProperties) {
		EXPIRES_IN = tokenProperties.getExpiry();
		try {
			KeyStore keyStore = KeyStore.getInstance(keyStoreProperties.getType());
			keyStore.load(new ClassPathResource(keyStoreProperties.getPath()).getInputStream(), keyStoreProperties.getPassword());

			Key key = keyStore.getKey(keyStoreProperties.getAlias(), keyStoreProperties.getPassword());
			if (key instanceof PrivateKey) {
				Certificate certificate = keyStore.getCertificate(keyStoreProperties.getAlias());
				PublicKey publicKey = certificate.getPublicKey();
				this.builder = Jwts.builder().signWith(key).setIssuer("EMS");
				this.parser = Jwts.parser().setSigningKey(publicKey);
			} else {
				throw new IllegalStateException("Failed to instantiate KeyStore");
			}
		} catch (Exception e) {
			logger.error("Failed to instantate TokenFactory: {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public String generateToken(Authentication authentication) {
		return builder.setClaims(getClaimsFromAuthentication(authentication)).compact();
	}

	@Override
	public Mono<Authentication> getAuthentication(String token) {
		return Mono.just(token).filter(StringUtils::hasText).map(parser::parseClaimsJws).map(Jwt::getBody).flatMap(claims -> Mono.just(new EmsAuthenticationToken(claims)));
	}

	@Override
	public boolean isValid(String token) {
		try {
			Claims claims = parser.parseClaimsJws(token).getBody();
			return claims.getExpiration().after(new Date(System.currentTimeMillis()));
		} catch (Exception e){
			return false;
		}
	}

	private Claims getClaimsFromAuthentication(Authentication authentication) {
		final EmsUserDetails user = (EmsUserDetails) authentication.getPrincipal();
		final long current = System.currentTimeMillis();
		Claims claims = Jwts.claims();
		claims.setSubject(authentication.getName());
		claims.put("uid", user.getId().toHexString());
		claims.put("aty", getAuthoritiesFromAuthentication(authentication));
		claims.setIssuedAt(new Date(current));
		claims.setExpiration(new Date(current + EXPIRES_IN));
		return claims;
	}

	private String getAuthoritiesFromAuthentication(Authentication authentication) {
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
	}
}
