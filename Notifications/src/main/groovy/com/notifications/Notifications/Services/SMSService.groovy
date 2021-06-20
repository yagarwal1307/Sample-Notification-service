package com.notifications.Notifications.Services

import com.notifications.Notifications.Config.SMSConfig
import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.twiml.MessagingResponse
import com.twilio.twiml.messaging.Message as TwiMessage
import com.twilio.type.PhoneNumber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SMSService {
    private String FROM_NUMBER;

    @Autowired
    SMSService(SMSConfig smsConfig) {
        Twilio.init(smsConfig.accountSid, smsConfig.authToken)
        this.FROM_NUMBER = smsConfig.fromNumber
    }

    Message sendSMS() {
        Message message = Message.creator(
                new PhoneNumber("+18046550106"),
                new PhoneNumber(FROM_NUMBER),
                "Hello There! Testing new account!!"
        ).create();

        return message;
    }

    String replyToSMS(String from, String body) {
        String message = String.format("Dear %s, Hello from MAVQ. Last message that you sent said '%s'",from, body);
        TwiMessage sms = new TwiMessage.Builder(message).build();
        MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();Twilio

        return twiml.toXml();
    }
}
