package io.github.zutherb.appstash.shop.service.user

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import io.github.zutherb.appstash.common.util.Config

/**
 * @author zutherb
 */
class Boot implements CommandLineRunner {

    public static void main(String[] args) {
        if (Config.getProperty("SPRING_BOOT")!=null && 
            Config.getProperty("SPRING_BOOT").equalsIgnoreCase("TRUE")){        
            SpringApplication.run(ApplicationConfiguration.class, args)
        }
    }

    @Override
    void run(String ... args) throws Exception {

    }
}
