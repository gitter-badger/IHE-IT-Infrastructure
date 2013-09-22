package com.gaduo.ihe.it_infrastructure.xds_transaction.service;

import java.io.InputStream;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import com.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import com.gaduo.ihe.utility.RSQCommon;
import com.gaduo.ihe.utility.xml.XMLPath;
//import com.gaduo.webservice.ServiceConsumer;
import com.gaduo.webservice.Soap;
import com.gaduo.webservice._interface.ISoap;

public class RegistryStoredQuery {
	// private NonBlockCallBack callback;
	public MessageContext context;
	public ISoap soap;
	public static Logger logger = Logger.getLogger(RegistryStoredQuery.class);

	public RegistryStoredQuery() {
		initial();
	}

	private void initial() {
		ClassLoader loader = getClass().getClassLoader();
		InputStream codesXml = loader.getResourceAsStream("codes.xml");
		InputStream webXml = loader.getResourceAsStream("web.xml");
		InputStream configXml = loader.getResourceAsStream("config.xml");
		RSQCommon.codes = new XMLPath(codesXml);
		RSQCommon.web = new XMLPath(webXml);
		RSQCommon.config = new XMLPath(configXml);
		RSQCommon.count = 0;

	}

	public OMElement QueryGenerator(OMElement source) {
		logger.info("Beging Transaction");
		initial();
		new RSQCommon(source);
		RSQCommon common = new RSQCommon();
		// ------ Loading Resource
		String filename = common.createTime();
		String QueryUUID = source.getFirstChildWithName(new QName("QueryUUID"))
				.getText();
		common.saveLog(filename, common.SOURCE + "_" + getQueryType(QueryUUID),
				source);

		QueryGenerator q = new QueryGenerator();
		OMElement request = q.execution(source);
		common.saveLog(filename, common.ITI_18_REQUEST + "_"
				+ getQueryType(QueryUUID), request);

		// -------submit ITI - 18 -------------------
		// ServiceConsumer serviceConsumer = null;
		if (!RSQCommon.registryUrl.equals("")) {
			logger.info("RegistryUrl : " + RSQCommon.registryUrl);
			// try {
			// serviceConsumer = new ServiceConsumer(RSQCommon.registryUrl,
			// request, "http://www.w3.org/2005/08/addressing",
			// "urn:ihe:iti:2007:RegistryStoredQuery", false, true);
			// } catch (IllegalStateException e) {
			// e.printStackTrace();
			// }
			// callback = serviceConsumer.getCallback();
			// IResponse res = new Response_ITI_18();
			// SOAPEnvelope envelope = (callback != null) ?
			// callback.getEnvelope() : null;
			// SOAPBody body = (envelope != null) ? envelope.getBody() : null;
			// OMElement response = (body != null) ? body.getFirstElement() :
			// null;
			// res.parser(callback.getEnvelope());

			boolean MTOM_XOP = false;
			soap = new Soap(RSQCommon.registryUrl,
					"http://www.w3.org/2005/08/addressing",
					"urn:ihe:iti:2007:RegistryStoredQuery", MTOM_XOP);
			context = soap.send(request);
			setContext(context);
			SOAPEnvelope envelope = (context != null) ? context.getEnvelope()
					: null;
			SOAPBody body = (envelope != null) ? envelope.getBody() : null;
			OMElement response = (body != null) ? body.getFirstElement() : null;
			common.saveLog(filename, common.ITI_18_RESPONSE + "_"
					+ getQueryType(QueryUUID), response);

			logger.trace(response);
			gc();
			return response;
		}
		gc();
		logger.error("Response is null");
		return null;
	}

	// public NonBlockCallBack getCallback() {
	// return callback;
	// }
	//
	// public void setCallback(NonBlockCallBack callback) {
	// this.callback = callback;
	// }

	private void gc() {
		Runtime r = Runtime.getRuntime();
		long memory = r.freeMemory();
		logger.trace("Free Memory : " + memory);
		r.gc();
	}

	private String getQueryType(String uuid) {
		if (uuid.equals(RegistryStoredQueryUUIDs.FIND_DOCUMENTS_UUID))
			return "FIND_DOCUMENTS";
		if (uuid.equals(RegistryStoredQueryUUIDs.FIND_FOLDERS_UUID))
			return "FIND_FOLDERS";
		if (uuid.equals(RegistryStoredQueryUUIDs.FIND_SUBMISSIONSETS_UUID))
			return "FIND_SUBMISSIONSETS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_ALL_UUID))
			return "GET_ALL";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_ASSOCIATIONS_UUID))
			return "GET_ASSOCIATIONS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_DOCUMENTS_AND_ASSOCIATIONS_UUID))
			return "GET_DOCUMENTS_AND_ASSOCIATIONS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_DOCUMENTS_UUID))
			return "GET_DOCUMENTS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_FOLDER_AND_CONTENTS_UUID))
			return "GET_FOLDER_AND_CONTENTS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_FOLDERS_FOR_DOCUMENT_UUID))
			return "GET_FOLDERS_FOR_DOCUMENT";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_FOLDERS_UUID))
			return "GET_FOLDERS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_RELATED_DOCUMENTS_UUID))
			return "GET_RELATED_DOCUMENTS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_SUBMISSIONSETS_AND_CONTENTS_UUID))
			return "GET_SUBMISSIONSETS_AND_CONTENTS";
		if (uuid.equals(RegistryStoredQueryUUIDs.GET_SUBMISSIONSETS_UUID))
			return "GET_SUBMISSIONSETS";
		return null;
	}

	public MessageContext getContext() {
		return context;
	}

	public void setContext(MessageContext context) {
		this.context = context;
	}
}
