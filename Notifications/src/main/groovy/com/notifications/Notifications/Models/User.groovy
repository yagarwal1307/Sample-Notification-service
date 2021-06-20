package com.notifications.Notifications.Models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User {
    @Id
    private String userId
    private String userName
    private String phoneNumber

    String getUserName() {
        return this.userName
    }

    void setUserName(String userName) {
        this.userName = userName
    }

    String getUserPhoneNumber() {
        return this.phoneNumber
    }

    void setUserPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber
    }

    String getUserId() {
        return this.userId
    }
}
