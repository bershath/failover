package org.jboss.labs.failover;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Entity implementation class for Entity: JMSData
 *
 */
@Entity
@Table( name = "msg_data" )
public class JMSData implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8415423568648765378L;
	private String messageId;
	private String messageBody;

	public JMSData() {
		super();
	}
	
	@Id
	@Column( name = "message_id" )
	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	@Column( name = "message_body" )
	public String getMessageBody() {
		return this.messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JMSData other = (JMSData) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		return true;
	}
   
	
}
