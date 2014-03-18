package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.utility.RSQCommon;
import edu.tcu.gaduo.webservice.ServiceConsumer;
import edu.tcu.gaduo.webservice._interface.ISoap;

public class RegistryStoredQuery extends Transaction {
	public ISoap soap;
	private String filename;
	private String queryType;
	public static Logger logger = Logger.getLogger(RegistryStoredQuery.class);

	public RegistryStoredQuery() {
		initial();
	}

	private void initial() {
		c = new RSQCommon();
		RSQCommon.count = 0;
	}

	public OMElement QueryGenerator(OMElement source) {
		logger.info("Beging Transaction");
		initial();
		new RSQCommon(source);
		// ------ Loading Resource
		filename = c.createTime();
		String QueryUUID = source.getFirstChildWithName(new QName("QueryUUID"))
				.getText();
		queryType = getQueryType(QueryUUID);
		c.saveLog(filename, ((RSQCommon)c).SOURCE + "_" + queryType, source);

		// -------submit ITI - 18 -------------------
		if (!RSQCommon.registryUrl.equals("")) {
			QueryGenerator q = new QueryGenerator();
			request = q.execution(source);
			logger.debug(request);
			if (request != null) {
				response = send(request);
				if (response != null) {		
					logger.debug(response);
					return response;
				}
			}
		}
		gc();
		logger.error("Response is null");
		return null;
	}

	@Override
	public OMElement send(OMElement request) {
		c.saveLog(filename, ((RSQCommon) c).ITI_18_REQUEST + "_" + queryType, request);

		ISoap soap = new ServiceConsumer(RSQCommon.registryUrl,
				"urn:ihe:iti:2007:RegistryStoredQuery");
		setContext(soap.send(request));
		
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		c.saveLog(filename, ((RSQCommon) c).ITI_18_RESPONSE + "_" + queryType,
				response);
		gc();
		return response;
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
}
