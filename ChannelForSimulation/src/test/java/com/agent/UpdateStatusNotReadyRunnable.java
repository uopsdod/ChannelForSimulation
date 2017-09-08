package com.agent;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bean.LoginBean;
import com.bean.UpdateStatusBean;
import com.google.gson.JsonObject;
import com.util.AmqpUtil;
import com.util.EntityTypeEnum;
import com.util.TestUtil;
import com.util.Util;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UpdateStatusNotReadyRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
		Util.getFileLogger().info("onApplicationEvent here01");
		UpdateStatusBean updateStatusBean = new UpdateStatusBean();
		
		// Agent ready
		updateStatusBean.setType("updatestatusvoice");
		updateStatusBean.setStatus("4");
		updateStatusBean.setStartORend("start");
		updateStatusBean.setUserID(TestUtil.dialNO_agent); // 注意: 要每次調整
		Util.getConsoleLogger().info("EntityTypeEnum.VOICE.getEntityTypeID(): " + EntityTypeEnum.VOICE.getEntityTypeID());
		updateStatusBean.setEntityTypeID(EntityTypeEnum.VOICE.getEntityTypeID());
//		String jsonOut = Util.getGson().toJson(updateStatusBean, UpdateStatusBean.class);
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, Util.getGson().toJson(updateStatusBean));
		
	}
}
