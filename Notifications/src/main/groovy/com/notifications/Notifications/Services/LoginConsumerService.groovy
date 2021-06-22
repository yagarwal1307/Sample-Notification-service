package com.notifications.Notifications.Services

import com.notifications.Notifications.Config.UserConfig
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class LoginConsumerService {
    private final smsService

    LoginConsumerService(SMSService smsService) {
        this.smsService = smsService
    }

    @KafkaListener(topics = "loginTopic", groupId = "group_id")
    void consume(String phoneNumber) {
        smsService.sendSMS('Yash Agarwal', phoneNumber)
    }
}
