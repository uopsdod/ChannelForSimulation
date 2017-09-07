package com.bean;

public class EntryLogBean {
	private String type;
	private String userID;
	private String channel;
	private String contactid; // for client
	private String ipaddress; // for client
	private String browser; // for agent
	private String platfrom; // for agent
	private String language; // for agent
	private String enterkey; // for agent
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
	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getPlatfrom() {
		return platfrom;
	}
	public void setPlatfrom(String platfrom) {
		this.platfrom = platfrom;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEnterkey() {
		return enterkey;
	}
	public void setEnterkey(String enterkey) {
		this.enterkey = enterkey;
	}
	
	
	
	
}
