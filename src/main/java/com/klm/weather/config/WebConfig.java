package com.klm.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.klm.weather.interceptor.QueryParamValidationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private QueryParamValidationInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/weather");
    }
}

