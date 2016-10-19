package io.github.zutherb.appstash.shop.service.user

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
@EnableAutoConfiguration
@ComponentScan
class ApplicationConfiguration extends WebMvcConfigurationSupport {

    void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter())
        addDefaultHttpMessageConverters(converters)
    }

    @Bean
    MappingJackson2HttpMessageConverter converter() {
        new MappingJackson2HttpMessageConverter()
    }
}