package com.gaduo.ihe.it_infrastructure.xds_transaction.service._interface;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import com.gaduo.ihe.utility._interface.ICommon;

public class XDSTransaction implements IXDSTransaction {
	protected ICommon c;
	protected MessageContext context;
	protected OMElement request, response;

	public static Logger logger = Logger.getLogger(XDSTransaction.class);

	public OMElement send(OMElement request) {
		return null;
	}

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
		logger.info("Free Memory : " + memory);
		r.gc();
	}
}
