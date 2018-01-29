package com.controller;



import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
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
		
		Map<String, String> versionParam = updateVersionParam();
		
		model.putAll(versionParam);
		
		return "watchdog";
	}
	
	private Map<String, String> updateVersionParam() {
		Map<String, String> versionParam = new HashMap<>();
		try {
			
			ClassPathResource classPathResource = new ClassPathResource("version.properties");
			System.out.println("classPathResource.getPath(): " +  classPathResource.getPath());
			File file = classPathResource.getFile();
			InputStream inputStream = classPathResource.getInputStream();
			System.out.println("file.getName(): " +  file.getName());
			
			PropertiesConfiguration conf = new PropertiesConfiguration(classPathResource.getPath());
			Util.getConsoleLogger().info("ContextRefreshedEvent conf: " + conf);
			
//			Map<String, String> versionParam = Util.getVersionParam();
			
			Object backend = conf.getProperty("backend");
			Util.getConsoleLogger().info("ContextRefreshedEvent backend: " + backend);
			versionParam.put("version_backend", backend.toString());
			Object web_channel = conf.getProperty("web_channel");
			Util.getConsoleLogger().info("ContextRefreshedEvent web_channel: " + web_channel);
			versionParam.put("version_web_channel", web_channel.toString());
			Object info360 = conf.getProperty("info360");
			Util.getConsoleLogger().info("ContextRefreshedEvent info360: " + info360);
			versionParam.put("version_info360", info360.toString());
			Object webapi = conf.getProperty("webapi");
			Util.getConsoleLogger().info("ContextRefreshedEvent webapi: " + webapi);
			versionParam.put("version_webapi", webapi.toString());
			Object simulation = conf.getProperty("simulation");
			Util.getConsoleLogger().info("ContextRefreshedEvent simulation: " + simulation);
			versionParam.put("version_simulation", simulation.toString());
			
			Util.getConsoleLogger().info("ContextRefreshedEvent versionParam: " + versionParam);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return versionParam;
	}
	
	
}