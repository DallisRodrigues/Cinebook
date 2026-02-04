package com.cinebook.movieservice.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance");
        
        // Define a cache named "movies" that expires after 10 minutes
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("movies");
        mapConfig.setTimeToLiveSeconds(600); // 10 Minutes
        config.addMapConfig(mapConfig);
        
        return config;
    }
}