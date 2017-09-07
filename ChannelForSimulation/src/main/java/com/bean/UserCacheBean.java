package com.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserCacheBean {
	private static Map<String,UserInfoForSimulation> userInfo = Collections.synchronizedMap(new HashMap<>());

	public static Map<String, UserInfoForSimulation> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Map<String, UserInfoForSimulation> userInfo) {
		UserCacheBean.userInfo = userInfo;
	}
	
	public static void addUserInfo(String key, UserInfoForSimulation value) {
		UserCacheBean.userInfo.put(key, value);
	}
	
	public static UserInfoForSimulation removeUserInfo(String key) {
		return UserCacheBean.userInfo.remove(key);
	}
	
	public static void setUserInfoInit(Map<String, UserInfoForSimulation> userInfo) {
		userInfo = new HashMap<>();
		UserCacheBean.userInfo = userInfo;
	}
}
