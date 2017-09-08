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
public class AcceptEventRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
/**		
		{"type":"acceptevent",
			"userID":101,
			"roomID":"none",
			"memberListToJoin":[{"ID":"1cf59b0f-afeb-6695-f41c-c435bdf77194"},{"ID":101}],
			"channel":"chat"
		}
**/		
		
		/** 蒐集房間成員list **/
		List<RoomMember> memberListToJoin = new ArrayList<>();
		RoomMember agentMem = new RoomMember(TestUtil.userID_agent);
		memberListToJoin.add(agentMem);
		RoomMember clientMem = new RoomMember(TestUtil.userID_client);
		memberListToJoin.add(clientMem);
		Util.getConsoleLogger().info("memberListToJoin: " + memberListToJoin);
		
		Type type = new TypeToken<List<RoomMember>>() {}.getType();
		String memberListToJoinJsonStr = Util.getGson().toJson(memberListToJoin, type);
		JsonArray memberListToJoinJsonAry = Util.getGJsonArray(memberListToJoinJsonStr);
		Util.getConsoleLogger().info("memberListToJoinJsonAry: " + memberListToJoinJsonAry);
		
		AcceptEventBean acceptEventBean = new AcceptEventBean();
		acceptEventBean.setUserID(TestUtil.userID_agent);
		acceptEventBean.setRoomID("none");
		acceptEventBean.setMemberListToJoin(memberListToJoinJsonAry);
		acceptEventBean.setChannel(TestUtil.entityTypeID);
		
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, Util.getGson().toJson(acceptEventBean));
		
	}
	
	class RoomMember {
		private String ID;
		public RoomMember(String aID){
			this.ID = aID;
		}
	}
	
	
}
