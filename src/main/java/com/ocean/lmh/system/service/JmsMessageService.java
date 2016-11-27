package com.ocean.lmh.system.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.ocean.lmh.system.model.vo.MessageEntity;


/**
 * 消息队列
 */
@Service("jmsMessageService")
public class JmsMessageService {

	private final static Logger logger = LoggerFactory.getLogger(JmsMessageService.class);

	private JmsTemplate myJmsTemplate;
	private Topic destination;

	public JmsTemplate getMyJmsTemplate() {
		return myJmsTemplate;
	}

	public void setMyJmsTemplate(JmsTemplate myJmsTemplate) {
		this.myJmsTemplate = myJmsTemplate;
	}

	public Topic getDestination() {
		return destination;
	}

	public void setDestination(Topic destination) {
		this.destination = destination;
	}

	public void sendMessage(final MessageEntity message) {
		logger.info("send jms Message start");
		myJmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});

		logger.info("send jms Message end");
	}
}

