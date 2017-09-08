package com.util;

import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmqpUtil {
	private static AmqpTemplate amqpTemplate;
	
	@Autowired
	public AmqpUtil(AmqpTemplate template){
		System.out.println("Util() called");
		AmqpUtil.amqpTemplate = template;
	}	
	
	public static AmqpTemplate getAmqpTemplate() {
		return amqpTemplate;
	}
	public static void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		AmqpUtil.amqpTemplate = amqpTemplate;
	}



	public static enum LOG_LEVEL{
		INFO, WARN;
	}
	
	private Map<String, String> mapProp;
	
	// Consumers - channel
	public static class QUEUE_NAME{

		public static final String CHANNEL_TO_BACKEND_QUEUE01 = "CHANNEL_TO_BACKEND_QUEUE01";
//		public static final String CHANNEL_TO_BACKEND_QUEUE02 = "CHANNEL_TO_BACKEND_QUEUE02";
		
		// Consumers - channel
		public static final String BACKEND_TO_WEBCHAT_QUEUE = "BACKEND_TO_WEBCHAT_QUEUE";
//		public static final String BACKEND_TO_LINE_QUEUE = "BACKEND_TO_LINE_QUEUE";
//		public static final String BACKEND_TO_WECHAT_QUEUE = "BACKEND_TO_WECHAT_QUEUE";
		public static final String BACKEND_TO_VOICE_QUEUE = "BACKEND_TO_VOICE_QUEUE";	
	}
}
