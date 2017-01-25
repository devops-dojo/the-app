package io.github.zutherb.appstash.shop.service.cart

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext


/**
 * @author zutherb
 */
class Boot implements CommandLineRunner {
    
    static void main(String[] args) {
        boolean doSpringBoot = false
        if (doSpringBoot == true){
            SpringApplication.run(ApplicationConfiguration.class, args)
        }
    }

    @Override
    void run(String ... args) throws Exception {

    }
}
