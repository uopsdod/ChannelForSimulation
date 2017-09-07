package com.spring;

import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.util.AmqpUtil;
import com.util.TestUtil;
import com.util.Util;

@Component
public class TestSender implements CommandLineRunner{
	private AmqpTemplate template;
//	private ApplicationContext appContext;
	private TestUtil testUtil;
	
	@Autowired
	public TestSender(TestUtil testUtil){
		System.out.println("TestSender() called");
		this.testUtil = testUtil;
	}

	public void run(String... arg0) throws Exception {
		Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 開始測試");
		
		testUtil.testVoiceListener();
		
		Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 結束測試");
	}

}