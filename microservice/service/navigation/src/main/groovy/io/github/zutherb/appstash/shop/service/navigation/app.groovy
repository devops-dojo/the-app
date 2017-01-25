package io.github.zutherb.appstash.shop.service.navigation

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication

/**
 * @author zutherb
 */
class Boot implements CommandLineRunner {
    public boolean doSpringBoot = false
    static void main(String[] args) {
        if (doSpringBoot==true){        
            SpringApplication.run(ApplicationConfiguration.class, args)
        }
    }

    @Override
    void run(String ... args) throws Exception {

    }
}
