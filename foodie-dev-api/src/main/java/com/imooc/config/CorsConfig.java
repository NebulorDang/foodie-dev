package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsConfig() {

    }

    @Bean
    public CorsFilter corsFilter() {
        // 1.添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://192.168.7.194:8080");
        config.addAllowedOrigin("http://localhost:8080");

        // 设置是否发送cookie信息
        config.setAllowCredentials(true);

        // 开放哪些Http方法，允许跨域访问
        config.addAllowedMethod("*");

        // 设置允许的header
        config.addAllowedHeader("*");

        // 添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        // 返回重新定义好的corsSource
        return new CorsFilter(configSource);
    }
}
