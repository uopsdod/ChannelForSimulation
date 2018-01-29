package com.bean;

import com.google.gson.JsonArray;

public class ExitBean {
	private String type;
	private String userID;
	private String entityTypeID;
	private String channel;
	private boolean waittingAgent; // for client
	private String waittingAgentID; // for client
	private JsonArray waittingClientIDList; // for agent
	private JsonArray waittingAgentIDList; // for agent
	
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getWaittingAgentID() {
		return waittingAgentID;
	}
	public void setWaittingAgentID(String waittingAgentID) {
		this.waittingAgentID = waittingAgentID;
	}
	public boolean isWaittingAgent() { 
		return waittingAgent;
	}
	public void setWaittingAgent(boolean waittingAgent) {
		this.waittingAgent = waittingAgent;
	}
	public JsonArray getWaittingClientIDList() {
		return waittingClientIDList;
	}
	public void setWaittingClientIDList(JsonArray waittingClientIDList) {
		this.waittingClientIDList = waittingClientIDList;
	}
	public JsonArray getWaittingAgentIDList() {
		return waittingAgentIDList;
	}
	public void setWaittingAgentIDList(JsonArray waittingAgentIDList) {
		this.waittingAgentIDList = waittingAgentIDList;
	}
	
	
	
}
