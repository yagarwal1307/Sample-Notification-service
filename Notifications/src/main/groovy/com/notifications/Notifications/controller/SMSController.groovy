package com.notifications.Notifications.controller

import com.notifications.Notifications.annotation.ValidateTwilioSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import com.twilio.rest.api.v2010.account.Message
import com.notifications.Notifications.service.SMSService

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@RestController
public class SMSController {

    private SMSService smsService;
    private Logger logger = LoggerFactory.getLogger(SMSService.class)

    @Autowired
    SMSController(final SMSService smsService ) {
        this.smsService = smsService;
    }

    @RequestMapping(value='/sms', method= RequestMethod.POST)
    String sensSMS(@RequestParam("PhoneNumber") String phoneNumber, @RequestParam("UserName") String userName) {
        smsService.sendSMS(userName, phoneNumber)
        return "Thanks for registering! Message will be sent to your number!"
    }

    @RequestMapping(value = '/sms/reply', produces = "application/xml", method=RequestMethod.POST)
    @ValidateTwilioSignature
    @ResponseBody
    String replyToSMS(@RequestParam("From") String from, @RequestParam("Body") String body) {
        return smsService.replyToSMS(from, body)
    }

    @RequestMapping(value="/testingAsync", method= RequestMethod.GET)
    String testAsync() throws InterruptedException, ExecutionException{
        logger.info("Async test starts here! Lets gooo!!!")

        CompletableFuture<String> testMessage = smsService.testAsync()

        /*If next line is uncommented this method will wait for the async method to get completed.
         get() method of completableFuture works like await in case of Javascript*/
        //logger.info("Here is my test reply: {}", testMessage.get())

        return "Hello world! Yash here!"
    }
}
