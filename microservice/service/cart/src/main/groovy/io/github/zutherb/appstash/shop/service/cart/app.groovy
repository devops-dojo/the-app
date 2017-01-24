package io.github.zutherb.appstash.shop.service.cart

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext


/**
 * @author zutherb
 */
class Boot implements CommandLineRunner {
    public boolean springBoot = false
    static void main(String[] args) {
        if (springBoot){
            SpringApplication.run(ApplicationConfiguration.class, args)
        }
    }

    @Override
    void run(String ... args) throws Exception {

    }
}
