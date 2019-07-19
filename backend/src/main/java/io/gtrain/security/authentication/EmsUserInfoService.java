package io.gtrain.security.authentication;

import io.gtrain.domain.model.Address;
import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.domain.model.EmsUserInfo;
import io.gtrain.domain.repository.interfaces.EmsUserInfoRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class EmsUserInfoService {

	private final EmsUserInfoRepository userInfoRepository;

	public EmsUserInfoService(EmsUserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	public Mono<EmsUserInfo> attemptRetrieveUserInfo(EmsAuthenticationToken authenticationToken) {
		if (authenticationToken.getCredentials() instanceof String) {
			final ObjectId userId = new ObjectId((String) authenticationToken.getCredentials());
			return userInfoRepository.getUserInfo(userId);
		}
		return Mono.empty();
	}

	public Mono<EmsUserInfo> attemptUpdateAddress(ObjectId userId, Address address) {
		return userInfoRepository.updateAddress(userId, address);
	}
}
