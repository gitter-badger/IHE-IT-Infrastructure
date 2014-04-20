package edu.tcu.gaduo.ihe.utility.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;

public class SoapWithAttachment extends Soap implements ISoap {
	protected String action = "";
	protected String endpoint = "";

	private OMElement data = null;
	private boolean mtom_xop;

	private boolean swa;
	public static Logger logger = Logger.getLogger(SoapWithAttachment.class);

	private final String boundary = "MIMEBoundary_65d9a8eda47c4546d5b6e58c9d77eb510d3398f7cd3dd5f5";
	private final String start = "0.75d9a8eda47c4546d5b6e58c9d77eb510d3398f7cd3dd5f5@apache.org";

	private HttpURLConnection conn;
	private OMElement envelope ;
	private HashMap<String, ArrayList<Object>> attachments = null;
	
	public SoapWithAttachment(String endpoint, String action) {
		super(endpoint, action);
		setEndpoint(endpoint);
		setAction(action);
		initEnvelope();
		if (attachments == null)
			attachments = new HashMap<String, ArrayList<Object>>();
	}

	public MessageContext send(OMElement data) {
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMNamespace soapenv = Namespace.SOAP12.getOMNamespace();
		OMElement body = axiom.createOMElement("Body", null, null);
		body.setNamespace(soapenv);
		body.addChild(data);
		envelope.addChild(body);
		
		try {
			URL url = new URL(endpoint);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setRequestMethod("POST");
			
			HashMap<String, String> httpHeaders = new HashMap<String, String>();
			if (attachments != null)
				httpHeaders.put(
						"Content-Type",
						"multipart/related; "
								+ "boundary=\"" + boundary + "\"; "
								+ "type=\"application/xop+xml\"; "
								+ "start=\"" + start + "\"; "
								+ "start-info=\"application/soap+xml\"; "
								+ "action=\"" + action + "\"");

			else
				httpHeaders.put("Content-Type", "application/xop+xml; charset=UTF-8; type=\"application/soap+xml\"");
			httpHeaders.put("User-Agent", "Axis2");
			httpHeaders.put("Transfer-Encoding", "chunked");

			StringBuffer msg = new StringBuffer();

			
			/* SOAP Part*/
			if (attachments != null) {
				msg.append("--" + boundary + "\r\n");
				msg.append("Content-Type: application/xop+xml; charset=UTF-8; type=\"application/soap+xml\"\r\n");
				msg.append("Content-ID: <" + start + ">\r\n");
				msg.append("Content-Transfer-Encoding: binary\r\n");
				msg.append("\r\n");
			}

			msg.append(envelope.toString() + " \r\n");
			msg.append("\r\n");
			/* Document Part*/
			if (attachments != null) {
				for (String id : attachments.keySet()) {
					ArrayList<Object> attachmentX = attachments.get(id);
					byte[] content = (byte[]) attachmentX.get(0);
					String mime_type = (String) attachmentX.get(1);

					msg.append("--" + boundary + "\r\n");
					msg.append("Content-Type: " + mime_type + "\r\n");
					msg.append("Content-ID: <" + id + ">\r\n");
					msg.append("Content-Transfer-Encoding: 8bit\r\n");
					msg.append("\r\n");
					msg.append(new String(content));
				}
				msg.append("--" + boundary + "--\r\n");
			}

			String message = msg.toString();

			for (String name : httpHeaders.keySet()) {
				String value = httpHeaders.get(name);
				conn.setRequestProperty(name, value);
			}
			conn.setRequestProperty("Content-Length", String.valueOf(message.length()));
			conn.connect();
			OutputStream os = conn.getOutputStream();
			os.write(message.getBytes());
			os.close();

			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	private void createEnvelope(){
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
/**
--MIMEBoundaryurn_uuid_97193F62664F18CAB81387618586441
Content-Type: application/xop+xml; charset=UTF-8; type="application/soap+xml"
Content-Transfer-Encoding: binary
Content-ID: <0.urn:uuid:97193F62664F18CAB81387618586442@apache.org>

<?xml version='1.0' encoding='UTF-8'?>
<soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope">
	<soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
		<wsa:Action>urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-bResponse</wsa:Action>
		<wsa:RelatesTo>urn:uuid:512d5ceb-03ec-4e8b-a44e-d8b9723257e9</wsa:RelatesTo>
	</soapenv:Header>
	<soapenv:Body>
		<rs:RegistryResponse xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" status="urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure">
			<rs:RegistryErrorList>
				<rs:RegistryError codeContext="Size attribute in Provide and Register metadata does not match supplied document: Metadata has 1000 and contents has 1003" errorCode="XDSRepositoryMetadataError" location="&#xa;&#x9;gov.nist.registry.ws.ProvideAndRegisterDocumentSet.validate_size_and_hash(ProvideAndRegisterDocumentSet.java:573)&#xa;&#x9;gov.nist.registry.ws.ProvideAndRegisterDocumentSet.store_document_swa_xop(ProvideAndRegisterDocumentSet.java:540)&#xa;&#x9;gov.nist.registry.ws.ProvideAndRegisterDocumentSet.provide_and_register(ProvideAndRegisterDocumentSet.java:309)&#xa;&#x9;gov.nist.registry.ws.ProvideAndRegisterDocumentSet.provideAndRegisterDocumentSet(ProvideAndRegisterDocumentSet.java:130)&#xa;&#x9;gov.nist.registry.ws.serviceclasses.AbstractRepository.ProvideAndRegisterDocumentSetRequest(AbstractRepository.java:101)&#xa;&#x9;sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)&#xa;&#x9;sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)&#xa;&#x9;sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)&#xa;&#x9;java.lang.reflect.Method.invoke(Unknown Source)&#xa;&#x9;gov.nist.registry.common2.service.AbstractXDSRawXMLINoutMessageReceiver.invokeBusinessLogic(AbstractXDSRawXMLINoutMessageReceiver.java:139)&#xa;&#x9;org.apache.axis2.receivers.AbstractInOutMessageReceiver.invokeBusinessLogic(AbstractInOutMessageReceiver.java:40)&#xa;&#x9;org.apache.axis2.receivers.AbstractMessageReceiver.receive(AbstractMessageReceiver.java:114)&#xa;&#x9;org.apache.axis2.engine.AxisEngine.receive(AxisEngine.java:173)&#xa;&#x9;org.apache.axis2.transport.http.HTTPTransportUtils.processHTTPPostRequest(HTTPTransportUtils.java:167)&#xa;&#x9;org.apache.axis2.transport.http.HTTPWorker.service(HTTPWorker.java:267)&#xa;" severity="urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error" />
			</rs:RegistryErrorList>
		</rs:RegistryResponse>
	</soapenv:Body>
</soapenv:Envelope>
--MIMEBoundaryurn_uuid_97193F62664F18CAB81387618586441--
 * 
 * */
		
		
		
//		MessageContext context = new MessageContext();
	}
	

	private OMElement initEnvelope() {
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMNamespace wsa = Namespace.WSA.getOMNamespace();
		OMNamespace soapenv = Namespace.SOAP12.getOMNamespace();
		/* SOAP Evnelope */
		envelope = axiom.createOMElement("Envelope", null, null);
		envelope.setNamespace(soapenv);
		envelope.setNamespace(soapenv);
		
		/* SOAP Header*/
		OMElement header = axiom.createOMElement("Header", null, null);
		header.setNamespace(wsa);
		header.setNamespace(soapenv);
		OMElement To = axiom.createOMElement("To", null, null);
		To.setNamespace(wsa);
		To.setText(endpoint);
		header.addChild(To);
		OMElement MessageID = axiom.createOMElement("MessageID", null, null);
		MessageID.setNamespace(wsa);
		MessageID.setText("urn:uuid:512d5ceb-03ec-4e8b-a44e-d8b9723257e9");
		header.addChild(MessageID);
		OMElement Action = axiom.createOMElement("Action", null, null);
		Action.setNamespace(wsa);
		Action.setText("urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b");
		header.addChild(Action);
		envelope.addChild(header);
		return envelope;
	}

	public String addAttachment(byte[] bytes, String mimeType) {
		ArrayList<Object> attachment = new ArrayList<Object>();
		attachment.add(bytes);
		attachment.add(mimeType);
		String contentId = UUID.randomUUID() + "@tcu.edu";
		attachments.put(contentId, attachment);
		return "cid:" + contentId;
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

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public boolean isMTOM_XOP() {
		return mtom_xop;
	}

	public void setMTOM_XOP(boolean MTOM_XOP) {
		this.mtom_xop = MTOM_XOP;
	}

	public boolean isSWA() {
		return swa;
	}

	public void setSwa(boolean swa) {
		this.swa = swa;
	}
}
