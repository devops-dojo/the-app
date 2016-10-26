package io.github.zutherb.appstash.shop.service.user.domain

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author zutherb
 */
interface UserRepository extends MongoRepository<User, String> {
    User findById(String id)
}
