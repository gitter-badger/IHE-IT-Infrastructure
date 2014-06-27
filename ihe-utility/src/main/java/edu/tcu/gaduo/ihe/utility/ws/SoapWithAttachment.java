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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility.Common;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;

public class SoapWithAttachment extends Soap implements ISoap {
	private boolean swa;
	public static Logger logger = Logger.getLogger(SoapWithAttachment.class);

	private final String boundary ;
	private final String start;

	private HttpURLConnection conn;
	private OMElement envelope ;
	private HashMap<String, ArrayList<Object>> attachments = null;
	
	public SoapWithAttachment(String endpoint, String action) {
		super(endpoint, action);
		Common common = new Common();
		String uuid = common.createUUID();
		uuid = uuid.replace(":", "_");
		boundary = "MIMEBoundary_" + uuid;
		uuid = common.createUUID();
		uuid = uuid.replace(":", "_");
		start = "0." + uuid + "@tcu.edu";
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

			for (String name : httpHeaders.keySet()) {
				String value = httpHeaders.get(name);
				conn.setRequestProperty(name, value);
			}
			
			String message = msg.toString();
			logger.info("\n" + message);
			conn.setRequestProperty("Content-Length", String.valueOf(message.length()));
			conn.connect();
			OutputStream os = conn.getOutputStream();
			os.write(message.getBytes());
			os.close();

			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				logger.info(line);
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
	
	public ArrayList<Object> getAttachmentsWithKey(String key){
		return attachments.get(key);
	}
	
	public boolean isSWA() {
		return swa;
	}

	public void setSwa(boolean swa) {
		this.swa = swa;
	}
}
