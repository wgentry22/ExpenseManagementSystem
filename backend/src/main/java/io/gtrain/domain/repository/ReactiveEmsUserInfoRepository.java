package io.gtrain.domain.repository;

import io.gtrain.domain.model.Address;
import io.gtrain.domain.model.EmsUserInfo;
import io.gtrain.domain.repository.interfaces.EmsUserInfoRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveEmsUserInfoRepository implements EmsUserInfoRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	private final FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true);

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

	@Override
	public Mono<EmsUserInfo> updateAddress(ObjectId userId, Address address) {
		return mongoTemplate.findAndModify(Query.query(Criteria.where("userId").is(userId)), getUpdateAddressQuery(address), findAndModifyOptions, EmsUserInfo.class);
	}

	private Update getUpdateAddressQuery(Address address) {
		Update update = new Update();
		update.set("address", address);
		return update;
	}
}
