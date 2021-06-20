package com.notifications.Notifications.repository

import com.notifications.Notifications.Models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    User findByPhoneNumber(String phoneNumber)
}