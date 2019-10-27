package user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.jedis.Protocol;

import java.net.URI;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        String redisUrl = System.getenv("REDIS_URL");
        URI redisUri = URI.create(redisUrl);

        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();

        jedisConnFactory.setUsePool(true);
        jedisConnFactory.setHostName(redisUri.getHost());
        jedisConnFactory.setPort(redisUri.getPort());
        jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
        jedisConnFactory.setPassword(redisUri.getUserInfo().split(":", 2)[1]);

        return jedisConnFactory;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
