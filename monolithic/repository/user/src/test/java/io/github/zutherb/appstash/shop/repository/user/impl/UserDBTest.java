package io.github.zutherb.appstash.shop.repository.user.impl;

import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.Address;
import io.github.zutherb.appstash.shop.repository.user.model.Role;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Collections;
import java.util.Random;

@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/zutherb/appstash/shop/repository/user/spring-context.xml")
public class UserDBTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void saveAndRetrieveOrderTest() {
        mongoOperations.dropCollection(User.class);
        User sampleUser = createSampleUser("sample");
        userRepository.save(sampleUser);

        Assert.assertEquals(1, userRepository.countAll());
        Assert.assertEquals(sampleUser.getUsername(), userRepository.findAll().get(0).getUsername());
    }

    @Test
    @Ignore // not ensured by Fongo
    public void uniqueUsernameTest() {
        User user1 = createSampleUser("user1");
        User user1Clone = createSampleUser("user1");
        User user2 = createSampleUser("user2");

        userRepository.save(user1);
        Assert.assertEquals(1, userRepository.countAll());

        userRepository.save(user2);
        Assert.assertEquals(2, userRepository.countAll());

        userRepository.save(user1Clone);
        Assert.assertEquals(2, userRepository.countAll());
    }

    private static User createSampleUser(String username) {
        User user = new User(username, "John", "Smith", "securepw", new Address("", "", "", ""), Collections.<Role>emptySet());
        user.setEmail("username" + new Random().nextInt(1000) + "@gmail.com");
        return user;
    }
}
