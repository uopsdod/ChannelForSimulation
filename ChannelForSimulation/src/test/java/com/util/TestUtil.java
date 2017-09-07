package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.util.Util;

@Component
public class TestUtil{
	public static String userID = UUID.randomUUID().toString();
	public static String callID = UUID.randomUUID().toString();
	public static String tenantID = "3";
	public static String QueueName = AmqpUtil.QUEUE_NAME.BACKEND_TO_VOICE_QUEUE;

	private final AmqpTemplate template;
	private ApplicationContext appContext;
	private static AtomicInteger amqpRequestCount = new AtomicInteger();
	
	@Autowired
	public TestUtil(AmqpTemplate aTemplate, ApplicationContext aApplicationContext){
		System.out.println("TestSender() called");
		this.template = aTemplate;
		this.appContext = aApplicationContext;
	}
	
	public static final String url_8080 = "http://localhost:8080/Info360WebAPI/RESTful";
	public static final String url_8082 = "http://localhost:8082/Info360WebAPI/RESTful";
	
	public static final String backendServiceUUID = "BackendService" + UUID.randomUUID().toString();
	public static final String amqpRequestCountTAG = "amqpRequestCount";
	public static final int amqpRequestCountRound = 10;
	
	/**
	 * test if there is no subscriber that uses the same rabbtmq server 
	 */
	public void testVoiceListener(){
//		String rabbitUUID = "" + UUID.randomUUID().toString();
//		Util.getConsoleLogger().info("TestSender - TestUtil.backendServiceUUID: " + TestUtil.backendServiceUUID);
//		/** 驗證rabbitmq是否運作 **/
////		int round = 10;
//		Util.getUnitTestLoggerFileLogger().info("驗證rabbitmq 是否有多人在搶用請求(成功狀況:須為1~10連續數字)");
//		for (int i = 0; i < TestUtil.amqpRequestCountRound; i++){
//			String data = "foo 中文 123";
//			JsonObject jsonOut = new JsonObject();
//			jsonOut.addProperty("test", true); // 重要-測試flag
//			jsonOut.addProperty("data", data);
//			jsonOut.addProperty(TestUtil.amqpRequestCountTAG, amqpRequestCount.incrementAndGet()); // 重要-測試flag
//			
//			Util.getConsoleLogger().info("TestSender - jsonOut.toString()" + jsonOut.toString());
//			template.convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, jsonOut.toString());
//		}
		JsonObject jsonOut = new JsonObject();
		jsonOut.addProperty("key01", "value01");
		template.convertAndSend(AmqpUtil.QUEUE_NAME.BACKEND_TO_VOICE_QUEUE, jsonOut.toString());
	}
	
}
