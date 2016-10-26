package io.github.zutherb.appstash.shop.service.user.impl;

import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.AddressInfo;
import io.github.zutherb.appstash.shop.service.user.model.RoleInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Collections;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * @author zutherb
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/zutherb/appstash/shop/service/user/spring-context.xml")
public class UserServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    @Ignore
    public void testFindByUsername() {
        mongoOperations.dropCollection(User.class);
        userService.save(new UserInfo("found", "securepw", Collections.<RoleInfo>emptySet(), new AddressInfo("", "", "", "", "", "", "", "", 0, 0)));
        UserInfo userInfo = userService.findByUsername("found");
        assertNotNull(userInfo);
        UserInfo notFound = userService.findByUsername("not-found");
        assertNull(notFound);
    }

}
