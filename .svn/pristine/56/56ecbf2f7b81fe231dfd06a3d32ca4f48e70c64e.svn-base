package com.gaduo.webservice;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.WSDL2Constants;
import org.apache.log4j.Logger;

import com.gaduo.ihe.utility.webservice.nonblock.NonBlockCallBack;

public class ServiceConsumer {
	private String Action = "";
	private String Namespace = "";
	private String ServiceUrl = "";
	private OMElement Data = null;
	private ServiceClient sender = null;
	private NonBlockCallBack callback;
	private EndpointReference targetEPR;
	private Options option;

	public static Logger logger = Logger.getLogger(ServiceConsumer.class);
	public ServiceConsumer(String serviceUrl, OMElement data, String namespace,
			String action, boolean isMTOM_XOP, boolean isAddress) {
		logger.info("isMTOM_XOP : " + isMTOM_XOP);
		logger.info("isAddress : " + isAddress);
		this.setServiceUrl(serviceUrl);
		this.setData(data);
		this.setNamespace(namespace);
		this.setAction(action);

		callback = new NonBlockCallBack();
		try {
			sender = new ServiceClient();
			sender.setOptions(getOptions(action, isMTOM_XOP, this.ServiceUrl));
			sender.engageModule(Constants.MODULE_ADDRESSING);
			sender.sendReceiveNonBlocking(data, callback);
			synchronized (callback) {
				try {
					callback.wait();
				} catch (InterruptedException e) {
					logger.error(e.toString());
				}
			}
		} catch (AxisFault e) {
			logger.error(e.toString());
		} finally {
			if (sender != null) {
				try {
					sender.cleanup();
				} catch (Exception e) {
					logger.error(e.toString());
				}
			}
		}
	}

	protected Options getOptions(String action, boolean enableMTOM, String url) {
		Options options = new Options();
		options.setAction(action);
		options.setProperty(WSDL2Constants.ATTRIBUTE_MUST_UNDERSTAND, "1");
		options.setTo(new EndpointReference(url));
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		options.setProperty(Constants.Configuration.ENABLE_MTOM, 
				((enableMTOM) ? Constants.VALUE_TRUE : Constants.VALUE_FALSE));
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTimeOutInMilliSeconds(1000*60*10);
		return options;
	}
	
	public OMElement getData() {
		return Data;
	}

	public void setData(OMElement data) {
		Data = data;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		logger.info("action : " + action);
		Action = action;
	}

	public String getNamespace() {
		return Namespace;
	}

	public void setNamespace(String namespace) {
		Namespace = namespace;
	}

	public String getServiceUrl() {
		return ServiceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		logger.info("serviceUrl : " + serviceUrl);
		ServiceUrl = serviceUrl;
	}

	public ServiceClient getSender() {
		return sender;
	}

	public void setSender(ServiceClient serviceClient) {
		this.sender = serviceClient;
	}

	public NonBlockCallBack getCallback() {
		return callback;
	}

	public void setCallback(NonBlockCallBack callback) {
		this.callback = callback;
	}

	public EndpointReference getTargetEPR() {
		return targetEPR;
	}

	public void setTargetEPR(EndpointReference targetEPR) {
		this.targetEPR = targetEPR;
	}

	public Options getOption() {
		return option;
	}

	public void setOption(Options option) {
		this.option = option;
	}

}