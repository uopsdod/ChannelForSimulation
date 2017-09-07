package com.spring;

import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;

import com.bean.ExitBean;
import com.bean.FindAgentBean;
import com.bean.Interaction;
import com.bean.UserCacheBean;
import com.bean.UserInfoForSimulation;
import com.channel.ChannelMessageListener;
import com.google.gson.JsonObject;
import com.util.AmqpUtil;
import com.util.ChannelUtil;
import com.util.Util;

@Configuration
@EnableRabbit
public class RabbitListenerRegister implements RabbitListenerConfigurer {
	
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
    	Util.getConsoleLogger().debug("configureRabbitListeners() starts");
    	Util.getConsoleLogger().debug("configureRabbitListeners() registrar: "+ registrar);
    	
        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
//        endpoint.setQueueNames("anotherQueue");
//        endpoint.setQueueNames(AmqpUtil.QUEUE_NAME.BACKEND_TO_VOICE_QUEUE);
        endpoint.setQueueNames(Util.getSystemParam().get("queueName")); // 此區塊與util塞值區塊的生命週期須再確認
        endpoint.setId(UUID.randomUUID().toString());
        endpoint.setMessageListener(new ChannelMessageListener());
        registrar.registerEndpoint(endpoint);
        Util.getConsoleLogger().info("IN configureRabbitListeners ends");
        Util.getFileLogger().info("IN configureRabbitListeners ends");
    }
}