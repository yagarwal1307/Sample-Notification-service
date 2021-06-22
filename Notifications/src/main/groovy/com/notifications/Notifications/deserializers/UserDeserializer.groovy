//package com.notifications.Notifications.deserializers
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.notifications.Notifications.Config.UserConfig
//import org.apache.kafka.common.serialization.Deserializer
//
//class UserDeserializer implements Deserializer{
//
//    @Override void configure(Map<String, ?>map, boolean b) {}
//
//    @Override
//    UserConfig deserialize(String topic, byte[] data) {
//        ObjectMapper mapper = new ObjectMapper()
//        UserConfig user = null
//        try {
//            user = mapper.readValue(data, UserConfig.class)
//        } catch (Exception e) {
//            e.printStackTrace()
//        }
//
//        return user
//    }
//
//    @Override void close() {}
//}
