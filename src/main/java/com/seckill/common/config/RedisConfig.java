package com.seckill.common.config;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;


/**
 * Redis缓存配置类
 * @author yt 2018-09-05
 *
 */
@Configuration
@EnableCaching // 启用缓存特性
public class RedisConfig extends CachingConfigurerSupport {

		@Value("${spring.redis.host}")
		private String host;

		@Value("${spring.redis.port}")
		private String port;

		@Value("${spring.redis.password}")
		private String  password;

		@Value("${spring.redis.timeout}")
		private String timeout ;

		@Value("${spring.redis.database}")
		private int dataBase;


		@Bean
	    public RedisConnectionFactory redisCF(){
	        //如果什么参数都不设置，默认连接本地6379端口
	        JedisConnectionFactory factory = new JedisConnectionFactory();

	        factory.setHostName(this.host);
	        factory.setPassword(this.password);
	        factory.setPort(Integer.valueOf(this.port));
	        factory.setTimeout(Integer.valueOf(this.timeout));
	        factory.setDatabase(dataBase);

	        return factory;
	    }

		/**
		 * 重写Redis序列化方式，使用Json方式:
		 * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
		 * Spring Data JPA为我们提供了下面的Serializer：
		 * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
		 * 在此我们将自己配置RedisTemplate并定义Serializer。
		 *
		 * @param redisConnectionFactory
		 * @return
		 */
		@Bean
		public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
			RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
			redisTemplate.setConnectionFactory(redisConnectionFactory);

			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
					Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			// 解决空值转换问题
			jackson2JsonRedisSerializer.setObjectMapper(om);
			// 设置值（value）的序列化采用Jackson2JsonRedisSerializer。
			redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
			// 设置键（key）的序列化采用StringRedisSerializer。
			redisTemplate.setKeySerializer(new StringRedisSerializer());
	        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//	        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//	        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//			redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
			redisTemplate.afterPropertiesSet();
			return redisTemplate;
		}


		@Bean
		public KeyGenerator keyGenerator() {
			return new KeyGenerator() {
				@Override
				public Object generate(Object target, Method method, Object... params) {
					StringBuilder sb = new StringBuilder();
					sb.append(target.getClass().getName());
					sb.append(method.getName());
					for (Object obj : params) {
						sb.append(obj.toString());
					}
					return sb.toString();
				}
			};
		}


		/**
		 * 缓存管理器
		 * @param redisTemplate
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		@Bean
		public CacheManager cacheManager(RedisTemplate redisTemplate) {
			 RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
	        //设置缓存默认的过期时间(秒)(全局的)
	        rcm.setDefaultExpiration(600);
	        return rcm;
		}


////向redis里存入数据和设置缓存时间
//stringRedisTemplate.opsForValue().set("baike", "100", 60 * 10, TimeUnit.SECONDS);
////val做-1操作
//stringRedisTemplate.boundValueOps("baike").increment(-1);
////根据key获取缓存中的val
//stringRedisTemplate.opsForValue().get("baike")
////val +1
//stringRedisTemplate.boundValueOps("baike").increment(1);
////根据key获取过期时间
//stringRedisTemplate.getExpire("baike");
////根据key获取过期时间并换算成指定单位
//stringRedisTemplate.getExpire("baike",TimeUnit.SECONDS);
////根据key删除缓存
//stringRedisTemplate.delete("baike");
////检查key是否存在，返回boolean值
//stringRedisTemplate.hasKey("baike");
////向指定key中存放set集合
//stringRedisTemplate.opsForSet().add("baike", "1","2","3");
////设置过期时间
//stringRedisTemplate.expire("baike",1000 , TimeUnit.MILLISECONDS);
////根据key查看集合中是否存在指定数据
//stringRedisTemplate.opsForSet().isMember("baike", "1");
////根据key获取set集合
//stringRedisTemplate.opsForSet().members("baike");
//	//验证有效时间
//	Long expire = redisTemplate.boundHashOps("baike").getExpire();

}
