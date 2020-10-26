package org.jboss.labs.failover;

import javax.ejb.Local;
import javax.jms.Message;

@Local
public interface MessageSenderSLSBLocal {

	String sendQueueMessage(String queueName, String messageText, int numOfMessages);
	String sendQueueMessage(String queueName, Message message);

}