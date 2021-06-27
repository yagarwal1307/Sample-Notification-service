package com.notifications.Notifications.service

import com.notifications.Notifications.config.UserConfig
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class LoginConsumerService {
    private final smsService

    LoginConsumerService(SMSService smsService) {
        this.smsService = smsService
    }

    @KafkaListener(topics = "loginTopic", groupId = "group_id")
    void consume(UserConfig user) {
        //System.out.println("Hello "+ user)
        smsService.sendSMS(user.userName, user.phoneNumber)
    }
}
