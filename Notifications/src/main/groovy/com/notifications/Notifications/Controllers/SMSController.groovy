package com.notifications.Notifications.Controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import com.twilio.rest.api.v2010.account.Message
import com.notifications.Notifications.Services.SMSService

@RestController
public class SMSController {

    private SMSService smsService;

    @Autowired
    SMSController(final SMSService smsService ) {
        this.smsService = smsService;
    }

    @RequestMapping(value='/sms', method= RequestMethod.POST)
    Message sensSMS() {
        return smsService.sendSMS()
    }

    @RequestMapping(value = '/', produces = "application/xml", method=RequestMethod.POST)
    @ResponseBody
    String replyToSMS(@RequestParam("From") String from, @RequestParam("Body") String body) {
        return smsService.replyToSMS(from, body)
    }

}
