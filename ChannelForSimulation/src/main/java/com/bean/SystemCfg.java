package com.bean;


import org.apache.commons.lang3.builder.ToStringBuilder;

public class SystemCfg {
	private Integer DBID;
	private String APP_Name;
	private String Parameter;
	private String Name;
	private String Value;
	private java.sql.Date UpdateTime;
	private String Description;
	private String TenantID;
	private String entityTypeID;
	private String siteID;
	
	public String getTenantID() {
		return TenantID;
	}
	public void setTenantID(String tenantID) {
		TenantID = tenantID;
	}
	public Integer getDBID() {
		return DBID;
	}
	public void setDBID(Integer dBID) {
		DBID = dBID;
	}
	public String getAPP_Name() {
		return APP_Name;
	}
	public void setAPP_Name(String aPP_Name) {
		APP_Name = aPP_Name;
	}
	public String getParameter() {
		return Parameter;
	}
	public void setParameter(String parameter) {
		Parameter = parameter;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public java.sql.Date getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(java.sql.Date updateTime) {
		UpdateTime = updateTime;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getEntityTypeID() {
		return entityTypeID;
	}
	public void setEntityTypeID(String entityTypeID) {
		this.entityTypeID = entityTypeID;
	}
	public String getSiteID() {
		return siteID;
	}
	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
