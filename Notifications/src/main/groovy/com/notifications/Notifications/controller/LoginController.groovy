package com.notifications.Notifications.controller

import com.notifications.Notifications.service.LoginProducerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
    private final loginProducerService

    @Autowired
    LoginController( LoginProducerService loginProducerService) {
        this.loginProducerService = loginProducerService
    }

    @RequestMapping(value='/login', method = RequestMethod.POST)
    String login(@RequestParam("PhoneNumber") String phoneNumber, @RequestParam("UserName") String userName) {
        loginProducerService.sendMessage(phoneNumber, userName)
        return "Welcome"
    }
}
