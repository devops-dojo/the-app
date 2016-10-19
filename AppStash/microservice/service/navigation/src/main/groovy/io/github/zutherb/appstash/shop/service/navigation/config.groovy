package io.github.zutherb.appstash.shop.service.navigation

import com.mongodb.Mongo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.web.filter.ShallowEtagHeaderFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
@EnableAutoConfiguration
@ComponentScan
class ApplicationConfiguration extends WebMvcConfigurationSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class)

    @Autowired
    Environment environment;

    @Bean
    Mongo mongo() {
        def hostName = environment.getProperty("NAVIGATION_SERVICE_MONGODB_URL",
                environment.getProperty("MONGODB_PORT_27017_TCP_ADDR", "localhost"))
        def port = environment.getProperty("MONGODB_PORT_27017_TCP_PORT", Integer.class, 27017)

        new Mongo(hostName, port)
    }

    @Bean
    MongoTemplate mongoTemplate() {
        new MongoTemplate(mongo(), "shop")
    }

    @Bean
    public FilterRegistrationBean etagHeaderFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        ShallowEtagHeaderFilter etagHeaderFilter = new ShallowEtagHeaderFilter();
        registrationBean.setFilter(etagHeaderFilter);
        registrationBean.setOrder(1);
        registrationBean;
    }
}