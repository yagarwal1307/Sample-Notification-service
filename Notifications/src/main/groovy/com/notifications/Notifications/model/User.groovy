package com.notifications.Notifications.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User {
    @Id
    String userId

    String userName
    String phoneNumber

    User(String userName, String phoneNumber) {
        this.userName = userName
        this.phoneNumber = phoneNumber
    }

}
