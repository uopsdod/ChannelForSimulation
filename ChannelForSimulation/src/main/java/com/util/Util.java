package com.util;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
















import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.bean.VersionBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spring.SpringContextHolder;

public class Util {
	
	private static Gson gson;

	public Util(Gson aGson, VersionBean versionBean){
		Util.getFileLogger().info("Util() start");
		Util.gson = aGson;
		// 寫入version
		Util.getFileLogger().info("Util - " + versionBean.getProjectName() + " version: " + versionBean.getVersion());
		Util.getFileLogger().info("Util() end");
	}	
	
	public static String getSdfDateFormat(){
		return Attr.sdfDateFormat;
	}
	public static String getSdfTimeFormat(){
		return Attr.sdfTimeFormat;
	}
	public static String getSdfDateTimeFormat(){
		return Attr.sdfDateTimeFormat;
	}
	public static Map<String, String> getSystemParam() {
		return Attr.SystemParam;
	}
	public static void setSystemParam(Map<String, String> systemParam) {
		Attr.SystemParam = systemParam;
	}
	public static JsonObject getGJsonObject(String aMsg){
		JsonParser jsonParser = new JsonParser(); 
		JsonElement parse = jsonParser.parse(aMsg);
		JsonObject msgJson = null;
		if (parse.isJsonObject()){
			msgJson = parse.getAsJsonObject();
		}
		return msgJson;
	}
	public static JsonArray getGJsonArray(String aMsg){
		JsonParser jsonParser = new JsonParser(); 
		JsonElement parse = jsonParser.parse(aMsg);
		JsonArray msgJson = null;
		if (parse.isJsonArray()){
			msgJson = parse.getAsJsonArray();
		}
		return msgJson;
	}
	
	public static JsonArray getGJsonArray(JsonObject aObj, String aKey){
		Util.getFileLogger().info("getGJsonArray getGJsonArray");
		Util.getFileLogger().info("getGJsonArray input aObj: " + aObj);
		Util.getFileLogger().info("getGJsonArray input aKey: " + aKey);
		JsonElement jsonElmt = aObj.get(aKey);
		Util.getFileLogger().info("getGJsonArray jsonElmt: " + jsonElmt);
		if (jsonElmt == null){
			return null;
		}else if (jsonElmt.isJsonArray()){
			return jsonElmt.getAsJsonArray();
		}else{
			Util.getFileLogger().info("getGJsonArray(JsonObject aObj, String aKey) is not a JsonArray");
		}
		return null;
	}	
	
	public static String getGString(JsonObject aObj, String aKey){
		return (aObj.get(aKey) != null && !(aObj.get(aKey)instanceof JsonNull))?aObj.get(aKey).getAsString():null;
	}
	public static Logger getFileLogger(){
		return Attr.fileLogger;
	}
	public static Logger getConsoleLogger(){
		return Attr.consoleLogger;
	}
	public static Logger getStatusFileLogger(){
		return Attr.statusFileLogger;
	}
	public static Logger getPressureTestFileLogger(){
		return Attr.pressureTestFileLogger;
	}
	public static Gson getGson() {
		return gson;
	}
	public static void setGson(Gson gson) {
		Util.gson = gson;
	}

	private static class Attr {
		private static final String sdfDateFormat = "yyyy-MM-dd";
		private static final String sdfTimeFormat = "HH:mm:ss";
		private static final String sdfDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		private static Map<String,String> SystemParam = new HashMap<>();
		private static final Logger fileLogger = LogManager.getLogger("util.fileLogger");
		private static final Logger consoleLogger = LogManager.getLogger("util.consoleLogger");
		private static final Logger statusFileLogger = LogManager.getLogger("util.statusFileLogger");
		private static final Logger pressureTestFileLogger = LogManager.getLogger("util.pressureTestFileLogger");
		
	}
	
