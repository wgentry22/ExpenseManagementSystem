package io.gtrain.domain.repository;

import io.gtrain.domain.model.EmsUserInfo;
import io.gtrain.domain.repository.interfaces.EmsUserInfoRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveEmsUserInfoRepository implements EmsUserInfoRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public ReactiveEmsUserInfoRepository(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<EmsUserInfo> save(EmsUserInfo userInfo) {
		return mongoTemplate.save(userInfo, "user_info");
	}

	@Override
	public Mono<EmsUserInfo> getUserInfo(ObjectId userId) {
		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class);
	}
}
