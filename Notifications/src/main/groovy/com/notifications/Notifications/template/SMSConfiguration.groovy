package com.notifications.Notifications.template

import com.notifications.Notifications.config.SMSConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class SMSConfiguration {
    SMSConfig config

    @Autowired
    SMSConfiguration(SMSConfig smsConfig) {
        this.config = smsConfig
    }
}
