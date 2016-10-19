package io.github.zutherb.appstash.shop.ui;

import io.github.zutherb.appstash.shop.ui.navigation.NavigationProvider;
import com.mongodb.Mongo;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;

/**
 * @author zutherb
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/zutherb/appstash/shop/ui/spring-context.xml")
public class ApplicationContextTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private Mongo mongo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoOperations mongoOperations;

    @Autowired
    private NavigationProvider navigationProvider;

    @Test
    public void testApplicationContextStartup() {
        assertNotNull(applicationContext);
        assertNotNull(mongo);
        assertNotNull(mongoTemplate);
        assertNotNull(mongoOperations);
        assertNotNull(navigationProvider);
    }
}
