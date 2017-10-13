package com.agent;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bean.AcceptEventBean;
import com.bean.LoginBean;
import com.bean.UpdateStatusBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.util.AmqpUtil;
import com.util.EntityTypeEnum;
import com.util.TestUtil;
import com.util.Util;

public class LogoutVoiceRunnable implements Runnable{
	@Override
	public void run() {
		/**
		{
			"type":"logoutvoice",
			"userID":815,
			"entityTypeID":"1",
			"tenantID":"3"
		}
		 **/		
		
		JsonObject logoutvoiceJson = new JsonObject();
		logoutvoiceJson.addProperty("type", "logoutvoice");
		logoutvoiceJson.addProperty("userID", TestUtil.dialNO_agent);
		logoutvoiceJson.addProperty("tenantID", TestUtil.tenantID);
		logoutvoiceJson.addProperty("entityTypeID", TestUtil.entityTypeID);
		
		String json = Util.getGson().toJson(logoutvoiceJson);
		System.out.println("json:" + json);		
			
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);		
		
	}
	
}
