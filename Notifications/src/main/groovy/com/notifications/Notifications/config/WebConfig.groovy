package com.notifications.Notifications.config

import com.notifications.Notifications.interceptor.TwilioValidationHandlerInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig implements WebMvcConfigurer {

    private TwilioValidationHandlerInterceptor handlerInterceptor

    @Autowired
    WebConfig(TwilioValidationHandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor)
    }
}
