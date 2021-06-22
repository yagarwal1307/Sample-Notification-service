package com.notifications.Notifications.service


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
class LoginProducerService {
    private final KafkaTemplate<String, String> loginTemplate;
    private final String TOPIC = "loginTopic";

    LoginProducerService(KafkaTemplate<String, String> loginTemplate) {
        this.loginTemplate = loginTemplate;
    }

    void sendMessage(String phoneNumber, String name) {
//        UserConfig user = new UserConfig(name, phoneNumber)
        this.loginTemplate.send(TOPIC, phoneNumber);
    }
}