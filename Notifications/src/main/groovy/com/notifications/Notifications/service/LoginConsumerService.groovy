package com.notifications.Notifications.service


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
//        smsService.sendSMS(user.userName, user.phoneNumber)
        System.out.println("Hello "+ phoneNumber)
    }
}
