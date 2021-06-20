package com.notifications.Notifications.Config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "sms")
class SMSConfig {
    String accountSid
    String fromNumber
    String authToken
}
