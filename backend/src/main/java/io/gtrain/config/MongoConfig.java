package io.gtrain.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.gtrain.config.properties.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

/**
 * @author William Gentry
 */
@Configuration
public class MongoConfig {

	private final MongoProperties mongoProperties;

	public MongoConfig(MongoProperties mongoProperties) {
		this.mongoProperties = mongoProperties;
	}

	@Bean
	public MongoClient reactiveMongoClient() {
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://localhost:27017")).credential(MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword())).build();
		return MongoClients.create(settings);
	}

	@Bean
	public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory() {
		return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), getDatabaseName());
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(reactiveMongoDatabaseFactory());
	}

	protected String getDatabaseName() {
		return mongoProperties.getDatabase();
	}
}
