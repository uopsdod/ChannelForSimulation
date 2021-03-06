package com.controller;



import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.TestUtil;
import com.util.Util;

@Controller
public class BasicController {
	
	public BasicController(){
		System.out.println("BasicController() called");
	}

	@RequestMapping("/")
	public String welcome(Map<String, String> model) {
		model.put("message", "hello");
		return "welcome";
	}

	@RequestMapping("simulation")
	public String simulation(Map<String, String> model) {
		model.put("message", "hello");
		System.out.println("BasicController simulation server_hostname: " + Util.getSystemParam().get("server_hostname"));
		// server
		model.put("server_hostname", Util.getSystemParam().get("server_hostname"));
		model.put("server_port", Util.getSystemParam().get("server_port"));
		model.put("server_project", Util.getSystemParam().get("server_project"));
		// general
		model.put("tenantID", TestUtil.tenantID);
		model.put("typeID", TestUtil.typeID);
		// agent
		model.put("userID_agent", TestUtil.userID_agent);
		model.put("dialNO_agent", TestUtil.dialNO_agent);
		model.put("userName_agent", TestUtil.userName_agent);
		// client
		model.put("userID_client", TestUtil.userID_client);
		model.put("callID_client", TestUtil.callID_client);
		model.put("userName_client", TestUtil.userName_client);
		model.put("pilotID_client", TestUtil.pilotID_client);
		return "simulation";
	}	
	
	@RequestMapping("simulation_restful")
	public String simulation_restful(Map<String, String> model) {
		model.put("message", "hello");
		System.out.println("BasicController simulation server_hostname: " + Util.getSystemParam().get("server_hostname"));
		// server
		model.put("server_hostname", Util.getSystemParam().get("server_hostname"));
		model.put("server_port", Util.getSystemParam().get("server_port"));
		model.put("server_project", Util.getSystemParam().get("server_project"));
		// server
		model.put("RESTful_protocol", Util.getSystemParam().get("RESTful_protocol"));
		model.put("RESTful_hostname", Util.getSystemParam().get("RESTful_hostname"));
		model.put("RESTful_port", Util.getSystemParam().get("RESTful_port"));
		model.put("RESTful_project", Util.getSystemParam().get("RESTful_project"));
//		// general
//		model.put("tenantID", TestUtil.tenantID);
//		model.put("typeID", TestUtil.typeID);
//		// agent
//		model.put("userID_agent", TestUtil.userID_agent);
//		model.put("dialNO_agent", TestUtil.dialNO_agent);
//		model.put("userName_agent", TestUtil.userName_agent);
//		// client
//		model.put("userID_client", TestUtil.userID_client);
//		model.put("callID_client", TestUtil.callID_client);
//		model.put("userName_client", TestUtil.userName_client);
//		model.put("pilotID_client", TestUtil.pilotID_client);
		return "simulation_restful";
	}
	
	
	@RequestMapping("watchdog")
	public String watchdog(Map<String, String> model) {
		model.put("message", "hello");
		model.putAll(Util.getSystemParam());
		return "watchdog";
	}
	
	
}