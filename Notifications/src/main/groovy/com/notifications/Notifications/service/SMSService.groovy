package com.notifications.Notifications.service

import com.notifications.Notifications.config.SMSConfig
import com.notifications.Notifications.model.SMSConversation
import com.notifications.Notifications.model.User
import com.notifications.Notifications.repository.SMSConversationRepository
import com.notifications.Notifications.repository.UserRepository
import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.twiml.MessagingResponse
import com.twilio.twiml.messaging.Message as TwiMessage
import com.twilio.type.PhoneNumber
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.util.concurrent.CompletableFuture


@Service
class SMSService {
    private Logger logger = LoggerFactory.getLogger(SMSService.class)
    private String FROM_NUMBER
    private UserRepository userRepository
    private SMSConversationRepository smsConversationRepository

    @Autowired
    SMSService(SMSConfig smsConfig, UserRepository userRepository, SMSConversationRepository smsConversationRepository) {
        Twilio.init(smsConfig.accountSid, smsConfig.authToken)
        this.FROM_NUMBER = smsConfig.fromNumber
        this.userRepository = userRepository
        this.smsConversationRepository = smsConversationRepository
    }

    @Async("asyncTaskExecutor")
    CompletableFuture<Message> sendSMS(String username, String phoneNumber) {

        String messageBody = "Hello There! Testing new account!!"

        //Added next line just to simulate a heavy operation. Uncomment it if wanna use
        //Thread.sleep(10000L)


        //Check user in database via phone number
        //Add user to the database if he/she doesn't already exists.
        if (!userRepository.findByPhoneNumber(phoneNumber)) {
            ArrayList<String> conversation = new ArrayList<>()
            conversation.add(messageBody)

            User newUser = new User(username, phoneNumber)
            SMSConversation smsConversation = new SMSConversation(phoneNumber, conversation)

            userRepository.insert(newUser)
            smsConversationRepository.insert(smsConversation)

            logger.info("Added new user to the database!")
        } else {
            SMSConversation oldSMSConversation = smsConversationRepository.findByPhoneNumber(phoneNumber)
            oldSMSConversation.smsConversation.push(messageBody)
            smsConversationRepository.save(oldSMSConversation)
            logger.info("User already exists in database!")
        }

        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(FROM_NUMBER),
                messageBody
        ).create();

        return CompletableFuture.completedFuture(message);
    }

    String replyToSMS(String from, String body) {
        SMSConversation oldSMSConversation = smsConversationRepository.findByPhoneNumber(from)
        oldSMSConversation.smsConversation.add(body)

        String reply =  String.format("Dear %s, Hello from MAVQ. Last message that you sent said '%s'",from, body);
        oldSMSConversation.smsConversation.add(reply)

        smsConversationRepository.save(oldSMSConversation)

        TwiMessage sms = new TwiMessage.Builder(reply).build();
        MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
        return twiml.toXml();
    }

    @Async("asyncTaskExecutor")
    CompletableFuture<String> testAsync() throws InterruptedException {
        logger.info("Thread execution starts here! Hope this is a unique message!")
        Thread.sleep(10000L)
        logger.info("Hooraay! Test completed!")
        return CompletableFuture.completedFuture("Hello world!")
    }
}
