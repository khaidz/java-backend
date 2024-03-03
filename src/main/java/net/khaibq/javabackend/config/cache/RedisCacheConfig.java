//package net.khaibq.javabackend.config.cache;

//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@Slf4j
//public class RedisCacheConfig {
//    @Value("${spring.data.redis.host}")
//    private String redisHost;
//    @Value("${spring.data.redis.port}")
//    private Integer redisPort;
//    @Value("${spring.data.redis.password}")
//    private String redisPassword;
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(redisHost);
//        redisStandaloneConfiguration.setPort(redisPort);
//        if (!StringUtils.isEmpty(redisPassword)) {
//            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
//        }
//
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Primary
//    @Bean
//    public RedisTemplate<Byte[], Byte[]> redisTemplate(RedisConnectionFactory cf) {
//        RedisTemplate<Byte[], Byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(cf);
//        return redisTemplate;
//    }
//
//    @Bean({"redisTemplateByteByte"})
//    public RedisTemplate<byte[], byte[]> redisTemplateByteByte(RedisConnectionFactory cf) {
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(cf);
//        return redisTemplate;
//    }
//
//    @Bean({"redisTemplateStringObject"})
//    public RedisTemplate<String, Object> redisTemplateStringObject(RedisConnectionFactory cf) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(cf);
//        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));  //
//        redisTemplate.setKeySerializer(new StringRedisSerializer()); // Để lưu key dưới dạng plain text có thể comment lại
//        return redisTemplate;
//    }
//
//    @Bean({"redisTemplateObjectObject"})
//    public RedisTemplate<Object, Object> redisTemplateObjectObject(RedisConnectionFactory cf) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(cf);
//        return redisTemplate;
//    }
//
//}
