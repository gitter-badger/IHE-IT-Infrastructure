package edu.tcu.gaduo.ihe.utility.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.util.AXIOMUtil;
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
import org.apache.axis2.wsdl.WSDLConstants;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;

public class Soap implements ISoap {
	protected String action = "";
	protected ServiceClient sender = null;
	protected String endpoint = "";
	
	protected OMElement data = null;
	protected boolean mtom_xop;
	
	private Options option;
	private MessageContext request, response;
	private OperationClient client;
	private boolean swa;
	private boolean canSend = true;
	public static Logger logger = Logger.getLogger(Soap.class);

	public Soap(String endpoint, String action) {
		setEndpoint(endpoint);
		setAction(action);
		setRequest(new MessageContext());
		try {
			setSender(new ServiceClient());
		} catch (AxisFault e) {
			logger.error("Soap : " + e.toString());
			setCanSend(false);
		}

	}

	public MessageContext send(InputStream is) {
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "utf-8");
			String str = writer.toString();
			OMElement element = AXIOMUtil.stringToOM(str);
			return send(element);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public MessageContext send(String data){
		try {
			OMElement element = AXIOMUtil.stringToOM(data);
			return send(element);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MessageContext send(OMElement data) {
		if (!getCanSend()) {
			logger.error("Can not Send");
			return null;
		}
		try {
			request.setEnvelope(createEnvelope(data));
			request.setDoingMTOM(true);
			request.setDoingSwA(true);
			
			sender.setOptions(getOptions());
			
			client = sender.createClient(ServiceClient.ANON_OUT_IN_OP);
			client.addMessageContext(request);
			
			client.execute(true);
			response = client.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			if (response == null) {
				logger.error("No MessageContext");
				return null;
			}
			//-------------- debug ------------ 
			SOAPEnvelope envelope01 = request.getEnvelope();
			logger.info("request\t" + envelope01);
			SOAPEnvelope envelope02 = response.getEnvelope();
			logger.info("response\t" + envelope02.toString());
			//-------------- debug ------------ 
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
		options.setTimeOutInMilliSeconds(1000 * 60 * 10);
		options.setAction(getAction());
		options.setProperty(WSDL2Constants.ATTRIBUTE_MUST_UNDERSTAND, "1");
		options.setTo(new EndpointReference(getEndpoint()));
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setProperty(Constants.Configuration.ENABLE_SWA, isSWA());
		return options;
	}

	private SOAPEnvelope createEnvelope(OMElement data) {
		SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope envelope = fac.getDefaultEnvelope();
		SOAPHeader header = envelope.getHeader();
		OMNamespace wsa = fac.createOMNamespace("http://www.w3.org/2005/08/addressing", "wsa");
		OMElement to = fac.createOMElement("To", wsa);
		to.setText(getEndpoint());
		header.addChild(to);
		OMElement messageID = fac.createOMElement("MessageID", wsa);
		messageID.setText(UUID.randomUUID().toString());
		header.addChild(messageID);
		OMElement Action = fac.createOMElement("Action", wsa);
		Action.setText(action);
		header.addChild(Action);
		SOAPBody body = envelope.getBody();
		body.addChild(data);
		return envelope;
	}

	public String addAttachment(DataHandler handler) {
		String contentId = request.addAttachment(handler);
		logger.info("cid:" + contentId);
		return "cid:" + contentId;
	}

	public Options getOption() {
		return option;
	}

	public void setOption(Options option) {
		this.option = option;
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
		return mtom_xop;
	}

	public void setMTOM_XOP(boolean MTOM_XOP) {
		this.mtom_xop = MTOM_XOP;
	}

	public boolean getCanSend() {
		return canSend;
	}

	public void setCanSend(boolean canSend) {
		this.canSend = canSend;
	}

	public boolean isSWA() {
		return swa;
	}

	public void setSwa(boolean swa) {
		this.swa = swa;
	}
}
