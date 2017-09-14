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
import com.agent.LeaveRoomRunnable;
import com.agent.RspSenduserdataRunnable;
import com.agent.UpdateStatusNotReadyRunnable;
import com.agent.UpdateStatusReadyRunnable;
import com.client.ClientExitRunnable;
import com.client.ClientLoginRunnable;
import com.util.TestUtil;
import com.util.Util;

@RestController
@CrossOrigin
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
public class RESTfulController {
	public static final String TAG = "RESTfulController";
	
	@Autowired
	private ScheduledExecutorService scheduledExecutorService;
	
    @PostMapping("/triggerAction")
    public void triggerAction(@RequestParam(value="actionName", required=true) String actionName
					    		,@RequestParam(value="tenantID") String tenantID
					    		,@RequestParam(value="typeID") String typeID
    							,@RequestParam(value="userID_agent") String userID_agent
    							,@RequestParam(value="dialNO_agent") String dialNO_agent
    							,@RequestParam(value="userName_agent") String userName_agent
    							,@RequestParam(value="userID_client") String userID_client
    							,@RequestParam(value="callID_client") String callID_client
    							,@RequestParam(value="userName_client") String userName_client
    							) {
    	Util.getConsoleLogger().info(TAG + "/triggerAction starts");
    	Util.getConsoleLogger().info(TAG + "/triggerAction input tenantID: " + tenantID);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input typeID: " + typeID);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input actionName: " + actionName);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input userID_agent: " + userID_agent);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input dialNO_agent: " + dialNO_agent);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input userName_agent: " + userName_agent);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input userID_client: " + userID_client);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input callID_client: " + callID_client);
    	Util.getConsoleLogger().info(TAG + "/triggerAction input userName_client: " + userName_client);
    	
    	/** 更新使用者參數 **/
    	TestUtil.userID_agent = userID_agent;
    	TestUtil.dialNO_agent = dialNO_agent;
    	TestUtil.userName_agent = userName_agent;
    	TestUtil.tenantID = tenantID;
    	TestUtil.typeID = typeID;
    	
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
    	case "agent_leaveroom":
    		scheduledExecutorService.submit(new LeaveRoomRunnable());
    		break;
		}
    	
    	Util.getConsoleLogger().info(TAG + "/triggerAction output ");
    	Util.getConsoleLogger().info(TAG + "/triggerAction ends");
    }
}