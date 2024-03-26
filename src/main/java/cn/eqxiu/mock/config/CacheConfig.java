package cn.eqxiu.mock.config;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    public Cache<String,Object> getCache(){
        TimedCache<String, Object> timedCache = CacheUtil.newTimedCache(1000 * 60 * 60 * 24);
        return timedCache;
    }
}
