package edu.tcu.gaduo.ihe.iti.xds_transaction.core;

import java.sql.Timestamp;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility._interface.ICommon;


public abstract class Transaction {
	protected ICommon c;
	protected MessageContext context;
	protected OMElement request, response;

	public static Logger logger = Logger.getLogger(Transaction.class);

	public abstract OMElement send(OMElement request) ;

	public OMElement getRequest() {
		return request;
	}

	public OMElement getResponse() {
		return response;
	}

	public void setRequest(OMElement request) {
		this.request = request;
	}

	public void setResponse(OMElement response) {
		this.response = response;
	}

	public MessageContext getContext() {
		return context;
	}

	public void setContext(MessageContext context) {
		this.context = context;
	}

	protected void gc() {
		Runtime r = Runtime.getRuntime();
		long memory = r.freeMemory();
		logger.debug("Free Memory : " + memory);
		r.gc();
	}

	protected String createTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}
}
