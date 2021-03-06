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

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RspSenduserdataRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
		JsonObject senduserdataRsp = new JsonObject();
		senduserdataRsp.addProperty("userID", TestUtil.dialNO_agent);
		senduserdataRsp.addProperty("type", "senduserdata"); // 新增
		senduserdataRsp.addProperty("Event", "senduserdata");
		senduserdataRsp.addProperty("clientName", TestUtil.userName_client);
		senduserdataRsp.addProperty("clientID", TestUtil.userID_client);
		senduserdataRsp.addProperty("clientPilotID", TestUtil.pilotID_client);
		senduserdataRsp.addProperty("clientEntityTypeID", EntityTypeEnum.VOICE.getEntityTypeID());
		senduserdataRsp.addProperty("entityTypeID", EntityTypeEnum.VOICE.getEntityTypeID());
		senduserdataRsp.add("userdata", Util.getGJsonObject(TestUtil.userdata));
		
		String json = Util.getGson().toJson(senduserdataRsp);
		System.out.println("json:" + json);
//		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.BACKEND_TO_WEBCHAT_QUEUE, json);
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);
	}
	
	class RoomMember {
		private String ID;
		public RoomMember(String aID){
			this.ID = aID;
		}
	}
	
	/**		
	{
	    "userID": "101", 
	    "Event": "senduserdata", 
	    "clientName": "B123456789", 
	    "clientID": "35813232-7b4c-f3d1-f287-c853a4e6848d", 
	    "userdata": {
	        "mapping": {
	            "Message": {
	                "phone": {
	                    "displayName": "電話", 
	                    "sort": "1", 
	                    "defaultName": "phone"
	                }, 
	                "contactID": {
	                    "displayName": "contactID", 
	                    "defaultName": "contactID"
	                }, 
	                "callNotice": {
	                    "displayName": "callNotice", 
	                    "defaultName": "callNotice"
	                }, 
	                "id": {
	                    "displayName": "身分證字號", 
	                    "sort": "0", 
	                    "defaultName": "id"
	                }
	            }
	        }, 
	        "CustomerData": [
	            {
	                "phone": "", 
	                "contactid": "0af74032-8a5e-49b2-85dd-736d9c59517e", 
	                "callnotice": "", 
	                "id": "B123456789"
	            }
	        ], 
	        "tenantID": "3", 
	        "typeID": "2"
	    }, 
	    "remoteIP": "localhost", 
	    "ACType": "Agent"
	}
	**/
	
}
