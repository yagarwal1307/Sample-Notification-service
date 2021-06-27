package com.notifications.Notifications.service

import com.notifications.Notifications.config.UserConfig
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
class LoginProducerService {
    private final KafkaTemplate<String, UserConfig> loginTemplate;
    private final String TOPIC = "loginTopic";

    LoginProducerService(KafkaTemplate<String, UserConfig> loginTemplate) {
        this.loginTemplate = loginTemplate;
    }

    void sendMessage(String phoneNumber, String name) {
        UserConfig user = new UserConfig(name, phoneNumber)
        this.loginTemplate.send(TOPIC, user);
    }
}