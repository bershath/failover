package org.jboss.labs.failover;

import javax.ejb.Local;

@Local
public interface StoreJMSDataLocal {
	public void storeJMSData(String messageId, String bodyText);
	public void removeJMSData(String messageId) throws Exception;
}
