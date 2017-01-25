package io.github.zutherb.appstash.shop.service.user

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext

/**
 * @author zutherb
 */
class Boot implements CommandLineRunner {
    public boolean doSpringBoot = false
    public static void main(String[] args) {
        if (doSpringBoot==true){        
            SpringApplication.run(ApplicationConfiguration.class, args)
        }
    }

    @Override
    void run(String ... args) throws Exception {

    }
}
