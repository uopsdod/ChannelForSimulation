package com.filter;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;

import com.bean.GetEntityTypeAsListCallable;
import com.test.TableDateTransfer;
import com.util.Util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ApplicationListenerBean implements ApplicationListener { 
	
	@Autowired
	private ScheduledExecutorService scheduledExecutorService;
	
//    @Autowired
//    private ServletContext servletContext;
	
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
        	Util.getConsoleLogger().info("ContextRefreshedEvent start ###################");
        	Util.getFileLogger().info("ContextRefreshedEvent start ###################");
     		 
        	try {
        		
        	
    		/** 更新EntityType Enum **/
    		scheduledExecutorService.submit(new GetEntityTypeAsListCallable());
    		
    		/** 拿取ServletContext **/
//    	    if (!(event instanceof ContextRefreshedEvent)) return;
//    	    ContextRefreshedEvent e = (ContextRefreshedEvent) event;
//    	    ApplicationContext appContext = e.getApplicationContext();
//    	    if (!(appContext instanceof WebApplicationContext)) return;
//    	    WebApplicationContext ctx = (WebApplicationContext) e.getApplicationContext();
//    	    ServletContext context = ctx.getServletContext();
    		
//    	    System.out.println(" context.getContextPath(): " +  servletContext.getContextPath());
    	   
//    		ClassLoader cl = ClassLoader.getSystemClassLoader();
//
//            URL[] urls = ((URLClassLoader)cl).getURLs();
//
//            for(URL url: urls){
//            	System.out.println("classPath: " + url.getFile());
//            }
            
//            try {
//				ClassPathResource classPathResource = new ClassPathResource("version.properties");
//				System.out.println("classPathResource.getPath(): " +  classPathResource.getPath());
//				File file = classPathResource.getFile();
//				InputStream inputStream = classPathResource.getInputStream();
//				System.out.println("file.getName(): " +  file.getName());
//				
//				PropertiesConfiguration conf = new PropertiesConfiguration(classPathResource.getPath());
//				Util.getConsoleLogger().info("ContextRefreshedEvent conf: " + conf);
//				
//				Map<String, String> versionParam = Util.getVersionParam();
//				
//				Object backend = conf.getProperty("backend");
//				Util.getConsoleLogger().info("ContextRefreshedEvent backend: " + backend);
//				versionParam.put("version_backend", backend.toString());
//				Object web_channel = conf.getProperty("web_channel");
//				Util.getConsoleLogger().info("ContextRefreshedEvent web_channel: " + web_channel);
//				versionParam.put("version_web_channel", web_channel.toString());
//				Object info360 = conf.getProperty("info360");
//				Util.getConsoleLogger().info("ContextRefreshedEvent info360: " + info360);
//				versionParam.put("version_info360", info360.toString());
//				Object webapi = conf.getProperty("webapi");
//				Util.getConsoleLogger().info("ContextRefreshedEvent webapi: " + webapi);
//				versionParam.put("version_webapi", webapi.toString());
//				Object simulation = conf.getProperty("simulation");
//				Util.getConsoleLogger().info("ContextRefreshedEvent simulation: " + simulation);
//				versionParam.put("version_simulation", simulation.toString());
//				
//				Util.getConsoleLogger().info("ContextRefreshedEvent versionParam: " + versionParam);
////				Util.getConsoleLogger().info("ContextRefreshedEvent conf: " + conf);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
    		
//    	    InputStream resourceAsStream = context.getResourceAsStream("version.properties");
    		// testing
//    		String path = System.getProperties().getProperty("user.dir");
//    		Util.getConsoleLogger().info("ContextRefreshedEvent path: " + path);
//    		try {
//				PropertiesConfiguration conf = new PropertiesConfiguration(".\\log4j2\\version.properties");
//				Util.getConsoleLogger().info("ContextRefreshedEvent conf: " + conf);
//				
//				Object backend = conf.getProperty("backend");
//				Util.getConsoleLogger().info("ContextRefreshedEvent backend: " + backend);
//				
//			} catch (ConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
    		
//    		scheduledExecutorService.submit(new TableDateTransfer());
        	}catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
        	
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