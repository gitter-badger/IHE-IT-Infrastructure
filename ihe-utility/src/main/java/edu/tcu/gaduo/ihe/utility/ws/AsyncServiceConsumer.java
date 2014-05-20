package edu.tcu.gaduo.ihe.utility.ws;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.WSDL2Constants;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;
import edu.tcu.gaduo.ihe.utility.ws.nonblock.NonBlockCallBack;

public class AsyncServiceConsumer extends Soap implements ISoap{
	private NonBlockCallBack callback;

	private boolean async;

	public static Logger logger = Logger.getLogger(AsyncServiceConsumer.class);
	public AsyncServiceConsumer(String endpoint, String namespace,String action) {
		super(endpoint, action);
		callback = new NonBlockCallBack();
	}

	public MessageContext send(OMElement data) {
		try {
			sender.setOptions(getOptions(getAction(), isMTOM_XOP(), getEndpoint()));
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
		return callback.getContext();
	}

	protected Options getOptions(String action, boolean enableMTOM, String url) {
		Options options = new Options();
		options.setAction(action);
		options.setTimeOutInMilliSeconds(1000*60*10);
		options.setProperty(WSDL2Constants.ATTRIBUTE_MUST_UNDERSTAND, "1");
		options.setTo(new EndpointReference(url));
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		options.setProperty(Constants.Configuration.ENABLE_MTOM,  ((enableMTOM) ? Constants.VALUE_TRUE : Constants.VALUE_FALSE));
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setUseSeparateListener(async);
		return options;
	}
	
	public NonBlockCallBack getCallback() {
		return callback;
	}

	public void setCallback(NonBlockCallBack callback) {
		this.callback = callback;
	}

}