package com.util;

public enum EntityTypeEnum {
	VOICE()
	,WEBCHAT()
	,WECHAT()
	,LINE()
	,FB()
	,EMAIL()
	,FAX()
	,ROBOT()
	;
	
	private String entityTypeID; // ex. 3
	private String description; // ex. 準備就緒
	public String getEntityTypeID() {
		return entityTypeID;
	}
	public void setEntityTypeID(String dbid) {
		this.entityTypeID = dbid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * the dbid for each status will be obtained from DB when the application starts
	 * @param aDbid
	 * @return
	 */
	public static EntityTypeEnum getEntityTypeEnumByDbid(String aDbid){
		if (VOICE.getEntityTypeID().equals(aDbid)){
			return VOICE;
		}else if (WEBCHAT.getEntityTypeID().equals(aDbid)){
			return WEBCHAT;
		}else if (WECHAT.getEntityTypeID().equals(aDbid)){
			return WECHAT;
		}else if (LINE.getEntityTypeID().equals(aDbid)){
			return LINE;
		}else if (FB.getEntityTypeID().equals(aDbid)){
			return FB;
		}else if (EMAIL.getEntityTypeID().equals(aDbid)){
			return EMAIL;
		}else if (FAX.getEntityTypeID().equals(aDbid)){
			return FAX;
		}else if (ROBOT.getEntityTypeID().equals(aDbid)){
			return ROBOT;
		}
		Util.getConsoleLogger().debug("StatusEnmu - getStatusEnumByDbid: " + " no match");
		return null;
	}
	
	/**
	 * insert the dbid of each entityStatus from DB into our EntityTypeEnum Bean
	 * @param aEntityTypeName
	 * @param aDbid
	 * @param aDescription
	 */
	public static void updateEntityTypeEnum(String aEntityTypeName, String aEntityTypeID,
			String aDescription) {
		Util.getConsoleLogger().info("updateEntityTypeEnum() starts");
		Util.getConsoleLogger().info("updateEntityTypeEnum() input aEntityTypeName: " + aEntityTypeName);
		Util.getConsoleLogger().info("updateEntityTypeEnum() input aEntityTypeID: " + aEntityTypeID);
		Util.getConsoleLogger().info("updateEntityTypeEnum() input aDescription: " + aDescription);
		
		aEntityTypeName = aEntityTypeName.toUpperCase();
		EntityTypeEnum currEntityTypeEnum = Enum.valueOf(EntityTypeEnum.class, aEntityTypeName);
		currEntityTypeEnum.setEntityTypeID(aEntityTypeID);
		currEntityTypeEnum.setDescription(aDescription);
		
//		Util.getConsoleLogger().info("updateEntityTypeEnum() currEntityTypeEnum.getEntityTypeID(): " + currEntityTypeEnum.getEntityTypeID());
		
	}	
	
}

