package io.gtrain.domain.repository.interfaces;

import io.gtrain.domain.model.Address;
import io.gtrain.domain.model.EmsUserInfo;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface EmsUserInfoRepository {

	Mono<EmsUserInfo> save(EmsUserInfo userInfo);
	Mono<EmsUserInfo> getUserInfo(ObjectId userId);
	Mono<EmsUserInfo> updateAddress(ObjectId userId, Address address);
}
