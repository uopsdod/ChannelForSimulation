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

public class Inviteagentthirdpartyvoice_ThirdPartyRunnable implements Runnable{
	@Override
	public void run() {
		/**
		 * 
{
	"type":"inviteAgentThirdParty",
	"roomID":"8b2717ab-9dce-4c99-8d7e-15adefb18763",
	"userID":101,
	"fromAgentID":101,
	"invitedAgentID":"102",
	"inviteType":"thirdParty",
	"userdata":{
		"mapping":{
			"Message":{
				"phone":{
					"displayName":"電話",
					"dbid":"22",
					"sort":"1",
					"defaultName":"phone",
					"key":"phone",
					"value":""
				},
				"contactID":{
					"displayName":"contactID",
					"dbid":"23",
					"defaultName":"contactID"
				},
				"id":{
					"displayName":"身分證字號",
					"dbid":"21",
					"sort":"0",
					"defaultName":"id",
					"key":"id",
					"value":"A123456789"
				}
			}
		},
		"CustomerData":[
			{
			"phone":"",
			"contactid":"e0cb44f9-d0ea-46d3-ae70-47845f11e356",
			"callnoticeList":"[]",
			"id":"A123456789"
			}
		],
		"tenantID":"3",
		"typeID":"2"
	},
	"text":"<ul><li><div><div class=\"layim-chat-user pull-left\"><img src=\"resources/layui/css/pc/layim/skin/new_logo.jpg\"></div><div class=\"layim-chat-text\">系統訊息: A 進入聊天視窗</div></div></li></ul>",
	"response":null,
	"entitytypeid":"2",
	"channel":"2"
	}		 * 
		 **/		
		
		JsonObject inviteJson = new JsonObject();
		inviteJson.addProperty("type", "inviteagentthirdpartyvoice");
		inviteJson.addProperty("userID", TestUtil.dialNO_agent);
		inviteJson.addProperty("roomID", TestUtil.roomID); // 要有房間建立
		inviteJson.add("userdata",  Util.getGJsonObject(TestUtil.userdata)); // client要登入
		inviteJson.addProperty("fromAgentID", TestUtil.dialNO_agent);
		inviteJson.addProperty("invitedAgentID", TestUtil.dialNO_agent02); // 第二位agent要登入
		inviteJson.addProperty("inviteType", "thirdParty");
		inviteJson.addProperty("entityTypeID", TestUtil.entityTypeID);
		inviteJson.addProperty("channel", TestUtil.entityTypeID);
		
		String json = Util.getGson().toJson(inviteJson);
		System.out.println("json:" + json);		
			
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);
		
	}
	
}
