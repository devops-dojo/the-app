package io.github.zutherb.appstash.shop.dataloader

import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment

import static org.springframework.boot.SpringApplication.run

/**
 * @author zutherb
 */
class Boot {

    public static void main(String[] args) {
        String[] sources = [
                "classpath:/io/github/zutherb/appstash/shop/dataloader/spring-env-context.xml",
                "classpath:/io/github/zutherb/appstash/shop/dataloader/spring-context.xml",
                "classpath:/io/github/zutherb/appstash/dataloader/spring-context.xml"]
        ApplicationContext ctx = run(sources, args)
    }
}
