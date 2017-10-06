package com.filter;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.bean.GetEntityTypeAsListCallable;
import com.test.TableDateTransfer;
import com.util.Util;

public class ApplicationListenerBean implements ApplicationListener {
	
	@Autowired
	private ScheduledExecutorService scheduledExecutorService;
	
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
        	Util.getConsoleLogger().info("ContextRefreshedEvent start ###################");
        	Util.getFileLogger().info("ContextRefreshedEvent start ###################");
     		 
    		/** 更新EntityType Enum **/
//    		scheduledExecutorService.submit(new GetEntityTypeAsListCallable());
        	
    		scheduledExecutorService.submit(new TableDateTransfer());
    		
        	Util.getConsoleLogger().info("ContextRefreshedEvent end ###################");
        	Util.getFileLogger().info("ContextRefreshedEvent end ###################");
        }
        
        if (event instanceof ContextClosedEvent) {
        	Util.getConsoleLogger().info("ContextClosedEvent start ###################");
        	Util.getFileLogger().info("ContextClosedEvent start ###################");

        	// do nothing
    		Util.getConsoleLogger().info("contextDestroyed() called");
    		Util.getFileLogger().info("contextDestroyed() called");
    		
    		/** 終止SendDashboardDataThread **/
//    		ScheduledExecutorService executor = SpringContextHolder.getApplicationContext().getBean(ScheduledExecutorService.class);
//    		ScheduledExecutorService executor = Util.getScheduledExecutorService();
    		/** close ExecutorService **/
    		try {
    			Util.getFileLogger().info("attempt to shutdown executor");
    			scheduledExecutorService.shutdown();
    			scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
    		}
    		catch (InterruptedException e) {
    			Util.getFileLogger().error("tasks interrupted");
    		}
    		finally {
    		    if (!scheduledExecutorService.isTerminated()) {
    		    	Util.getFileLogger().info("cancel non-finished tasks");
    		    }
    		    scheduledExecutorService.shutdownNow();
    		    Util.getFileLogger().info("shutdown finished");
    		}		
    		
    		
        	Util.getConsoleLogger().info("ContextClosedEvent end ###################");
        	Util.getFileLogger().info("ContextClosedEvent end ###################");
        }
        
    }
    
}