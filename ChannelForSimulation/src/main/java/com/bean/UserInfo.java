package com.bean;


import java.util.ArrayList; 
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.JsonObject;
import com.util.Util;
//此類別給WebSocketPool.userallconnections使用
public class UserInfo {
	private String userID;
	private String username;
	transient private List<String> userRoom = Collections.synchronizedList(new ArrayList<String>());
	private String ACType;
	
	/** 給當Client突然離開,告知對到的Agent此使用者已經離開,無法再建立通話了 **/
	private String agentIsWaitingForThisClientID;
	
	/** 給當Agent突然離開,告知等待的Agents and clients不用再等 **/
	private List<String> clientsAreWaittingForThisAgentList = new ArrayList<>();
	private List<String> agentsAreWaittingForThisAgentList = new ArrayList<>();
	
	/** interaction 1/2 **/
	private String userinteraction;
	/** interaction 2/2  最後寫入interaction時需要使用者登入時間 **/
	private java.util.Date startdate;
	
	/** RING 1/4 用於最後清理時,停掉RING **/
	transient private Timer heartbeatTimer;
	/** RING 2/4 判斷是否終止RING **/
	private AtomicBoolean stopRing = new AtomicBoolean(false); // 處理concurrent問題
	private AtomicBoolean timeout = new AtomicBoolean(false); // 處理concurrent問題
	/** RING 3/4  判斷ring是否在程式流程中結束,若為正常流程,則皆歸屬於ringtimeout **/	
	private boolean isRingEndExpected = false;
//	/** RING 4/4 判斷結束原因 **/	
//	private RingStopReasonEnum ringStopReasonEnum = null;
//	private RingStopReasonEnum ringConfStopReasonEnum = null;
//	private RingStopReasonEnum ringTransStopReasonEnum = null;
	
	/** 三方轉接的Timeout **/
	private AtomicBoolean stopConfRing = new AtomicBoolean(false);
	private AtomicBoolean timeoutConf = new AtomicBoolean(false);
	
	/** interaction寫入資料需要 **/
	private String roomOwner;

	/** Client找尋Agent請求確保順序 **/	
//	transient private Future<?> findAgentTaskResult;
//	transient private FindAgentCallable findAgentCallable;

	/** 確保已經由onCloseHelper清理的user,不再進行多餘的動作 **/	
	private boolean isClosing = false;
	
	/** 判斷是否要使用Agent更新的那個contactID **/	
	private boolean isContactIDupdatedByAgent = false;
	
	/** 用於皆換READY狀態時判斷是否可以切換,若不行就中斷切換狀態行為 **/
	private int maxCount = 0;
	
	/** 用於寄送至rabbitmq queue時判斷要寄給哪個channel的queue **/
	private String entityTypeID;
	
//	/** 狀態更新使用 - 存放status log dbid - "end"時寫入DB用 **/
////	private Map<StatusEnum, StatusInfo> statusInfoMap = Collections.synchronizedMap(new HashMap<>());
//	private Map<StatusEnum, StatusInfo> statusInfoMap = Collections.synchronizedMap(new MyStatusInfoMap());
//	
//	/** (待評估) 當下狀態 **/
////	private StatusInfo currStatusInfo;
//	private StatusEnum agentCurrStatus;
//	
//	/** 更新Client狀態 **/
//	private ClientStatusEnum clientCurrStatus;
	
	/** 紀錄與機器人通話內容 **/
	private String contentWithRobot; 
	
	/** callID - Entyrlog()產生的此次互動編號 **/
	private String callID;
	
	/** 多公司 - tenantID **/
	private String tenantID;
	
	/** 暫存住client userdata **/
	private JsonObject userdata;
	
