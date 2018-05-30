package com.infy.ci.unitamqpservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
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
@RequestMapping("/unittestdata")
public class UnitAmqpRestController {

	private String replyQueueName;

	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue_unit";

	private final Logger logger = LoggerFactory.getLogger(UnitAmqpRestController.class);
	private final RabbitTemplate rabbitTemplate;

	public static String getProperty() {
		Properties properties = new Properties();
		try {
			File file = ResourceUtils.getFile("classpath:application.properties");
			InputStream in = new FileInputStream(file);
			properties.load(in);
		} catch (IOException e) {

		}
		return properties.getProperty("spring.rabbitmq.host");

	}

	@Autowired
	public UnitAmqpRestController(RabbitTemplate rabbitTemplate) throws IOException, TimeoutException {
		this.rabbitTemplate = rabbitTemplate;
		rabbitTemplate.setReplyTimeout(15_000L);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(getProperty());
		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	@RequestMapping(value = "/{projectid}/ut/aggregate", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getAggregatedDataForSectionOfNightlyBuild(@PathVariable("projectid") int projectid,
			@RequestParam("buildtype") String buildtype, @RequestParam("build") String build) throws Exception {
		String message = String.format("aggregate" + "-" + projectid + "-" + build + "-" + buildtype);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for aggregate");
		}
		return returned.toString();
	}

	@RequestMapping(value = "/{projectid}/ut/modulewise", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getModulewiseDataForSectionOfCiBuild(@PathVariable("projectid") int projectid,
			@RequestParam("buildtype") String buildtype, @RequestParam("build") String build) throws Exception {

		String message = String.format("modulewise" + "-" + projectid + "-" + build + "-" + buildtype);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for modulewise");
		}
		return returned.toString();
	}

	@RequestMapping(value = "/{projectid}/ut/week", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getWeekDataForSectionBuild(@PathVariable("projectid") int projectid) throws Exception {

		String message = String.format("week" + "-" + projectid);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for week");
		}
		return returned.toString();
	}

	@RequestMapping(value = "/{projectid}/ut/month", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getMonthDataForSectionBuild(@PathVariable("projectid") int projectid) throws Exception {

		String message = String.format("month" + "-" + projectid);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for month");
		}
		return returned.toString();
	}

	@RequestMapping(value = "/{projectid}/ut/custom", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getCustomDataForSectionOfNightlyBuild(@PathVariable("projectid") int projectid,
			@RequestParam("todate") String todate, @RequestParam("fromdate") String fromdate) throws Exception {

		String message = String.format("custom" + "-" + projectid + "-" + todate + "-" + fromdate);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for custom");
		}
		return returned.toString();
	}

	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getProjectDetails() throws ClassNotFoundException, IOException, SQLException {

		int projectid = 1;

		String message = String.format("projects" + "-" + projectid);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for projects");
		}
		return returned.toString();

	}

	@RequestMapping(value = "/{projectid}/latestnightlybuilds", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getLatestAvailableNightlyBuilds(@PathVariable("projectid") int projectid)
			throws Exception {

		String message = String.format("latestnightlybuilds" + "-" + projectid);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for latestnightlybuilds");
		}
		return returned.toString();

	}

	@RequestMapping(value = "/daterange", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getdaterange() throws Exception {

		int projectid = 1;

		String message = String.format("daterange" + "-" + projectid);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for daterange");
		}
		return returned.toString();

	}

	@RequestMapping(value = "/{projectid}/ut/{buildnumber}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getbuildnumberwiseinfo(@PathVariable("projectid") int projectid,
			@PathVariable("buildnumber") int buildnumber) throws Exception {

		String message = String.format("buildnumber" + "-" + projectid + "-" + buildnumber);
		logger.info("Sending: " + message);
		Object returned = rabbitTemplate.convertSendAndReceive("", requestQueueName, message);
		logger.info("Reply: " + returned);
		if (returned == null) {
			throw new RuntimeException("failed to get a response for buildnumber");
		}
		return returned.toString();

	}

	public void close() throws IOException {
		connection.close();
	}
}
