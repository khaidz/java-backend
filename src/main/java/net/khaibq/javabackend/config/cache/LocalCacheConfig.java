//package net.khaibq.javabackend.config.cache;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.Expiry;
//import lombok.extern.slf4j.Slf4j;
//import org.checkerframework.checker.index.qual.NonNegative;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.caffeine.CaffeineCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//@Slf4j
//public class LocalCacheConfig {
//    @Value("${app.cache.memory.cache-names}")
//    private String cacheNames;
//
//    @Value("${app.cache.memory.expire-time}")
//    private Long expireTime;
//
//    @Bean
//    public Caffeine<Object, Object> caffeineCacheBuilder() {
//        return Caffeine.newBuilder().expireAfter(new Expiry<>() {
//            @Override
//            public long expireAfterCreate(Object o, Object o2, long l) {
//                return TimeUnit.MILLISECONDS.toNanos(expireTime);
//            }
//
//            @Override
//            public long expireAfterUpdate(Object o, Object o2, long l, @NonNegative long l1) {
//                return l1;
//            }
//
//            @Override
//            public long expireAfterRead(Object o, Object o2, long l, @NonNegative long l1) {
//                return l1;
//            }
//        });
//    }
//
//    @Bean
//    @Primary
//    public CacheManager localCacheManager(Caffeine<Object, Object> caffeineCacheBuilder) {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        if (cacheNames != null && !cacheNames.isEmpty()) {
//            cacheManager.setCacheNames(List.of(cacheNames));
//        } else {
//            cacheManager.setCacheNames(null);
//        }
//
//        log.info("Configuring local cache manager");
//        log.info("Using caffeine: {}", caffeineCacheBuilder);
//        log.info("Cache names: {}", cacheNames);
//        cacheManager.setCaffeine(caffeineCacheBuilder);
//        return cacheManager;
//    }
//}
