package com.kranflex.outage.configuration;

import feign.Logger;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class FeignClientConfiguration {

    @Bean
    public FeignClientErrorDecoder serviceClientErrorDecoder() {
        return new FeignClientErrorDecoder();
    }

    @Bean
    public OkHttpClient serviceHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

}
