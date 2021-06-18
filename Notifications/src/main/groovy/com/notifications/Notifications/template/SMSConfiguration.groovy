package com.notifications.Notifications.template

import com.notifications.Notifications.Config.SMSConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix="sms")
class SMSConfiguration {
    SMSConfig config

    @Autowired
    SMSConfiguration(SMSConfig smsConfig) {
        this.config = config
    }
}
