package com.notifications.Notifications.repository

import com.notifications.Notifications.Models.SMSConversation
import org.springframework.data.mongodb.repository.MongoRepository

interface SMSConversationRepository extends MongoRepository<SMSConversation, String>{
    SMSConversation findByPhoneNumber(String phoneNumber)
}