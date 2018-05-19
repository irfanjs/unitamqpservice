package com.infy.ci.unitamqpservice;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Controller
@Configuration
@PropertySource("classpath:/application.properties")
@RequestMapping("/unittestdata")
public class UnitAmqpRestController {
	//private final RabbitTemplate rabbitTemplate;
	 Environment env;
	 private String replyQueueName;
	 
	 private Connection connection;
	    private Channel channel;
	    private String requestQueueName = "rpc_queue1";
	    @Value("${spring.rabbitmq.host}")
	    private String rabHost;
	    
	    @Bean
	    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	            return new PropertySourcesPlaceholderConfigurer();
	    }
	    
	    
	 private Logger logger = LoggerFactory.getLogger(UnitAmqpRestController.class);
	 
	
//	@Autowired	
//	  public UnitAmqpRestController(RabbitTemplate rabbitTemplate) {
//		    this.rabbitTemplate = rabbitTemplate;
//		  }
	 
	 
	 public UnitAmqpRestController() throws IOException, TimeoutException
	 {
		  ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("35.154.27.134");

	        connection = factory.newConnection();
	        channel = connection.createChannel();
	 }
	 
	  @Autowired
	    RabbitTemplate rabbitTemplate;
	 
	@RequestMapping(value="/{projectid}/ut/aggregate",   
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)    
    public @ResponseBody String getAggregatedDataForSectionOfNightlyBuild (@PathVariable("projectid") int projectid,
			@RequestParam("buildtype") String buildtype,
			@RequestParam("build") String build) throws Exception {
		
		buildtype = "nightly";
		
		
		if(build.toLowerCase().equals("latest") && buildtype.equals("nightly")){
			
			 String message = String.format("aggregate");
			 return rabbitTemplate.convertSendAndReceive("myExchange", requestQueueName, message).toString();	 
//commented today
			 
			//commented today		 
	//		String corrId = UUID.randomUUID().toString();
	//commented today		
	
			 //	        AMQP.BasicProperties props = new AMQP.BasicProperties
		//                .Builder()
		  //              .correlationId(corrId)
		    //            .replyTo(replyQueueName)
		      //          .build();

		//        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

		  //      final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

		    //    channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
		      //      @Override
		       //     public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		       //         if (properties.getCorrelationId().equals(corrId)) {
		      //              response.offer(new String(body, "UTF-8"));
		      //          }
		     //       }
		     //   });
		        
		        
		  //     return response.take().toString();
		    
		}
		else
		{
			return null;
		}
		
	}
	
	 public void close() throws IOException {
	        connection.close();
	    }
}
