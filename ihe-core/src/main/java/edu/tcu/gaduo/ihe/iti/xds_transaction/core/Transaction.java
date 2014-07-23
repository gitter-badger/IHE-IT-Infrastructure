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

	/**
	 * @param request is the SOAP's body, and send by web service.
	 * @return It's web service response. 
	 */
	public abstract OMElement send(OMElement request) ;
	/**
	 * To implement the Transaction iti-20 RecordAuditEvent
	 */
	public abstract void auditLog() ;

	public OMElement getRequest() {
		return request;
	}

	public OMElement getResponse() {
		return response;
	}

	protected void setRequest(OMElement request) {
		this.request = request;
	}

	protected void setResponse(OMElement response) {
		this.response = response;
	}

	public MessageContext getContext() {
		return context;
	}

	protected void setContext(MessageContext context) {
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
	
	protected boolean assertEquals(OMElement response, String success){
		if(response.toString().equals(success))
			return true;
		return false;
	}
	

	protected boolean simple = true;
	protected boolean swa = !true;
	protected boolean async = !true;

	public boolean isSimple() {
		return simple;
	}
	public void setSimple(boolean simple) {
		this.simple = simple;
		if(simple){
			swa = false;
			async = false;
		}
	}
	
	public boolean isSWA(){
		return this.swa;
	}
	
	public void setSWA(boolean swa){
		this.swa = swa;
		if(swa){
			simple = false;
			async = false;
		}
	}
	
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
		if(async){
			simple = false;
			swa = false;
		}
	}
}

