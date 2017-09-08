package com.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bean.ExitBean;
import com.bean.LoginBean;
import com.util.AmqpUtil;
import com.util.EntityTypeEnum;
import com.util.TestUtil;
import com.util.Util;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ClientExitRunnable implements Runnable{
//	@Test
//	public void contexLoads() throws Exception {
//	}

	@Override
	public void run() {
		ExitBean exitBean = new ExitBean();
		exitBean.setChannel(EntityTypeEnum.VOICE.getEntityTypeID());
		exitBean.setType("exit");
		exitBean.setUserID(TestUtil.userID_client);
//		exitBean.setWaittingAgent(false);
//		exitBean.setWaittingAgentID(waittingAgentID); // cache
		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, Util.getGson().toJson(exitBean,ExitBean.class));
	}
}
