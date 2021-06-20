package com.notifications.Notifications.Models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class SMSConversation {
    @Id
    String conversationId

    String phoneNumber
    ArrayList<String> smsConversation

    SMSConversation(String phoneNumber, ArrayList<String> smsConversation) {
        this.phoneNumber = phoneNumber
        this.smsConversation = smsConversation
    }
}
