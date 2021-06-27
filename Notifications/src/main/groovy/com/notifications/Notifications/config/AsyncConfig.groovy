package com.notifications.Notifications.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig {
    @Bean(name = "asyncTaskExecutor")
    Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
        executor.setCorePoolSize(3)
        executor.setMaxPoolSize(3)
        executor.setQueueCapacity(100)
        executor.setThreadNamePrefix("AsyncThread-")
        executor.initialize()
        return executor
    }
}
