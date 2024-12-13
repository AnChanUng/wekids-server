package com.wekids.backend.config;

import com.wekids.backend.utils.masking.strategy.DataMaskingStrategy;
import com.wekids.backend.utils.masking.strategy.MaskingStrategy;
import com.wekids.backend.utils.masking.strategy.NoMaskingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MaskingStrategyConfig {

    @Bean
    @Profile({"release", "test"})
    public DataMaskingStrategy noMaskingStrategy() {
        return new NoMaskingStrategy();
    }

    @Bean
    @Profile({"dev"})
    public DataMaskingStrategy maskingStrategy() {
        return new MaskingStrategy();
    }
}
