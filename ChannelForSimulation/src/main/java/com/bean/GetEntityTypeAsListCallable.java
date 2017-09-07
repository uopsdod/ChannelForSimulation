package com.bean;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.util.EntityTypeEnum;
import com.util.Util;

public class GetEntityTypeAsListCallable implements Callable{

	@Override
	public Object call() throws Exception {
		String result = this.getEntityTypeAsList();
		JsonParser jsonParser = new JsonParser(); 
		JsonElement jsonElement = jsonParser.parse(result);
		JsonArray msgJsonAry = null;
		if (jsonElement.isJsonArray()){
			msgJsonAry = jsonElement.getAsJsonArray();
		}
		for (JsonElement jsonElment : msgJsonAry) {
			if (jsonElment.isJsonObject()){
				JsonObject jsonObj = jsonElment.getAsJsonObject();
				String dbid = Util.getGString(jsonObj, "dbid");
				String entityTypeID = Util.getGString(jsonObj, "entityTypeID");
				String entityTypeName = Util.getGString(jsonObj, "entityTypeName");
				String entityDescription = Util.getGString(jsonObj, "entityDescription");
				
				EntityTypeEnum.updateEntityTypeEnum(entityTypeName, entityTypeID, entityDescription);
			}
		}
		return null;
	}
	
	public static String getEntityTypeAsList(){
    	Util.getFileLogger().info("getEntityTypeAsList starts");	
    	
		/** 建立資料 **/
		List<AbstractMap.SimpleEntry<String, String>> params = new ArrayList<>();
		/** 建立URL字串 **/
		String hostURL = Util.getHostURLStr("RESTful");
		String projectName = Util.getProjectStr("RESTful");
		String urlStr = hostURL + projectName + "/RESTful/getEntityTypeAsList";
		Util.getConsoleLogger().info("getEntityTypeAsList urlStr: " + urlStr);
		Util.getFileLogger().info("getEntityTypeAsList urlStr: " + urlStr);
////		String urlStr = TestUtil.url_8080 + "/getEntityTypeAsList";
//		String urlStr = "http://ws.crm.com.tw:8080/Info360WebAPI/RESTful" + "/getEntityTypeAsList";
		Util.getConsoleLogger().info("getEntityTypeAsList: " + urlStr);
		/** 寄出請求 **/
		String result = Util.sendHttpPostRequest(urlStr, StandardCharsets.UTF_8, params);		
		Util.getConsoleLogger().info("getEntityTypeAsList result: " + result);	
		Util.getFileLogger().info("getEntityTypeAsList result: " + result);	
		
		Util.getFileLogger().info("getEntityTypeAsList ends");
        return result;
        
    }
}