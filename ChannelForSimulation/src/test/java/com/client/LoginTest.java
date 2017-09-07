package com.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bean.LoginBean;
import com.util.AmqpUtil;
import com.util.EntityTypeEnum;
import com.util.TestUtil;
import com.util.Util;

@RunWith(SpringRunner.class)

@SpringBootTest
public class LoginTest {
	@Test
	public void contexLoads() throws Exception {
//		LoginBean loginBean = new LoginBean();
//		String result= "";
//		String result= null;
		
		LoginBean loginBean = new LoginBean();
		loginBean.setACtype("Client");
//		loginBean.setChannel("wechat");
//		loginBean.setChannel(Util.WECHAT_ENTITYTYPEID); // 後端已經改成抓取entityTypeID, 之後各channel端也要去抓取DB
		loginBean.setMaxCount("0");
		loginBean.setType("login");
		loginBean.setUserID(TestUtil.userID);
		loginBean.setUserName("VoiceUser");
		
		loginBean.setCallID(TestUtil.callID);
		loginBean.setTenantID(TestUtil.tenantID);
		loginBean.setEntityTypeID(EntityTypeEnum.VOICE.getEntityTypeID());
		
		String loginBeanJSON = Util.getGson().toJson(loginBean,LoginBean.class);
//		AmqpUtil.getAmqpTemplate().convertAndSend(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, loginBeanJSON);
		AmqpUtil.getAmqpTemplate().convertSendAndReceive(AmqpUtil.QUEUE_NAME.CHANNEL_TO_BACKEND_QUEUE01, loginBeanJSON);
		
//		/** test驗證 **/ 
//        assertThat(result).isNotNull();
		
	}
}
