package io.github.zutherb.appstash.shop.ui.profile;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * ProfileInitializer initialize the default profile when no profile was set
 *
 * @author zutherb
 */
public class ProfileInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileInitializer.class);

    @Override
    public void initialize(ConfigurableWebApplicationContext configurableWebApplicationContext) {
        ConfigurableEnvironment environment = configurableWebApplicationContext.getEnvironment();
        if (environment.getSystemEnvironment().containsKey("MONGODB_PORT_27017_TCP_ADDR") &&
                environment.getSystemEnvironment().containsKey("MONGODB_PORT_27017_TCP_PORT")) {
            environment.setActiveProfiles("docker");
        }
        if (!hasActiveProfile(environment)) {
            environment.setActiveProfiles("production");
        }
        LOGGER.info("Active profiles are {}", StringUtils.join(environment.getActiveProfiles(), ","));
    }

    private boolean hasActiveProfile(ConfigurableEnvironment environment) {
        return environment.getActiveProfiles().length > 0;
    }
}