package com.spring;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.gson.Gson;
import com.util.Util;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;

@Configuration
@EnableMBeanExport(defaultDomain="${projectName}")
public class rabbitmqBeanGenerator {
	
//	@Value("${rabbitmqhostname}")
//	public static String RABBIT_SERVER;
	@Value("${rabbitmq-hostname}")
	public String rabbitmqServer;

	@Value("${rabbitmq-username}")
	public String rabbitmqUsername;

	@Value("${rabbitmq-password}")
	public String rabbitmqPassword;
	
	// 設定server IP, username, password
	@Bean
	public CachingConnectionFactory connectionFactory() {
		Util.getFileLogger().debug("RABBIT_MQSERVER: " + rabbitmqServer);
		Util.getFileLogger().debug("connectionFactory() bean instantiation starts");
		Util.getFileLogger().debug("connectionFactory() this.rabbitmqServer: " + this.rabbitmqServer);
		Util.getFileLogger().debug("connectionFactory() this.rabbitmqUsername: " + this.rabbitmqUsername);
		Util.getFileLogger().debug("connectionFactory() this.rabbitmqPassword: " + this.rabbitmqPassword);
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(this.rabbitmqServer);
		cachingConnectionFactory.setUsername(this.rabbitmqUsername);
		cachingConnectionFactory.setPassword(this.rabbitmqPassword);
		Util.getFileLogger().debug("connectionFactory() bean instantiated end");
		return cachingConnectionFactory;
	}
	
	@Bean RabbitAdmin rabbitAdmin(CachingConnectionFactory aCachingConnectionFactory){
		return new RabbitAdmin(aCachingConnectionFactory);
	}
	
	
	/**
	 * modify the original connectionFactory to prevent failed request from being sent back to rabbitmq infinitely
	 * @param connectionFactory
	 * @return
	 */
	@Bean // reference website: http://blog.trifork.com/2016/02/29/spring-amqp-payload-validation/
	SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
	  Util.getConsoleLogger().debug("rabbitListenerContainerFactory() called");
	  Util.getFileLogger().debug("rabbitListenerContainerFactory() bean instantiation starts");
		SimpleRabbitListenerContainerFactory listenerContainerFactory =
	      new SimpleRabbitListenerContainerFactory();
	  listenerContainerFactory.setConnectionFactory(connectionFactory);
	  listenerContainerFactory.setErrorHandler(
	      new ConditionalRejectingErrorHandler(
	          new InvalidPayloadRejectingFatalExceptionStrategy()));
//	  listenerContainerFactory.setMessageConverter(messageConverter());
	  Util.getFileLogger().debug("rabbitListenerContainerFactory() bean instantiation end");
	  return listenerContainerFactory;
	}
	
	/**
	 * this bean used by rabbitListenerContainerFactory()
	 * Extension of Spring-AMQP's
	 * {@link ConditionalRejectingErrorHandler.DefaultExceptionStrategy}
	 * which also considers a root cause of {@link MethodArgumentNotValidException}
	 * (thrown when payload does not validate) as fatal.
	 */
	static class InvalidPayloadRejectingFatalExceptionStrategy implements FatalExceptionStrategy {
	 
		@Override
		public boolean isFatal(Throwable t) {
			// if (t instanceof ListenerExecutionFailedException &&
			// (
			// // t.getCause() instanceof MessageConversionException ||
			// t.getCause() instanceof MethodArgumentNotValidException)) {
			// Util.getFileLogger()
			// .warn("Fatal message conversion error; message rejected; it will be dropped: {}",
			// ((ListenerExecutionFailedException) t).getFailedMessage());
			// return true;
			// }
			// return false;
						
			Util.getFileLogger().warn("isFatal() - Util.getExceptionMsg(t): " + Util.getExceptionMsg(t));
//			Util.getFileLogger().warn("isFatal() - called: " + t);
//			Util.getFileLogger().warn("isFatal - t: " + t);
//			Util.getFileLogger().warn("isFatal - t.getMessage(): " + t.getMessage());
//			Util.getFileLogger().warn("isFatal - t.getCause(): " + t.getCause());
//			Util.getFileLogger().warn("isFatal - t.getStackTrace(): " + t.getStackTrace());
//			Util.getConsoleLogger().debug("isFatal - t: " + t);
			return true; // it's the same as try-catch all exception on
							// consumers and throws
							// AmqpRejectAndDontRequeueException
			// return false; // 會重新requeue
		}
	}
    
}