	//Base64 加密
    public static String getEncryptedAsBase64Encode(String value) throws UnsupportedEncodingException{
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] valueByte = value.getBytes("UTF-8");
        value = encoder.encodeToString(valueByte);
        value = "@"+value+"@";
        return value;
    }
    
    //Base64 解密
    public static String getEncryptedAsBase64Decode(String value) throws UnsupportedEncodingException{
        Matcher match = Pattern.compile("@+[\\x00-\\x7F]+@").matcher(value);
        while(match.find()){
            String matchers = match.group();
            matchers = matchers.replaceAll("@", "");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] valueByte = matchers.getBytes("UTF-8");
            String decodevalue = new String(decoder.decode(valueByte));
            value = value.replaceAll(matchers, decodevalue);
        }
        return value;
    }

	public static String getHostURLStr(String aHost){
		
		String protocol = Attr.SystemParam.get(aHost + "_protocol") + ":";
		String hostname = Attr.SystemParam.get(aHost + "_hostname");
		String port = Attr.SystemParam.get(aHost + "_port");
		
		return protocol + "//" + hostname + ":" + port;
	}
	
	public static String getProjectStr(String aHost){
		String projectName = Attr.SystemParam.get(aHost + "_project");
		return projectName;
	}
	
    public static String sendHttpPostRequest(String aUrlStr, Charset aCharset, List<AbstractMap.SimpleEntry<String, String>> aPostDataList){
//		Util.getFileLogger().info("sendHttpPostRequest start");
//		Util.getFileLogger().info("sendHttpPostRequest input - aUrlStr: " + aUrlStr);
//		Util.getFileLogger().info("sendHttpPostRequest input - aCharset: " + aCharset.name());
//		Util.getFileLogger().info("sendHttpPostRequest input - aPostDataList: " + aPostDataList);
		StringBuilder responseSB = new StringBuilder();
		
		/** 建立URL物件 **/
		URL url = null;
		try {
			url = new URL( aUrlStr );
//			Util.getFileLogger().info("url: " + url.toString());
		} catch (MalformedURLException e) {
			Util.getFileLogger().info("sendHttpPostRequest() Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getConsoleLogger().info("sendHttpPostRequest() Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("sendHttpPostRequest end");
//			e.printStackTrace();
			return responseSB.toString();
		}		
		
		/** 建立連線 **/
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + aCharset.name() + ";");
			connection.setRequestProperty("Content-Length", String.valueOf(aPostDataList.size()));
			connection.setConnectTimeout(20000); //set timeout to 20 seconds
			// Write data
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, aCharset.name()));;
			writer.write(com.util.Util.getQuery(aPostDataList));
			writer.flush();
			// 建立連線
			connection.connect();
			// Read response
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), aCharset.name()));
			String line;
			while ((line = br.readLine()) != null)
				responseSB.append(line);
			// Close streams
			writer.close();
			os.close();
			br.close();
		} catch (java.net.SocketTimeoutException e) {
			Util.getFileLogger().info("sendHttpPostRequest start input - SocketTimeoutException: " + Util.getExceptionMsg(e));
			Util.getConsoleLogger().info("sendHttpPostRequest start input - SocketTimeoutException: " + Util.getExceptionMsg(e));
//			e.printStackTrace();
		} catch (IOException e) {
			Util.getFileLogger().info("sendHttpPostRequest start input - IOException: " + Util.getExceptionMsg(e));
			Util.getConsoleLogger().info("sendHttpPostRequest start input - IOException: " + Util.getExceptionMsg(e));
//			e.printStackTrace();
		} catch (Exception e) {
			Util.getFileLogger().info("sendHttpPostRequest start input - Exception: " + Util.getExceptionMsg(e));
			Util.getConsoleLogger().info("sendHttpPostRequest start input - Exception: " + Util.getExceptionMsg(e));
//			e.printStackTrace();
		}
		
//		Util.getFileLogger().info("sendHttpPostRequest start input - responseSB.toString(): " + responseSB.toString());
//		Util.getFileLogger().info("sendHttpPostRequest end");
		return responseSB.toString();
		
    }
	
	public static String getQuery(List<AbstractMap.SimpleEntry<String,String>> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (AbstractMap.SimpleEntry<String,String> pair : params)
	    {
	    	/** 若為空值,則不再繼續(防呆) **/
	    	if (pair.getValue() == null || pair.getKey() == null)
	    		continue;
	    	
	    	
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }
//	    Util.getFileLogger().info("getQuery() - result.toString(): " + result.toString());
	    return result.toString();
	}
	
    
	public static String getExceptionMsg(Throwable e){
		String eMsg = null;
		try(StringWriter trace = new StringWriter();){
			e.printStackTrace(new PrintWriter(trace));
			eMsg = trace.toString();
		}catch(Exception exception){
			Util.getFileLogger().info("getExceptionMsg exception.getMessage(): " + exception.getMessage());
		}
	    return eMsg;
	}
	
}
