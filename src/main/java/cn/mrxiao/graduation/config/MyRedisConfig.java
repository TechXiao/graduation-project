package cn.mrxiao.graduation.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
/**
 * 序列化器
 * @author 深度
 *
 */
@Configuration
public class MyRedisConfig {
	//自定义缓存管理器
	@Bean
    public RedisCacheManager employeeRedisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration =
                          RedisCacheConfiguration.defaultCacheConfig()
                                  .entryTtl(Duration.ofDays(30))   // 设置缓存过期时间为30天
                                  .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new
                                          GenericJackson2JsonRedisSerializer()));     // 设置CacheManager的值序列化方式为json序列化，可加入@Class属性
                return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();     // 设置默认的cache组件
    }
}
