package com.bean;

import com.google.gson.JsonArray;
 
public class AcceptEventBean {
	
	private String type = "acceptevent";
	private String userID;
	private String roomID;
	private JsonArray memberListToJoin;
	private String channel;
	private String entityTypeID;
	public String getEntityTypeID() {
		return entityTypeID;
	}
	public void setEntityTypeID(String entityTypeID) {
		this.entityTypeID = entityTypeID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public JsonArray getMemberListToJoin() {
		return memberListToJoin;
	}
	public void setMemberListToJoin(JsonArray memberListToJoin) {
		this.memberListToJoin = memberListToJoin;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	
	
}
