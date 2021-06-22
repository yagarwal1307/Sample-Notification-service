package com.notifications.Notifications.Config

class UserConfig {
    String userName
    String phoneNumber

    UserConfig(String userName, String phoneNumber) {
        this.userName = userName
        this.phoneNumber = phoneNumber
    }

    @Override
    String toString() {
        return "UserConfig [userName=" + userName + ", phoneNumber=" + phoneNumber + "]";
    }
}