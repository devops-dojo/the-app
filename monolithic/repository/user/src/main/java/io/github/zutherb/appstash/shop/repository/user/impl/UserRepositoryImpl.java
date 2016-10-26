package io.github.zutherb.appstash.shop.repository.user.impl;

import io.github.zutherb.appstash.common.repository.AbstractRepositoryImpl;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.common.repository.AbstractRepositoryImpl;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author zutherb
 */
@Repository("userRepository")
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {

    @Autowired
    public UserRepositoryImpl(@Qualifier("mongoTemplate") MongoOperations mongoOperations) {
        super(mongoOperations, User.class);
    }

    @Override
    public User findById(ObjectId userId) {
        return mongoOperations.findById(userId, User.class);
    }

    @Override
    public User findByUsername(String username) {
        return mongoOperations.findOne(new Query(Criteria.where("username").is(username)), User.class);
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        return mongoOperations.count(new Query(Criteria.where("email").is(email)), User.class) > 0;
    }
}
