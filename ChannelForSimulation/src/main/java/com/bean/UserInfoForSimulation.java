package com.bean;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

//import com.amqp.backend.thread.findAgent.FindAgentCallable;
//import com.util.StatusEnum;
      
  
//此類別給WebSocketPool.userallconnections使用
public class UserInfoForSimulation extends UserInfo{
	private String servingAgentForSimulation;
	private String roomIDForSimulation;

	public String getRoomIDForSimulation() {
		return roomIDForSimulation;
	}

	public void setRoomIDForSimulation(String roomIDForWechat) {
		this.roomIDForSimulation = roomIDForWechat;
	}

	public String getServingAgentForSimulation() {
		return servingAgentForSimulation;
	}

	public void setServingAgentForSimulation(String servingAgentForWechat) {
		this.servingAgentForSimulation = servingAgentForWechat;
	}
}



