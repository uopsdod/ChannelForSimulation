package com.bean;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.JsonObject;

public class LoginBean {
	// in
	private String type;
	private String userID; // 更名(注意)
	private String UserName;
	private String maxCount;
	private String ACtype;
//	private String channel;
	private String previousAgentUserID; // 重複登入使用
	private String content; // 保留機器人通話紀錄使用
	private String callID; // Entrylog()所產生的此次互動編碼
	private String tenantID; // 各公司tenantID
	private String siteID; // 各頁面siteID
	private JsonObject userdata;
	
	private String entityTypeID; // 從channelForWeb那放入值
	
	private String remoteIP; // 從channelForWeb那放入值
	
	private List<Cfg_Pilot_Agent> pilotIDList;
	private String clientPilotID;
	
	private String dialNo;
	private String dialNoPwd;
	
	public String getDialNoPwd() {
		return dialNoPwd;
	}
	public void setDialNoPwd(String dialNoPwd) {
		this.dialNoPwd = dialNoPwd;
	}
	public String getDialNo() {
		return dialNo;
	}
	public void setDialNo(String dialNo) {
		this.dialNo = dialNo;
	}
	public String getClientPilotID() {
		return clientPilotID;
	}
	public void setClientPilotID(String clientPilotID) {
		this.clientPilotID = clientPilotID;
	}
//	/** softphone account & pwd **/
//	private String softphoneAccount;
//	private String softphonePwd;

	// out
	private String Event;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}
	public String getACtype() {
		return ACtype;
	}
	public void setACtype(String aCtype) {
		ACtype = aCtype;
	}
//	public String getChannel() {
//		return channel;
//	}
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPreviousAgentUserID() {
		return previousAgentUserID;
	}
	public void setPreviousAgentUserID(String previousAgentUserID) {
		this.previousAgentUserID = previousAgentUserID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCallID() {
		return callID;
	}
	public void setCallID(String callID) {
		this.callID = callID;
	}
	public String getEntityTypeID() {
		return entityTypeID;
	}
	public void setEntityTypeID(String entityTypeID) {
		this.entityTypeID = entityTypeID;
	}
	public String getTenantID() {
		return tenantID;
	}
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
//	public String getSoftphoneAccount() {
//		return softphoneAccount;
//	}
//	public void setSoftphoneAccount(String softphoneAccount) {
//		this.softphoneAccount = softphoneAccount;
//	}
//	public String getSoftphonePwd() {
//		return softphonePwd;
//	}
//	public void setSoftphonePwd(String softphonePwd) {
//		this.softphonePwd = softphonePwd;
//	}
	public JsonObject getUserdata() {
		return userdata;
	}
	public void setUserdata(JsonObject userdata) {
		this.userdata = userdata;
	}
	public List<Cfg_Pilot_Agent> getPilotIDList() {
		return pilotIDList;
	}
	public void setPilotIDList(List<Cfg_Pilot_Agent> pilotList) {
		this.pilotIDList = pilotList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
