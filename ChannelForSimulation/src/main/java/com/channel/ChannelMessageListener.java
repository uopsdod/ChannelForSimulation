package com.channel;

import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.bean.ExitBean;
import com.bean.FindAgentBean;
import com.bean.Interaction;
import com.bean.UserCacheBean;
import com.bean.UserInfoForSimulation;
import com.google.gson.JsonObject;
import com.util.AmqpUtil;
import com.util.ChannelUtil;
import com.util.Util;

public class ChannelMessageListener implements MessageListener{

	@Override
	public void onMessage(Message aByteMsg) {
    	Util.getFileLogger().info("IN BACKEND_TO_VOICE_QUEUE starts");
    	Util.getFileLogger().info("IN BACKEND_TO_VOICE_QUEUE - aByteMsg: " + aByteMsg);
    	String aMsg = null;
		try {
			aMsg = new String(aByteMsg.getBody(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		Util.getFileLogger().info("IN process_BACKEND_TO_WECHAT_QUEUE - aMsg: " + aMsg);
		Util.getConsoleLogger().debug("process_BACKEND_TO_WECHAT_QUEUE() called - [x] Received -  aMsg: "+ aMsg);
		Util.getConsoleLogger().debug("Thread.currentThread().getName(): "+ Thread.currentThread().getName());
		
		String response = null;
		// 測試無限迴圈機制
		// String testIsFatalHandler = null;
		// testIsFatalHandler.toCharArray();

		// 3. 拿從RabbitMQ queue回來的資料(跳過測試資)
		JsonObject jsonIn = Util.getGJsonObject(aMsg);
		if (jsonIn.get("test") != null && jsonIn.get("test").getAsBoolean()) {
			return;
		}
		
		/** 共用資訊 **/
		Map<String, UserInfoForSimulation> map = UserCacheBean.getUserInfo();
		String userID = Util.getGString(jsonIn, "userID");
		String Event = Util.getGString(jsonIn, "Event");
		UserInfoForSimulation userInfoForWechat = UserCacheBean.getUserInfo().get(userID);
		
		Util.getConsoleLogger().info("Event: "+Event);
		Util.getFileLogger().info("Event: "+Event);
		Util.getFileLogger().info("userID: "+userID);
		
		// 4. 送回給當初寄過來的Client(使用WebSocket.conn.send(...))
		Util.getConsoleLogger()
				.debug("jsonIn.toString(): " + jsonIn.toString());
		if (userID == null) {
			Util.getConsoleLogger().debug("endpointID is null");
			return;
		}
		Util.getFileLogger().info(
				"OUT to endpoint - (" + Event + ")aMsg: " + aMsg);

		// 5. 向下皆入channel API

		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("/").getPath();
		// String path = "src/";
		Util.getFileLogger().info("path: " + path);
		
		if (Event != null){
			Event = Event.trim().toLowerCase();
		}


		
		switch (Event) {
		case "userjoin":
			// 登入後所收取的事件
//			Util.getConsoleLogger().info("get Event: userjoin");
//			Util.getFileLogger().info("get Event: userjoin");
			
			/** 寄送系統訊息 **/
			String[] userjoinChatRoomMsg = Util.getGString(jsonIn,
					"chatRoomMsg").split("<br>");
			Util.getConsoleLogger().info(
					"userjoinChatRoomMsg: " + userjoinChatRoomMsg);
			Util.getFileLogger().info(
					"userjoinChatRoomMsg: " + userjoinChatRoomMsg);
			
			for (String Msg : userjoinChatRoomMsg) {
//				String response = WeixinUtil.sendPushEvent(path, userID, Msg);
				response = ChannelUtil.sendToEndPoint();
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}
			
			/** 寄送關閉訊息 **/
			String CanLeaveMsg = "如要關閉請輸入關鍵字: \"關閉\"";
//			String userJoinResponse = WeixinUtil.sendPushEvent(path, userID,
//			CanLeaveMsg);
			response = ChannelUtil.sendToEndPoint();
			Util.getFileLogger().info("progress here");
			
			/** setinteraction **/
            // 更新interactionlog
			String ixnid = UUID.randomUUID().toString();
			String agentid = null;
			String status = "0";
			String typeid = "Inbound";
			String entitytypeid = "3"; // 以後要去DB抓對照表
			String subtypeid = "InBound New";
			String thecomment = null;
			String stoppedreason = null;
			String activitycode = null;
			String startdate = new java.util.Date().toString();
			String closefrom = null;
			String channel = null; // 沒在用了
			
			Interaction interaction = new Interaction();
			interaction.setType("setinteraction");
			interaction.setUserID(userID);
			interaction.setCallid(userInfoForWechat.getCallID());
//			interaction.setContactid(userInfoForWechat.getContactID()); // 待觀察
			interaction.setIxnid(ixnid);
			interaction.setAgentid(agentid);
			interaction.setStatus(status);
			interaction.setTypeid(typeid);
			interaction.setEntitytypeid(entitytypeid);
			interaction.setSubtypeid(subtypeid);
			interaction.setThecomment(thecomment);
			interaction.setStoppedreason(stoppedreason);
			interaction.setActivitycode(activitycode);
			interaction.setStartdate(startdate);
			interaction.setClosefrom(closefrom);
			interaction.setChannel(channel);
			
			String interactionJSON = Util.getGson().toJson(interaction, Interaction.class);
			Util.getFileLogger().info("interactionJSON: " +interactionJSON);
			AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, interactionJSON);
			Util.getConsoleLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			Util.getFileLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			
			/** 寄出找agent請求 **/
			FindAgentBean userjoinFindAgentBean = new FindAgentBean();
			userjoinFindAgentBean.setChannel("3"); // 注意: 之後要改掉
			userjoinFindAgentBean.setType("findAgent");
			userjoinFindAgentBean.setUserID(userID);
			userjoinFindAgentBean.setUserName("WeChatUser");
			String userjoinFindAgentBeanJSON = Util.getGson().toJson(userjoinFindAgentBean,FindAgentBean.class);
			Util.getConsoleLogger().info("userjoinFindAgentBeanJSON:" +userjoinFindAgentBeanJSON);
			Util.getFileLogger().info("userjoinFindAgentBeanJSON:" +userjoinFindAgentBeanJSON);
			AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, userjoinFindAgentBeanJSON);
			Util.getConsoleLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			Util.getFileLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			
			/** 改變user目前狀態(等待agent回應中(不論有沒有RING皆算是此狀態)) **/
			UserInfoForSimulation userInfo = map.get(userID);
//			userInfo.setUserStatusForWechat(UserStatusUtil.getService());
			
			/** Timeout機制(之後要移到最前面做timeout) **/
//			if(userInfo.getUserStatusForWechat().equals(UserStatusUtil.getEstablished())){
//				String RespVal = WeixinUtil.doCountDownThread(SystemParamUtil.getEstablishedTimeout(), userID, true);
//				Util.getFileLogger().info("RespVal: "+RespVal);
//				Util.getConsoleLogger().info("RespVal: "+RespVal);
//			}else if(userInfo.getUserStatusForWechat().equals(UserStatusUtil.getService())){
//				String RespVal = WeixinUtil.doCountDownThread(SystemParamUtil.getFindingAgentTimeout(), userID, true);
//				Util.getFileLogger().info("RespVal: "+RespVal);
//				Util.getConsoleLogger().info("RespVal: "+RespVal);
//			}
			
			break;
			
		case "exit":
			
			/** 告知使用者離開 **/
//			String exitResponse = WeixinUtil.sendPushEvent(path, userID,
//					SystemParamUtil.getExitWording());
			String exitResponse = ChannelUtil.sendToEndPoint();
//			Util.getConsoleLogger().info("exitResponse: " + exitResponse);
//			Util.getFileLogger().info("exitResponse: " + exitResponse);
			
			/** 清理map **/
			Util.getConsoleLogger().info(Event + "before removal - map:" +map);
			Util.getFileLogger().info(Event + "before removal - map:" +map);
			
			UserInfoForSimulation removeUserInfo = UserCacheBean.removeUserInfo(userID);
			Util.getFileLogger().info(Event + " - removeUserInfo: " + removeUserInfo);
			
			Util.getConsoleLogger().info(Event + "after removal - map:" +map);
			Util.getFileLogger().info(Event + "after removal - map:" +map);
			
			break;

		case "senduserdata":

			Util.getConsoleLogger().info("get Event: senduserdata");
			Util.getFileLogger().info("get Event: senduserdata");

			break;

		case "findagent":

			Util.getConsoleLogger().info("get Event: findAgent");
			Util.getFileLogger().info("get Event: findAgent");
			
			/** 通知使用者系統訊息 **/
			String[] findAgentChatRoomMsg = Util.getGString(jsonIn,
					"chatRoomMsg").split("<br>");
			Util.getConsoleLogger().info(
					"findAgentChatRoomMsg: " + findAgentChatRoomMsg);
			Util.getFileLogger().info(
					"findAgentChatRoomMsg: " + findAgentChatRoomMsg);

			for (String Msg : findAgentChatRoomMsg) {
//				String response = WeixinUtil.sendPushEvent(path, userID, Msg);
				response = ChannelUtil.sendToEndPoint();
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}
			
			/** 拿取當下對到的AgentID(目前沒再使用,之後可考慮去除) **/
			Map<String, UserInfoForSimulation> userInfoMap = UserCacheBean.getUserInfo();
			UserInfoForSimulation findAgentUserInfo = userInfoMap.get(userID);
			findAgentUserInfo.setServingAgentForSimulation(Util.getGString(jsonIn, "Agent"));
			Util.getConsoleLogger().info(
					"findAgentUserInfo.getServingAgent(): " + findAgentUserInfo.getServingAgentForSimulation());
			Util.getFileLogger().info("findAgentUserInfo.getServingAgent(): " + findAgentUserInfo.getServingAgentForSimulation());
			
			/** Timeout機制 **/
//			String RespVal = WeixinUtil.doCountDownThread(SystemParamUtil.getEstablishedTimeout(), userID, true);
//			Util.getFileLogger().info("RespVal: "+RespVal);
//			Util.getConsoleLogger().info("RespVal: "+RespVal);
			break;

		case "acceptevent":

			Util.getConsoleLogger().info("get Event: acceptevent");
			Util.getFileLogger().info("get Event: acceptevent");
			
			/** 通知使用者系統訊息 **/
			String[] acceptEventChatRoomMsg = Util.getGString(jsonIn,
					"chatRoomMsg").split("<br>");
			Util.getConsoleLogger().info(
					"acceptEventChatRoomMsg: " + acceptEventChatRoomMsg);
			Util.getFileLogger().info(
					"acceptEventChatRoomMsg: " + acceptEventChatRoomMsg);

			for (String Msg : acceptEventChatRoomMsg) {
//				String response = WeixinUtil.sendPushEvent(path, userID, Msg);
				response = ChannelUtil.sendToEndPoint();
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}
			
			/** 更新Cache狀態 **/
			String roomID = Util.getGString(jsonIn, "roomID");
			Util.getFileLogger().info("acceptEventChatRoomMsg roomID: " + roomID);
			userInfoForWechat.setRoomIDForSimulation(roomID);
//			userInfoForWechat.setUserStatusForWechat(UserStatusUtil.getEstablished());
			
			/** Timeout機制 **/
//			String RespValAcceptEvent = WeixinUtil.doCountDownThread(SystemParamUtil.getEstablishedTimeout(), userID, true);
//			Util.getFileLogger().info("RespValAcceptEvent: "+RespValAcceptEvent);
//			Util.getConsoleLogger().info("RespValAcceptEvent: "+RespValAcceptEvent);
			
			//AgentIDList_g.push(obj.from);
			//RoomOwnerAgentID_g = obj.from;

			break;

		case "rejectevent":

			Util.getConsoleLogger().info("get Event: RejectEvent");
			Util.getFileLogger().info("get Event: RejectEvent");
			
			/** 通知使用者系統訊息 **/
			String[] RejectEventChatRoomMsg = Util.getGString(jsonIn,
					"chatRoomMsg").split("<br>");
			Util.getConsoleLogger().info(
					"RejectEventChatRoomMsg: " + RejectEventChatRoomMsg);
			Util.getFileLogger().info(
					"RejectEventChatRoomMsg: " + RejectEventChatRoomMsg);

			for (String Msg : RejectEventChatRoomMsg) {
//				String response = WeixinUtil.sendPushEvent(path, userID, Msg);
				response = ChannelUtil.sendToEndPoint();
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}

			/** 再找一次agent **/
			FindAgentBean rejectEventFindAgentBean = new FindAgentBean();
			rejectEventFindAgentBean.setChannel("wechat");
			rejectEventFindAgentBean.setType("findAgent");
			rejectEventFindAgentBean.setUserID(userID);
			rejectEventFindAgentBean.setUserName("WeChatUser");
			String rejectEventFindAgentBeanJSON = Util.getGson().toJson(
					rejectEventFindAgentBean, FindAgentBean.class);
			Util.getConsoleLogger().info(
					"rejectEventFindAgentBeanJSON:"
							+ rejectEventFindAgentBeanJSON);
			Util.getFileLogger().info(
					"rejectEventFindAgentBeanJSON:"
							+ rejectEventFindAgentBeanJSON);
			AmqpUtil.getAmqpTemplate().convertAndSend(
					AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01,
					rejectEventFindAgentBeanJSON);
			Util.getConsoleLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			Util.getFileLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");

			break;

		case "ringtimeout":

			Util.getConsoleLogger().info("get Event: ringTimeout");
			Util.getFileLogger().info("get Event: ringTimeout");
			
			/** 通知使用者系統訊息 **/
			String[] ringTimeoutChatRoomMsg = Util.getGString(jsonIn,
					"chatRoomMsg").split("<br>");
			Util.getConsoleLogger().info(
					"ringTimeoutChatRoomMsg: " + ringTimeoutChatRoomMsg);
			Util.getFileLogger().info(
					"ringTimeoutChatRoomMsg: " + ringTimeoutChatRoomMsg);

			for (String Msg : ringTimeoutChatRoomMsg) {
//				String response = WeixinUtil.sendPushEvent(path, userID, Msg);
				response = ChannelUtil.sendToEndPoint();
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}

			/** 再找一次agent **/
			FindAgentBean ringTimeoutFindAgentBean = new FindAgentBean();
			ringTimeoutFindAgentBean.setChannel("wechat");
			ringTimeoutFindAgentBean.setType("findAgent");
			ringTimeoutFindAgentBean.setUserID(userID);
			ringTimeoutFindAgentBean.setUserName("WeChatUser");
			String ringTimeoutFindAgentBeanJSON = Util.getGson().toJson(
					ringTimeoutFindAgentBean, FindAgentBean.class);
			Util.getConsoleLogger().info(
					"ringTimeoutFindAgentBeanJSON:"
							+ ringTimeoutFindAgentBeanJSON);
			Util.getFileLogger().info(
					"ringTimeoutFindAgentBeanJSON:"
							+ ringTimeoutFindAgentBeanJSON);
			AmqpUtil.getAmqpTemplate().convertAndSend(
					AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01,
					ringTimeoutFindAgentBeanJSON);
			Util.getConsoleLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			Util.getFileLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");

			break;

		case "agentleft":

			Util.getConsoleLogger().info("get Event: agentLeft");
			Util.getFileLogger().info("get Event: agentLeft");

			/** 再找一次agent **/
			FindAgentBean agentLeftFindAgentBean = new FindAgentBean();
			agentLeftFindAgentBean.setChannel("wechat");
			agentLeftFindAgentBean.setType("findAgent");
			agentLeftFindAgentBean.setUserID(userID);
			agentLeftFindAgentBean.setUserName("WeChatUser");
			String agentLeftFindAgentBeanJSON = Util.getGson().toJson(
					agentLeftFindAgentBean, FindAgentBean.class);
			Util.getConsoleLogger().info(
					"agentLeftFindAgentBeanJSON:" + agentLeftFindAgentBeanJSON);
			Util.getFileLogger().info(
					"agentLeftFindAgentBeanJSON:" + agentLeftFindAgentBeanJSON);
			AmqpUtil.getAmqpTemplate().convertAndSend(
					AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01,
					agentLeftFindAgentBeanJSON);
			Util.getConsoleLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
			Util.getFileLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");

			break;

		case "messagetoroom":

			/** 若非自己傳的訊息,則進行更新 **/
			if(!Util.getGString(jsonIn, "id").equals(userID)){
				
				Util.getConsoleLogger().info("get Event: messagetoRoom");
				Util.getFileLogger().info("get Event: messagetoRoom");
				
				String messagetoRoomMsg = Util.getGString(jsonIn, "text");
//				String messagetoRoomResponse = WeixinUtil.sendPushEvent(path, userID,
//						messagetoRoomMsg);
				response = ChannelUtil.sendToEndPoint();
				
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}
			
			/** Timeout機制 **/
//			String messagetoroomRespVal = WeixinUtil.doCountDownThread(SystemParamUtil.getEstablishedTimeout(), userID, true);
//			Util.getFileLogger().info("messagetoroomRespVal: "+messagetoroomRespVal);
//			Util.getConsoleLogger().info("messagetoroomRespVal: "+messagetoroomRespVal);
			break;
			
		case "removeuserinroom":

			Util.getConsoleLogger().info("get Event: removeuserinroom");
			Util.getFileLogger().info("get Event: removeuerinroom");
			
			JsonObject chatRoomMsgJson = jsonIn.getAsJsonObject("chatRoomMsg");
			
			/** 查看房間是否有人離開房間訊息 **/
			String leftRoomMsg = Util.getGString(chatRoomMsgJson,"leftRoomMsg");
//			String leftRoomMsgResponse = WeixinUtil.sendPushEvent(path, userID,
//					leftRoomMsg);
			response = ChannelUtil.sendToEndPoint();
			
			Util.getConsoleLogger().info("response: " + response);
			Util.getFileLogger().info("response: " + response);
			
			/** 查看房間是否有房間關閉訊息 **/
			String closedRoomMsg = Util.getGString(chatRoomMsgJson,
					"closedRoomMsg");
			if(closedRoomMsg!=null){
//				String closedRoomMsgResponse = WeixinUtil.sendPushEvent(path, userID,
//						closedRoomMsg);
				response = ChannelUtil.sendToEndPoint();
				
				Util.getConsoleLogger().info("response: " + response);
				Util.getFileLogger().info("response: " + response);
			}
			
			
			/** 若房間沒人了,則送出"exit"事件,清理後端資料 **/
			String roomSizeStr = Util.getGString(jsonIn, "roomSize");
			Util.getFileLogger().info("roomSizeStr: " + roomSizeStr);
			if (roomSizeStr != null){
				int roomSize = Integer.parseInt(roomSizeStr);
				if (roomSize == 0){
					Util.getFileLogger().info("roomSize == 0");
					
					ExitBean exitBean = new ExitBean();
//					exitBean.setChannel(Util.WECHAT_ENTITYTYPEID);
					exitBean.setChannel(Util.getSystemParam().get("entityTypeID"));
					exitBean.setType("exit");
					exitBean.setUserID(userID);
//					exitBean.setWaittingAgent(false);
//					exitBean.setWaittingAgentID(waittingAgentID); // cache
					String exitBeanJSON = Util.getGson().toJson(exitBean,ExitBean.class);
					Util.getConsoleLogger().info("exitBeanJSON:" +exitBeanJSON);
					Util.getFileLogger().info("exitBeanJSON:" +exitBeanJSON);
					AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, exitBeanJSON);
					Util.getConsoleLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
					Util.getFileLogger().info("Send to CHANNEL_TO_BACKEND_QUEUE01");
				}
			}
			
			/** Timeout機制 **/
//			String removeuserinroomRespVal = WeixinUtil.doCountDownThread(SystemParamUtil.getFindingAgentTimeout(), userID, false);
//			Util.getFileLogger().info("removeuserinroomRespVal: "+removeuserinroomRespVal);
//			Util.getConsoleLogger().info("removeuserinroomRespVal: "+removeuserinroomRespVal);
			
			
			break;

		case "adduserinroom":

			Util.getConsoleLogger().info("get Event: addUserInRoom");
			Util.getFileLogger().info("get Event: addUserInRoom");

			break;

		case "responsethirdparty":

			Util.getConsoleLogger().info("get Event: responseThirdParty");
			Util.getFileLogger().info("get Event: responseThirdParty");

			break;

		case "unknown":

			Util.getConsoleLogger().info("get Event: unknown");
			Util.getFileLogger().info("get Event: unknown");

			break;
		}
    }
}
