//package com.notifications.Notifications.serializers
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.notifications.Notifications.Config.UserConfig
//import org.apache.kafka.common.serialization.Serializer
//
//class UserSerializer implements Serializer{
//    @Override
//    void configure(Map<String, ?>map, boolean b) {
//
//    }
//
//    @Override
//    byte[] serialize(String topic, UserConfig data) {
//        byte[] retVal =  null
//        ObjectMapper objectMapper = new ObjectMapper()
//        try {
//            retVal = objectMapper.writeValueAsString(data).getBytes()
//        } catch (Exception e){
//            e.printStackTrace()
//        }
//        return retVal
//    }
//
//    @Override
//    void close() {
//
//    }
//}
