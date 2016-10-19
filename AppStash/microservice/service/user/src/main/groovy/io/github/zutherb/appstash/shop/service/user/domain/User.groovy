package io.github.zutherb.appstash.shop.service.user.domain

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

/**
 * @author zutherb
 */
@JsonSerialize
class User implements Serializable {
    @Id
    def String id
    def SalutationType salutationType
    @Indexed(unique = true)
    def String username
    def String password
    def String firstname
    def String lastname
    @Indexed(unique = true)
    def String email

    def Set<Role> roles;
    def Set<String> categories;

    def Address address;

}
