package com.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bean.LoginBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.util.AmqpUtil;
import com.util.EntityTypeEnum;
import com.util.TestUtil;
import com.util.Util;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ClientLoginRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
		
		/** senduserdata **/
//		url : RESTful_protocol+"//"+RESTful_hostname+":"+RESTful_port+"/"+RESTful_project+"/RESTful/searchUserdataNew",		
		/** 建立資料 **/
		List<AbstractMap.SimpleEntry<String, String>> params = new ArrayList<>();
		params.add(new AbstractMap.SimpleEntry<String, String>("searchVal", TestUtil.userID_client));
		params.add(new AbstractMap.SimpleEntry<String, String>("typeID", TestUtil.typeID));
		params.add(new AbstractMap.SimpleEntry<String, String>("tenantID", TestUtil.tenantID));
		/** 建立URL字串 **/
		String hostURL = Util.getHostURLStr("RESTful");
		String projectName = Util.getProjectStr("RESTful");
		String urlStr = hostURL + "/" + projectName + "/RESTful/searchUserdataNew";
		Util.getConsoleLogger().info("searchUserdataNew urlStr: " + urlStr);
		Util.getFileLogger().info("searchUserdataNew urlStr: " + urlStr);
////			String urlStr = TestUtil.url_8080 + "/getEntityTypeAsList";
//			String urlStr = "http://ws.crm.com.tw:8080/Info360WebAPI/RESTful" + "/getEntityTypeAsList";
		Util.getConsoleLogger().info("searchUserdataNew: " + urlStr);
		/** 寄出請求 **/
		String senduserResult = Util.sendHttpPostRequest(urlStr, StandardCharsets.UTF_8, params);		
		Util.getConsoleLogger().info("searchUserdataNew result: " + senduserResult);	
		Util.getFileLogger().info("searchUserdataNew result: " + senduserResult);
		
		/** 保留值 **/
		TestUtil.userdata = senduserResult;
		
		JsonObject resultJsonObj = Util.getGJsonObject(senduserResult);
		JsonArray customerDataJsonAry = Util.getGJsonArray(resultJsonObj, "CustomerData");
		JsonObject customerData = customerDataJsonAry.get(0).getAsJsonObject();
		String contactID = Util.getGString(customerData, "contactid"); 
		Util.getConsoleLogger().info("searchUserdataNew - contactID: " + contactID);
		Util.getFileLogger().info("searchUserdataNew - contactID: " + contactID);
		
//		
//		
//		
//		userdata_g = data;		
////			var obj = jQuery.parseJSON(data.data);		
//		contactID_g = data.CustomerData[0].contactid;				
		
			
		/** Login **/
		LoginBean loginBean = new LoginBean();
		loginBean.setACtype("Client");
//		loginBean.setChannel("wechat");
//		loginBean.setChannel(Util.WECHAT_ENTITYTYPEID); // 後端已經改成抓取entityTypeID, 之後各channel端也要去抓取DB
		loginBean.setMaxCount("0");
		loginBean.setType("login");
		loginBean.setUserID(TestUtil.userID_client);
		loginBean.setUserName(TestUtil.userName_client);
		loginBean.setUserdata(Util.getGJsonObject(TestUtil.userdata));
		
		loginBean.setCallID(TestUtil.callID_client);
		loginBean.setTenantID(TestUtil.tenantID);
		loginBean.setEntityTypeID(EntityTypeEnum.VOICE.getEntityTypeID());
		loginBean.setClientPilotID("1");// 注意: pilotID
		
		String loginBeanJSON = Util.getGson().toJson(loginBean,LoginBean.class);
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, loginBeanJSON);
//		AmqpUtil.getAmqpTemplate().convertSendAndReceive(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, loginBeanJSON);
	}
}
