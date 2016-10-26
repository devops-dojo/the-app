package io.github.zutherb.appstash.shop.service.cart

import io.github.zutherb.appstash.shop.service.cart.domain.CartItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.filter.ShallowEtagHeaderFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol

@Configuration
@EnableAutoConfiguration
@ComponentScan
class ApplicationConfiguration extends WebMvcConfigurationSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class)

    @Autowired
    Environment environment;

    @Bean
    RedisConnectionFactory connectionFactory() {
        def factory = new JedisConnectionFactory(poolConfig());
        def hostName = environment.getProperty("CART_SERVICE_REDIS_URL",
                environment.getProperty("REDIS_PORT_6379_TCP_ADDR", "localhost"))
        def port = environment.getProperty("REDIS_PORT_6379_TCP_PORT", Integer.class, Protocol.DEFAULT_PORT)
        LOGGER.info("Redis Hostname is $hostName:$port")
        factory.setHostName(hostName)
        factory.setPort(port)
        factory.setUsePool(true)
        factory
    }

    @Bean
    JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10000);
        poolConfig.setMinIdle(1000);
        poolConfig.setMaxIdle(-1);
        poolConfig.setMaxWaitMillis(500);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig
    }

    @Bean
    RedisTemplate<String, CartItem> redisTemplate() {
        def RedisTemplate<String, List<CartItem>> redisTemplate = new RedisTemplate()
        redisTemplate.setConnectionFactory(connectionFactory())
        redisTemplate.setKeySerializer(new StringRedisSerializer())
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<CartItem>(CartItem.class))
        redisTemplate
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