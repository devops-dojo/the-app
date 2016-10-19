package io.github.zutherb.appstash.shop.repository.user.api;

import io.github.zutherb.appstash.common.repository.AbstractRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.bson.types.ObjectId;

/**
 * @author zutherb
 */
public interface UserRepository extends AbstractRepository<User> {
    User findById(ObjectId userId);

    User findByUsername(String username);

    boolean existsUserWithEmail(String email);
}
