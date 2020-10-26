package org.jboss.labs.failover;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

/**
 * Session Bean implementation class MessageSenderSLSB
 */
@Stateless(mappedName = "MessageSenderSLSB")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MessageSenderSLSB implements MessageSenderSLSBLocal {

	
	@EJB
	StoreJMSDataLocal storeJMSData;
	
	@Resource(mappedName = "java:/RemoteJmsXA")
	private ConnectionFactory connectionFactory;

	@Resource
	SessionContext sessionContext;
	
	private Connection connection = null;
	private static Logger log = Logger.getLogger(MessageSenderSLSB.class);
	
	
    public MessageSenderSLSB() {
        
    }
    
    
//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String sendQueueMessage(String queueName, String messageText, int numOfMessages){
		try{
			for(int i = 1; i <= numOfMessages; i++){
				send(queueName,messageText,i);
			}
			log.info(numOfMessages + " messages sent successfully");
			return "success";
		} catch(Exception e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String send(String queueName, String messageText, int messageNumber){
		try{
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(); // as per JMS 2.0 spec
			TextMessage textMessage = session.createTextMessage(messageText);
			Queue queue = session.createQueue(queueName);
			MessageProducer messageProducer = session.createProducer(queue);
			textMessage.setStringProperty("messageid",String.valueOf(messageNumber));
			messageProducer.send(textMessage);
			storeJMSData.storeJMSData(String.valueOf(messageNumber),textMessage.getBody(String.class));
			return "success";
		} catch(JMSException e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			return e.getErrorCode();
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
    
    
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String sendQueueMessage(String queueName, Message message){
		try{
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(); // as per JMS 2.0 spec
			Queue queue = session.createQueue(queueName);
			MessageProducer messageProducer = session.createProducer(queue);
			messageProducer.send(message);
			storeJMSData.removeJMSData(message.getStringProperty("messageid"));
			return message.getJMSMessageID();
		} catch(Exception e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
    
}