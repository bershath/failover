package org.jboss.labs.failover;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.logging.Logger;

/**
 * Message-Driven Bean implementation class for: ConsumerMDB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queue/A"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "queue/A")

@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsumerMDB implements MessageListener {

	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@EJB
	MessageSenderSLSBLocal local;
	
	@EJB
	StoreJMSDataLocal storeJMSData;
	
	private static Logger log = Logger.getLogger(ConsumerMDB.class);
	
	public ConsumerMDB() {}
	
	/**
     * @see MessageListener#onMessage(Message)
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onMessage(Message message) {
    	try {
    		local.sendQueueMessage("B", message);
    		Thread.sleep(300);
        	log.trace("MSG id " + message.getJMSMessageID());
		} catch (InterruptedException | JMSException e) {
			e.printStackTrace();
		}
    }
	
	public void ejbCreate() {
		log.info(" ejbCreate called" + new Date());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ejbRemove() {
		log.info(" ejbRemove called" + new Date());
	}

}