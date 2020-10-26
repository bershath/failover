package org.jboss.labs.failover;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

/**
 * Session Bean implementation class StoreJMSData
 */
@Stateless(mappedName = "StoreJMSData")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StoreJMSData implements StoreJMSDataLocal {

	@PersistenceContext(name="primary")
	protected EntityManager em;
    
	private static Logger log = Logger.getLogger(StoreJMSData.class);
	
	public StoreJMSData() {
		
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void storeJMSData(String messageId, String bodyText){
		JMSData jmsData = new JMSData();
		jmsData.setMessageId(messageId);
		jmsData.setMessageBody(bodyText);
		try{
			em.persist(jmsData);
		} catch (Exception e){
			em.close();
			e.printStackTrace();
		} 
		log.info("Inserted a record. Key of the record " + messageId);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void removeJMSData(String messageId) throws Exception{
		JMSData jmsData	= em.find(JMSData.class,messageId);
		if( jmsData != null ){
			em.remove(jmsData);
			log.info("Removed record by the key " + messageId);
		} else{
			log.error("No record found by the key "+ messageId);
			throw new Exception("Record not found");
		}
			
	}
}
