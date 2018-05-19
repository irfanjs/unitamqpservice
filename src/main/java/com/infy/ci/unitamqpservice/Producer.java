//package com.infy.ci.unitamqpservice;
//
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.QueueingConsumer;
//import com.rabbitmq.client.AMQP.BasicProperties;
//
//public class Producer {
//
//    private Connection connection;
//    private Channel channel;
//    private static String server = "35.154.125.146";
//    //private static int port = 5672;
//  //  private static String vhost = "yourvhost";
//    private static String username = "guest";
//    private static String password = "guest";
//    private String exchangeName = "testEx";
//    private String replyQueue = "replyQ";
//    private String requestKey = "request";
//    private String replyKey = "reply";
//
//    private void produce() {
//        try {
//            //connect
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(server);
//      //      factory.setPort(port);
//        //    factory.setVirtualHost(vhost);
//            factory.setUsername(username);
//            factory.setPassword(password);
//            factory.setRequestedHeartbeat(60);
//            connection = factory.newConnection();
//            channel = connection.createChannel();
//
//            //listen for reply messages
//            String message = "Hello World!";
//            channel.queueDeclare(replyQueue, false, true, true, null);
//            channel.queueBind(replyQueue, exchangeName, replyKey, null);
//            QueueingConsumer qc = new QueueingConsumer(channel);
//            channel.basicConsume(replyQueue, true, qc);
//
//            //send request message
//            BasicProperties properties = new BasicProperties.Builder().
//                    contentType("text/plain").
//                    deliveryMode(1).
//                    replyTo(replyKey).
//                    build();
//            channel.basicPublish(exchangeName, requestKey, properties, message.getBytes());
//
//            //receive the reply message
//            QueueingConsumer.Delivery delivery = qc.nextDelivery();
//            String replyMessage = new String(delivery.getBody());
//            System.out.println(replyMessage);
//
//            //disconnect
//            connection.close();
//        } catch(Exception e) {
//            System.out.println(e);
//            System.exit(-1);            
//        }   
//    }
//
//    public static void main(String[] args) {
//        Producer p = new Producer();
//        p.produce();
//    }
//}