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
	    
	    private final Logger logger = LoggerFactory.getLogger(UnitAmqpRestController.class);
	    private final RabbitTemplate rabbitTemplate;
	 
	    @Autowired
	    public UnitAmqpRestController(  RabbitTemplate rabbitTemplate) throws IOException, TimeoutException {
	    	 this.rabbitTemplate = rabbitTemplate;
	         rabbitTemplate.setReplyTimeout(15_000L);
	         
		 ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("13.232.79.81");

	        connection = factory.newConnection();
	        channel = connection.createChannel();
	 }  
	 
	@RequestMapping(value="/{projectid}/ut/aggregate",   
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)    
	  public @ResponseBody String getAggregatedDataForSectionOfNightlyBuild(@PathVariable("projectid") int projectid,
	            @RequestParam("buildtype") String buildtype, @RequestParam("build") String build) throws Exception {	
		if(build.toLowerCase().equals("latest") && buildtype.equals("nightly")){
			
			 String message = String.format("aggregate");
			  logger.info("Sending: " + message);
			  Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
	            logger.info("Reply: " + returned);
	            if (returned == null) {
	                throw new RuntimeException("failed to get a response");
	            }
	            return returned.toString();

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
