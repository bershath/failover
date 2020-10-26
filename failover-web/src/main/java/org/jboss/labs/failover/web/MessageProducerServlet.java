package org.jboss.labs.failover.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;

import org.jboss.labs.failover.MessageSenderSLSBLocal;

@WebServlet("/producer")
public class MessageProducerServlet extends HttpServlet {

	private static final long serialVersionUID = -2317575218124258639L;
	@EJB
	MessageSenderSLSBLocal messageSenderSLSBLocal;

	public MessageProducerServlet() { }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Message Sender Bean used; outcome : " + messageSenderSLSBLocal.sendQueueMessage("A", "Test Text Message", 10000));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}