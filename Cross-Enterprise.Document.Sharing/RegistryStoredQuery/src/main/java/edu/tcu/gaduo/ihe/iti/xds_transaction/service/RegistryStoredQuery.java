package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.RegistryUrlType;
import edu.tcu.gaduo.ihe.utility.Common;
import edu.tcu.gaduo.ihe.utility.RSQCommon;
import edu.tcu.gaduo.ihe.utility.ws.ServiceConsumer;
import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;

public class RegistryStoredQuery extends Transaction {
	public ISoap soap;
	private String filename;
	private String queryType;
	public static Logger logger = Logger.getLogger(RegistryStoredQuery.class);

	private final String ACTION = "urn:ihe:iti:2007:RegistryStoredQuery";
	private String registryUrl = null;
	
	
	public RegistryStoredQuery() {
		initial();
	}

	private void initial() {
		c = new Common();
		filename = createTime();
	}

	public OMElement QueryGenerator(OMElement source) {
		logger.info("Beging Transaction");
		initial();
		// ------ Loading Resource
		String QueryUUID = source.getFirstChildWithName(new QName("QueryUUID")).getText();
		queryType = getQueryType(QueryUUID);
//		c.saveLog(filename, ((RSQCommon)c).SOURCE + "_" + queryType, source);
		ByteArrayInputStream is = new ByteArrayInputStream(source.toString().getBytes());
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(QueryType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			QueryType query = (QueryType) jaxbUnmarshaller.unmarshal(is);
			response = QueryGenerator(query);
			return response;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		gc();
		logger.error("Response is null");
		return null;
	}
	
	public OMElement QueryGenerator(QueryType query){
		initial();

		RegistryUrlType endpoint = query.getRegistryUrl();
		registryUrl = endpoint.getValue();
		registryUrl = registryUrl.trim();
		// -------submit ITI - 18 -------------------
		if(!registryUrl.equals("")){
			QueryGenerator q = new QueryGenerator();
			request = q.execution(query);
//			logger.debug(request);
			if (request != null) {
				response = send(request);
				if (response != null) {		
//					logger.debug(response);
					return response;
				}
			}
		}
		return null;
	}
	

	@Override
	public OMElement send(OMElement request) {
//		c.saveLog(filename, ((RSQCommon) c).ITI_18_REQUEST + "_" + queryType, request);

		ISoap soap = new ServiceConsumer(registryUrl, ACTION);
		setContext(soap.send(request));
		
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
//		c.saveLog(filename, ((RSQCommon) c).ITI_18_RESPONSE + "_" + queryType, response);
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

	@Override
	public void auditLog() {
		
	}
}
