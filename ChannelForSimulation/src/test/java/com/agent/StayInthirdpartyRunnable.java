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

public class StayInthirdpartyRunnable implements Runnable{
	private String resp;
	
	public StayInthirdpartyRunnable(String resp) {
		super();
		this.resp = resp;
	}

	@Override
	public void run() {
		/**
		{
		type: "stayinthirdparty"
		, userID: 101
 		, response: "yes"
 		, entityTypeID: "2"
 		, roomID:"87518c6f-f312-48a5-944c-"
 		}
		 **/		
		
		JsonObject inviteJson = new JsonObject();
		inviteJson.addProperty("type", "stayinthirdparty");
		inviteJson.addProperty("userID", TestUtil.dialNO_agent);
		inviteJson.addProperty("response", this.resp); // 退出三方
		inviteJson.addProperty("roomID", TestUtil.roomID); 
		inviteJson.addProperty("entityTypeID", TestUtil.entityTypeID);
		
		String json = Util.getGson().toJson(inviteJson);
		System.out.println("json:" + json);		
			
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);
		
	}
	
}
