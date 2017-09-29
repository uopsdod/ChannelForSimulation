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

public class DialInRspRunnable implements Runnable{
	@Override
	public void run() {
		/**
	{
		"type":"dial",
		"entityTypeID":"2",
		"userID":101,
		"fromUserDialNo":305,
		"invitedUserDialNo":"18181",
		"dialType":"dialIn"
	}
		 **/		
		
		JsonObject dialInJson = new JsonObject();
		dialInJson.addProperty("type", "dialrsp"); // 換成"dialrsp"
		dialInJson.addProperty("userID", TestUtil.dialNO_agent);
		dialInJson.addProperty("entityTypeID", TestUtil.entityTypeID);
		dialInJson.addProperty("fromUserDialNo", TestUtil.dialNO_agent);
		dialInJson.addProperty("invitedUserDialNo", TestUtil.dialNO_agent02);
		dialInJson.addProperty("dialType", "dialIn");
		dialInJson.addProperty("dialInRoomID", TestUtil.dialInRoomID);
		
		dialInJson.addProperty("respone", "accept");
		
		String json = Util.getGson().toJson(dialInJson);
		System.out.println("json:" + json);		
			
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);
		
	}
	
}
