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
public class LeaveRoomRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
		/**		
		{
			"type":"leaveroom",
			"userID":101,
			"roomID":"fc22c5d4-8d46-44ea-b8f0-a2c6df7a7975",
			"channel":"chat"
		}
	**/		
			
			/** 蒐集房間成員list **/
			JsonObject leaveroomJson = new JsonObject();
			leaveroomJson.addProperty("type", "leaveroom");
			leaveroomJson.addProperty("userID", TestUtil.dialNO_agent);
			leaveroomJson.addProperty("roomID", TestUtil.roomID);
			leaveroomJson.addProperty("channel", TestUtil.entityTypeID);
			leaveroomJson.addProperty("entityTypeID", TestUtil.entityTypeID);
			
			String json = Util.getGson().toJson(leaveroomJson);
			System.out.println("json:" + json);
			AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, json);
	
	}
	
	class RoomMember {
		private String ID;
		public RoomMember(String aID){
			this.ID = aID;
		}
	}
	
	
}
