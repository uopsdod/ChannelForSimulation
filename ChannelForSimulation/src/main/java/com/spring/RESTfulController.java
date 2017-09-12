package com.spring;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agent.AcceptEventRunnable;
import com.agent.RspSenduserdataRunnable;
import com.agent.UpdateStatusNotReadyRunnable;
import com.agent.UpdateStatusReadyRunnable;
import com.client.ClientExitRunnable;
import com.client.ClientLoginRunnable;
import com.util.Util;

@RestController
@CrossOrigin
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
public class RESTfulController {
	public static final String TAG = "RESTfulController";
	
	@Autowired
	private ScheduledExecutorService scheduledExecutorService;
	
    @PostMapping("/triggerAction")
    public void triggerAction(@RequestParam(value="actionName", required=true) String actionName) {
    	Util.getConsoleLogger().info(TAG + "/triggerAction starts");
    	Util.getConsoleLogger().info(TAG + "/triggerAction input actionName: " + actionName);
    	
    	/** 全部轉為小寫 **/
    	actionName = actionName.toLowerCase();
    	
    	switch(actionName){
    	case "updatestatus_ready":
    		scheduledExecutorService.submit(new UpdateStatusReadyRunnable());
    		break;
    	case "updatestatus_notready":
    		scheduledExecutorService.submit(new UpdateStatusNotReadyRunnable());
    		break;
    	case "client_login":
    		scheduledExecutorService.submit(new ClientLoginRunnable());
    		break;
    	case "client_exit":
    		scheduledExecutorService.submit(new ClientExitRunnable());
    		break;
    	case "accept_event":
    		scheduledExecutorService.submit(new AcceptEventRunnable());
    		break;
    	case "rsp_senduserdata":
    		scheduledExecutorService.submit(new RspSenduserdataRunnable());
    		break;
		}
    	
    	Util.getConsoleLogger().info(TAG + "/triggerAction output ");
    	Util.getConsoleLogger().info(TAG + "/triggerAction ends");
    }
}