package com.gaduo.ihe.it_infrastructure.xds_transaction.service;

import java.io.InputStream;
import java.util.HashSet;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.PnRCommon;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility._interface.ICommon;
import com.gaduo.ihe.utility.xml.XMLPath;
import com.gaduo.webservice.Soap;
import com.gaduo.webservice._interface.ISoap;

public class ProvideAndRegisterDocumentSet {
	public static ISoap soap;
	public MessageContext context;
	// private NonBlockCallBack callback;
	public OMElement request;

	public static Logger logger = Logger
			.getLogger(ProvideAndRegisterDocumentSet.class);

	public ProvideAndRegisterDocumentSet() {
		initial();
	}

	private void initial() {
		ClassLoader loader = getClass().getClassLoader();
		InputStream codesXml = loader.getResourceAsStream("codes.xml");
		InputStream webXml = loader.getResourceAsStream("web.xml");
		InputStream configXml = loader.getResourceAsStream("config.xml");
		PnRCommon.codes = new XMLPath(codesXml);
		PnRCommon.web = new XMLPath(webXml);
		PnRCommon.config = new XMLPath(configXml);
		PnRCommon.ObjectRef = new HashSet<String>();
		PnRCommon.count = 0;
	}

	// public String MetadataGenerator02(String base64) {
	// String string = "";
	// /*try {
	// byte[] bytes = Base64.decodeBase64(base64.getBytes());
	// InputStream stream = new ByteArrayInputStream(bytes);
	// XMLInputFactory factory = XMLInputFactory.newInstance();
	// XMLStreamReader parser = factory.createXMLStreamReader(stream);
	// StAXOMBuilder builder = new StAXOMBuilder(parser);
	// OMElement doc = builder.getDocumentElement();
	// doc.build();
	// OMElement result = MetadataGenerator(doc);
	// Codec codec = new Codec();
	// string = codec.encodeBase64(result.toString().getBytes());
	// } catch (XMLStreamException e) {
	// return e.toString();
	// }*/
	// return string;
	// }

	public OMElement MetadataGenerator(OMElement source) {
		logger.info("Beging Transaction");
		source.build();
		ICommon common = new PnRCommon();
		IAxiomUtil axiom = new AxiomUtil();
		initial();
		new PnRCommon(source);
		String Operations = axiom.getValueOfType("Operations", source);
		// ------ Loading Resource
		String filename = new PnRCommon().createTime();
		// --
		common.saveLog(filename,
				((PnRCommon) common).SOURCE + "_" + Operations, source);
		MessageContext context = MessageContext.getCurrentMessageContext();
		if (context != null) {
			SOAPEnvelope envelope = context.getEnvelope();
			common.saveLog(filename, "_ITI-41_envelope" + "_" + Operations,
					envelope);
		}
		// -------submit ITI - 41 -------------------
		if (!PnRCommon.repositoryUrl.equals("")) {
			boolean MTOM_XOP = true;
			soap = new Soap(PnRCommon.repositoryUrl,
					"http://www.w3.org/2005/08/addressing",
					"urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b",
					MTOM_XOP);

			MetadataGenerator m = new MetadataGenerator();
			/* Provide And Register Document Set -b */
			request = m.execution(source);
			common.saveLog(filename, ((PnRCommon) common).ITI_41_REQUEST + "_"
					+ Operations, request);
			context = soap.send(request);
			setContext(context);
			SOAPEnvelope envelope = (context != null) ? context.getEnvelope()
					: null;
			SOAPBody body = (envelope != null) ? envelope.getBody() : null;
			OMElement response = (body != null) ? body.getFirstElement() : null;
			// ----------------------------------------------------------///
			// ServiceConsumer serviceConsumer = new
			// ServiceConsumer(Common.repositoryUrl,
			// request, "http://www.w3.org/2005/08/addressing",
			// "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b", true,
			// true);
			// callback = serviceConsumer.getCallback();
			// Response res = new Response_ITI_41();
			// res.parser(context);
			// OMElement response = ((Response_ITI_41)res).getResponse();
			// common.saveLog(filename, common.ITI_41_RESPONSE + "_" +
			// Operations, response);
			// if(response == null) return null;
			// String status = response.getAttributeValue(new QName("status"));
			// if (status.equals(Namespace.SUCCESS.getNamespace())) {
			// response.addChild(m.getUniqeId());
			// }
			common.saveLog(filename, ((PnRCommon) common).ITI_41_RESPONSE,
					response);
			gc();
			logger.info(response);
			return response;
		}
		gc();
		logger.error("Response is null");
		return null;
		// -------submit ITI - 41 -------------------
	}

	// public NonBlockCallBack getCallback() {
	// return callback;
	// }
	//
	// void setCallback(NonBlockCallBack callback) {
	// this.callback = callback;
	// }

	private void gc() {
		Runtime r = Runtime.getRuntime();
		long memory = r.freeMemory();
		logger.info("Free Memory : " + memory);
		r.gc();
	}

	public MessageContext getContext() {
		return context;
	}

	public void setContext(MessageContext context) {
		this.context = context;
	}

	void isSecure() {
		String keystore = PnRCommon.root_dir
				+ "Certs\\OpenXDS_2010_Keystore.p12";
		String truststore = PnRCommon.root_dir
				+ "Certs\\OpenXDS_2010_Truststore.jks";
		logger.info(truststore);
		if (PnRCommon.repositoryUrl.contains("https")) {
			System.setProperty("javax.net.ssl.keyStore", keystore);
			System.setProperty("javax.net.ssl.keyStorePassword", "password");
			System.setProperty("javax.net.ssl.trustStore", truststore);
			System.setProperty("javax.net.ssl.trustStorePassword", "password");// "123456"
			System.setProperty("java.protocol.handler.pkgs",
					"com.sun.net.ssl.internal.www.protocol");
		}
	}

}
