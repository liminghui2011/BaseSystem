package com.ocean.lmh.system.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.ocean.lmh.system.model.vo.MessageEntity;

public class ActivemqMessageTest implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		System.err.println("start message");
		ObjectMessage objectMessage = (ObjectMessage) arg0;
		try {
			MessageEntity messageEntity = (MessageEntity) objectMessage.getObject();
			System.err.println(messageEntity.toString());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
