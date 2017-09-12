package com.spring;



import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.put("server_hostname", Util.getSystemParam().get("server_hostname"));
		model.put("server_port", Util.getSystemParam().get("server_port"));
		model.put("server_project", Util.getSystemParam().get("server_project"));
		return "simulation";
	}	
	
}