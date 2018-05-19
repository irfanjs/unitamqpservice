//package com.infy.ci.unitamqpservice;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UnitAmqpListener {
//
//    private static final Logger log = LoggerFactory.getLogger(UnitAmqpListener.class);
//
//    @RabbitListener(queues = UnitamqpserviceApplication.QUEUE_aggregate_NAME)
//    public void receiveMessage(final Message message) {
//        log.info("Received message as generic: {}", message.toString());
//    }
//
//  
//}