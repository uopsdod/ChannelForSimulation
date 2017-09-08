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
public class RingStartRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
/**		
 * {
 * 	"type":"updateStatus",
 * 	"status":"6",
 * 	"startORend":"start",
 * 	"clientID":"c9fffcd4-7291-59c6-f0ec-4addebea64a3",
 * 	"entityTypeID":"2",
 * 	"ring_dbid":"17",
 * 	"Event":"updatestatus",
 * 	"userID":"101",
 * 	"currStatusEnum":"RING",
 * 	"remoteIP":null,
 * 	"ACType":"Agent"
 * }
**/		
		UpdateStatusBean updateStatusBean = new UpdateStatusBean();
		updateStatusBean.setStatus("6");
		updateStatusBean.setStartORend("start");
		updateStatusBean.setClientID(TestUtil.userID_client);
		updateStatusBean.setEntityTypeID(EntityTypeEnum.WEBCHAT.getEntityTypeID());
		updateStatusBean.setEvent("updatestatus");
		updateStatusBean.setUserID(TestUtil.userID_agent);
		
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.BACKEND_TO_WEBCHAT_QUEUE, Util.getGson().toJson(updateStatusBean));
	}
	
	class RoomMember {
		private String ID;
		public RoomMember(String aID){
			this.ID = aID;
		}
	}
	
	
}