	public String getContentWithRobot() {
		return contentWithRobot;
	}
	public void setContentWithRobot(String contentWithRobot) {
		this.contentWithRobot = contentWithRobot;
	}
	public List<String> getUserRoom() {
		return userRoom;
	}
	public void setUserRoom(List<String> userroom) {
		this.userRoom = userroom;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserinteraction() {
		return userinteraction;
	}
	/**
	 * 
	 * @param userinteraction
	 */
	public void setUserinteraction(String userinteraction) {
		Util.getFileLogger().info("setUserinteraction() start");
		Util.getFileLogger().info("setUserinteraction() thread: " + Thread.currentThread().getName());
		Util.getFileLogger().info("setUserinteraction() input - userinteraction: " + userinteraction);
		this.userinteraction = userinteraction;
		Util.getFileLogger().info("setUserinteraction() start");
	}
	public Timer getHeartbeatTimer() {
		return heartbeatTimer;
	}
	public void setHeartbeatTimer(Timer heartbeatTimer) {
		this.heartbeatTimer = heartbeatTimer;
	}
	public String getACType() {
		return ACType;
	}
	public void setACType(String aACType) {
		ACType = aACType;
	}
	public java.util.Date getStartdate() {
		return startdate;
	}
	public void setStartdate(java.util.Date startdate) {
		this.startdate = startdate;
	}
	public boolean isStopRing() {
		return stopRing.get();
	}
	/**
	 * 
	 * @param stopRing
	 */
	public void setStopRing(boolean stopRing) {
		Util.getFileLogger().info("setStopRing() thread: " + Thread.currentThread().getName());
		Util.getFileLogger().info("setStopRing() start");
		Util.getFileLogger().info("setStopRing() input - stopRing: " + stopRing);
		this.stopRing.set(stopRing);
		Util.getFileLogger().info("setStopRing() end");
	}
	public boolean getTimeout() {
		return timeout.get();
	}
	public void setTimeout(boolean timeout) {
		this.timeout.set(timeout);;
	}
//	public Map<StatusEnum, StatusInfo> getStatusInfoMap() {
//		return statusInfoMap;
//	}
//	public void setStatusInfoMap(Map<StatusEnum, StatusInfo> statusInfoMap) {
//		this.statusInfoMap = statusInfoMap;
//	}
	public String getRoomOwner() {
		return roomOwner;
	}
	public void setRoomOwner(String roomOwner) {
		this.roomOwner = roomOwner;
	}
//	public Future<?> getFindAgentTaskResult() {
//		return findAgentTaskResult;
//	}
//	public void setFindAgentTaskResult(Future<?> findAgentTaskResult) {
//		this.findAgentTaskResult = findAgentTaskResult;
//	}
//	public FindAgentCallable getFindAgentCallable() {
//		return findAgentCallable;
//	}
//	public void setFindAgentCallable(FindAgentCallable findAgentCallable) {
//		this.findAgentCallable = findAgentCallable;
//	}
	
//	public boolean isReady(){
//		if (this.statusInfoMap.get(StatusEnum.READY) != null){
//			return true;
//		}
//		return false;
//	}
	
//	public boolean isNotReady(){
//		if (this.statusInfoMap.get(StatusEnum.NOTREADY) != null){
//			return true;
//		}
//		return false;
//	}
	public boolean isClosing() {
		return isClosing;
	}
	
	public void setClosing(boolean isClosing) {
		this.isClosing = isClosing;
	}
	public boolean isContactIDupdatedByAgent() {
		return isContactIDupdatedByAgent;
	}
	public void setContactIDupdatedByAgent(boolean isContactIDupdatedByAgent) {
		this.isContactIDupdatedByAgent = isContactIDupdatedByAgent;
	}
			
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public boolean isRingEndExpected() {
		return isRingEndExpected;
	}
	public void setRingEndExpected(boolean isRingEndNormally) {
		this.isRingEndExpected = isRingEndNormally;
	}
	
	public boolean isStopConfRing(){
		return this.stopConfRing.get();
	}
	
	public void setStopConfRing(boolean aStopConfRing){
		this.stopConfRing.set(aStopConfRing);
	}

	
	public boolean isTimeoutConf(){
		return this.timeoutConf.get();
	}
	
	public void setTimeoutConf(boolean aTimeoutConf){
		this.timeoutConf.set(aTimeoutConf);
	}
	public String getEntityTypeID() {
		return entityTypeID;
	}
	public void setEntityTypeID(String channel) {
		this.entityTypeID = channel;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public List<String> getClientsAreWaittingForThisAgentList() {
		return clientsAreWaittingForThisAgentList;
	}
	public void setClientsAreWaittingForThisAgentList(
			List<String> agentIsWaittingForClientIDList) {
		this.clientsAreWaittingForThisAgentList = agentIsWaittingForClientIDList;
	}
	public AtomicBoolean getStopRing() {
		return stopRing;
	}
	public void setStopRing(AtomicBoolean stopRing) {
		this.stopRing = stopRing;
	}
	public AtomicBoolean getStopConfRing() {
		return stopConfRing;
	}
	public void setStopConfRing(AtomicBoolean stopConfRing) {
		this.stopConfRing = stopConfRing;
	}
	public AtomicBoolean getTimeoutConf() {
		return timeoutConf;
	}
	public void setTimeoutConf(AtomicBoolean timeoutConf) {
		this.timeoutConf = timeoutConf;
	}
	public void setTimeout(AtomicBoolean timeout) {
		this.timeout = timeout;
	}
	public List<String> getAgentsAreWaittingForThisAgentList() {
		return agentsAreWaittingForThisAgentList;
	}
	public void setAgentsAreWaittingForThisAgentList(
			List<String> agentsAreWaittingForThisAgentList) {
		this.agentsAreWaittingForThisAgentList = agentsAreWaittingForThisAgentList;
	}
	public String getAgentIsWaitingForThisClientID() {
		return agentIsWaitingForThisClientID;
	}
	public void setAgentIsWaitingForThisClientID(
			String agentIsWaitingForThisClientID) {
		this.agentIsWaitingForThisClientID = agentIsWaitingForThisClientID;
	}
//	public ClientStatusEnum getClientCurrStatus() {
//		return clientCurrStatus;
//	}
//	public void setClientCurrStatus(ClientStatusEnum clientStatusEnum) {
//		this.clientCurrStatus = clientStatusEnum;
//	}
//	
//	
//	public StatusEnum getAgentCurrStatus() {
//		return agentCurrStatus;
//	}
//	public void setAgentCurrStatus(StatusEnum agentCurrStatus) {
//		this.agentCurrStatus = agentCurrStatus;
//	}
//	
//	public RingStopReasonEnum getRingStopReasonEnum() {
//		return ringStopReasonEnum;
//	}
//	public void setRingStopReasonEnum(RingStopReasonEnum ringStopReasonEnum) {
//		this.ringStopReasonEnum = ringStopReasonEnum;
//	}
//	public RingStopReasonEnum getRingConfStopReasonEnum() {
//		return ringConfStopReasonEnum;
//	}
//	public void setRingConfStopReasonEnum(RingStopReasonEnum ringConfStopReasonEnum) {
//		this.ringConfStopReasonEnum = ringConfStopReasonEnum;
//	}
//	public RingStopReasonEnum getRingTransStopReasonEnum() {
//		return ringTransStopReasonEnum;
//	}
//	public void setRingTransStopReasonEnum(
//			RingStopReasonEnum ringTransStopReasonEnum) {
//		this.ringTransStopReasonEnum = ringTransStopReasonEnum;
//	}
	
	public String getCallID() {
		return callID;
	}
	public void setCallID(String callID) {
		this.callID = callID;
	}
	
	public String getTenantID() {
		return tenantID;
	}
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}
	public JsonObject getUserdata() {
		return userdata;
	}
	public void setUserdata(JsonObject userdata) {
		this.userdata = userdata;
	}
	
	
	
	
//	/**
//	 * 更新值機時間
//	 */
//	public void updatAagentCurrStatus(){
//		Set<StatusEnum> keySet = this.statusInfoMap.keySet();
//		if (keySet.contains(StatusEnum.AFTERCALLWORK)){
//			this.agentCurrStatus = StatusEnum.AFTERCALLWORK;
//		}else if(keySet.contains(StatusEnum.READY)){
//			this.agentCurrStatus = StatusEnum.READY;
//		}else if(keySet.contains(StatusEnum.NOTREADY)){
//			this.agentCurrStatus = StatusEnum.NOTREADY;
//		}
//	}
	
	
//	/**
//	 * 此目的為讓map不論在put,remove時，都去更新當下值機狀態
//	 * @author sam
//	 *
//	 */
//	class MyStatusInfoMap extends HashMap<StatusEnum, StatusInfo>{
//		private static final long serialVersionUID = 609761010483630405L;
//
//		@Override
//		public StatusInfo put(StatusEnum aStatusEnum, StatusInfo aStatusInfo){
//			Util.getFileLogger().info("MyStatusInfoMap::put() start");
//			Util.getFileLogger().info("MyStatusInfoMap::put() input - aStatusEnum: " + aStatusEnum);
//			Util.getFileLogger().info("MyStatusInfoMap::put() input - aStatusInfo: " + aStatusInfo);
//			StatusInfo preStatusInfo = super.put(aStatusEnum, aStatusInfo);
//			/** 更新當下值機狀態 **/
//			UserInfo.this.updatAagentCurrStatus();
//			Util.getFileLogger().info("MyStatusInfoMap::put() end");
//			return preStatusInfo;
//		}
//
//		@Override
//		public StatusInfo remove(Object key) {
//			Util.getFileLogger().info("MyStatusInfoMap::remove() start");
//			Util.getFileLogger().info("MyStatusInfoMap::remove() input - key: " + key);
//			StatusInfo preStatusInfo = super.remove(key);
//			/** 更新當下值機狀態 **/
//			UserInfo.this.updatAagentCurrStatus();
//			Util.getFileLogger().info("MyStatusInfoMap::remove() end");
//			return preStatusInfo;
//		}
//	}
	
	
}


