package com.gaduo.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.WSDL2Constants;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.wsdl.WSDLConstants;
import org.apache.commons.httpclient.Header;
import org.apache.log4j.Logger;

import com.gaduo.webservice._interface.ISoap;

public class Soap implements ISoap {
	private String action = "";
	private String namespace = "";
	private String endpoint = "";
	private boolean MTOM_XOP;
	private OMElement data = null;
	private ServiceClient sender = null;
	private Options option;
	private MessageContext request, response;
	private OperationClient client;
	private boolean canSend = true;
	public static Logger logger = Logger.getLogger(Soap.class);

	public Soap(String endpoint, String namespace, String action,
			boolean MTOM_XOP) {
		setMTOM_XOP(MTOM_XOP);
		setNamespace(namespace);
		setAction(action);
		setEndpoint(endpoint);
		setRequest(new MessageContext());
		try {
			setSender(new ServiceClient());
		} catch (AxisFault e) {
			logger.error("Soap : " + e.toString());
			setCanSend(false);
		}

	}

	public MessageContext send(OMElement data) {
		if (!getCanSend()) {
			logger.error("Can not Send");
			return null;
		}
		SOAPEnvelope envelope;
		try {
			sender.setOptions(getOptions());
			request.setEnvelope(createEnvelope(data));
			client = sender.createClient(ServiceClient.ANON_OUT_IN_OP);
			client.addMessageContext(request);
			client.execute(true);
			envelope = request.getEnvelope();
			logger.info("request\t" + envelope);
			response = client
					.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			if (response == null) {
				logger.error("No MessageContext");
				return null;
			}
			envelope = response.getEnvelope();
			logger.info("response\t" + envelope.toString());
		} catch (org.apache.axiom.om.OMException e) {
			logger.error("send : \t" + e.toString());
		} catch (java.lang.NullPointerException e) {
			logger.error("send : \t" + e.toString());
		} catch (AxisFault e) {
			logger.error("send : \t" + e.toString());
		}
		return response;
	}

	protected Options getOptions() {
		Options options = new Options();
		options.setAction(getAction());
		options.setProperty(WSDL2Constants.ATTRIBUTE_MUST_UNDERSTAND, "1");
		options.setTo(new EndpointReference(getEndpoint()));
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setProperty(Constants.Configuration.CACHE_ATTACHMENTS,
				Constants.VALUE_TRUE);

		if (isMTOM_XOP()) {
			options.setProperty(Constants.Configuration.ENABLE_MTOM,
					((isMTOM_XOP()) ? Constants.VALUE_TRUE
							: Constants.VALUE_FALSE));
		} else {
			options.setProperty(Constants.Configuration.ENABLE_SWA,
					((isMTOM_XOP()) ? Constants.VALUE_FALSE
							: Constants.VALUE_TRUE));
		}
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("ContentType", "application/xop+xml"));
		options.setProperty(HTTPConstants.HTTP_HEADERS, headers);
		options.setTimeOutInMilliSeconds(1000 * 60 * 10);
		return options;
	}

	private SOAPEnvelope createEnvelope(OMElement data) {
		SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope envelope = fac.getDefaultEnvelope();
		SOAPHeader header = envelope.getHeader();
		OMNamespace wsa = fac.createOMNamespace(
				"http://www.w3.org/2005/08/addressing", "wsa");
		OMElement to = fac.createOMElement("To", wsa);
		to.setText(getEndpoint());
		header.addChild(to);
		OMElement messageID = fac.createOMElement("MessageID", wsa);
		messageID.setText(UUID.randomUUID().toString());
		header.addChild(messageID);
		OMElement Action = fac.createOMElement("Action", wsa);
		Action.addAttribute("MustUnderstand", "1", null);
		Action.setText(action);
		header.addChild(Action);
		SOAPBody body = envelope.getBody();
		body.addChild(data);
		return envelope;
	}

	public String addAttachment(DataHandler handler) {
		String contentId = request.addAttachment(handler);
		return "cid:" + contentId;
	}

	public Options getOption() {
		return option;
	}

	public void setOption(Options option) {
		this.option = option;
	}

	public ServiceClient getSender() {
		return sender;
	}

	public void setSender(ServiceClient sender) {
		this.sender = sender;
	}

	public OMElement getData() {
		return data;
	}

	public void setData(OMElement data) {
		this.data = data;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public MessageContext getResponse() {
		return response;
	}

	public void setResponse(MessageContext response) {
		this.response = response;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public MessageContext getRequest() {
		return request;
	}

	public void setRequest(MessageContext request) {
		this.request = request;
	}

	public boolean isMTOM_XOP() {
		return MTOM_XOP;
	}

	public void setMTOM_XOP(boolean MTOM_XOP) {
		this.MTOM_XOP = MTOM_XOP;
	}

	public boolean getCanSend() {
		return canSend;
	}

	public void setCanSend(boolean canSend) {
		this.canSend = canSend;
	}

}
