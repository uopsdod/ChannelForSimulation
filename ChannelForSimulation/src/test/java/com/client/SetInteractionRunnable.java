package com.client;

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

public class SetInteractionRunnable implements Runnable{
	@Override
	public void run() {
		/**
		{
			"type":"setinteraction",
			"userID":"242c6936-e8f2-cb40-31b9-2e712a68bb02",
			"callid":"242c6936-e8f2-cb40-31b9-2e712a68bb02",
			"contactid":"1fb0448e-aece-48d9-a6a3-722fa8d1df1c",
			"ixnid":"4c47097d-1bbd-42d4-9886-3d313ec495dd",
			"agentid":"101",
			"entitytypeid":"2",
			"tenantID":"3"
		}
		 **/
		
		JsonObject inviteJson = new JsonObject();
		inviteJson.addProperty("type", "setinteraction");
		inviteJson.addProperty("userID", TestUtil.userID_client);
		inviteJson.addProperty("callid", TestUtil.userID_client);
		JsonObject userdataJson = Util.getGJsonObject(TestUtil.userdata);
		JsonArray customerDataAry = userdataJson.get("CustomerData").getAsJsonArray();
		JsonObject customerData = customerDataAry.get(0).getAsJsonObject(); 
		String contactid = customerData.get("contactid").getAsString();
		inviteJson.addProperty("contactid", contactid);
		inviteJson.addProperty("ixnid", TestUtil.roomID);
		inviteJson.addProperty("agentid", TestUtil.userID_agent);
		inviteJson.addProperty("entitytypeid", TestUtil.entityTypeID);
		inviteJson.addProperty("tenantID", TestUtil.tenantID);
		
		System.out.println("SetInteractionRunnable contactid: " + contactid);
		
		String json = Util.getGson().toJson(inviteJson);
		System.out.println("json:" + json);		
			
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);
		
	}
	
}
