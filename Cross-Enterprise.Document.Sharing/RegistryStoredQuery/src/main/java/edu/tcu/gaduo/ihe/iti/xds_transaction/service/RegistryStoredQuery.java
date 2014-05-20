package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.constants.atna.EventOutcomeIndicator;
import edu.tcu.gaduo.ihe.iti.atna_transaction.service.RecordAuditEvent;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_18_110112;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;
import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;
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
	private EventOutcomeIndicator eventOutcomeIndicator;
	private QueryType query;
	
	private void initial() {
		c = new RSQCommon();
		filename = createTime();
	}

	public OMElement QueryGenerator(OMElement source) {
		logger.info("Beging Transaction");
		initial();
		ByteArrayInputStream is = new ByteArrayInputStream(source.toString().getBytes());
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(QueryType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			QueryType query = (QueryType) jaxbUnmarshaller.unmarshal(is);
			
			queryType = getQueryType(query.getQueryUUID().getValue());
			c.saveLog(filename, ((RSQCommon)c).SOURCE + "_" + queryType, source);
			
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
		this.query = query;
		ClassLoader loader = getClass().getClassLoader();
		Properties prop = new Properties();
		InputStream is = loader.getResourceAsStream("RegistryStoredQuery.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		registryUrl = prop.getProperty("registry.endpoint");
		
		queryType = getQueryType(query.getQueryUUID().getValue());
		
		// -------submit ITI - 18 -------------------
		if(!registryUrl.equals("")){
			QueryGenerator q = new QueryGenerator();
			request = q.execution(query);

			logger.debug(request);
			if (request != null) {
				response = send(request);
				if (response != null) {		
					logger.debug(response);				
				}
			}
		}
		return response;
	}
	

	@Override
	public OMElement send(OMElement request) {
		c.saveLog(filename, ((RSQCommon) c).ITI_18_REQUEST + "_" + queryType, request);

		ISoap soap = new ServiceConsumer(registryUrl, ACTION);
		setContext(soap.send(request));
		
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		c.saveLog(filename, ((RSQCommon) c).ITI_18_RESPONSE + "_" + queryType, response);
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
// TODO 未完成
		if (response == null) {
			this.eventOutcomeIndicator = EventOutcomeIndicator.MajorFailure;
		} else if (assertEquals(
				response,
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>")) {
			this.eventOutcomeIndicator = EventOutcomeIndicator.Success;
		} else {
			this.eventOutcomeIndicator = EventOutcomeIndicator.SeriousFaailure;
		}
		
		String endpoint = registryUrl;
		String queryUUID = query.getQueryUUID().getValue();
		EventOutcomeIndicator eventOutcomeIndicator = this.eventOutcomeIndicator;
		
		
		ISysLoger loger = new SysLogerITI_18_110112();
		((SysLogerITI_18_110112) loger).setEndpoint(endpoint);
		

		List<ParameterType> list = query.getParameters();
		Iterator<ParameterType> iterator = list.iterator();
		while(iterator.hasNext()){
			ParameterType p = iterator.next();
			String name = p.getName();
			if(name.equals(StoredQueryConstants.DE_PATIENT_ID)){
				List<ValueType> patientId = p.getValues();
				Iterator<ValueType> pIterator = patientId.iterator();
				while(pIterator.hasNext()){
					ValueType pId = pIterator.next();
					((SysLogerITI_18_110112) loger).addPatientId(pId.getValue());
				}
			}
			if(name.equals(StoredQueryConstants.FOL_PATIENT_ID)){
				List<ValueType> patientId = p.getValues();
				Iterator<ValueType> pIterator = patientId.iterator();
				while(pIterator.hasNext()){
					ValueType pId = pIterator.next();
					((SysLogerITI_18_110112) loger).addPatientId(pId.getValue());
				}
			}
			if(name.equals(StoredQueryConstants.SS_PATIENT_ID)){
				List<ValueType> patientId = p.getValues();
				Iterator<ValueType> pIterator = patientId.iterator();
				while(pIterator.hasNext()){
					ValueType pId = pIterator.next();
					((SysLogerITI_18_110112) loger).addPatientId(pId.getValue());
				}
			}
		}
		
		((SysLogerITI_18_110112) loger).setEventOutcomeIndicator(eventOutcomeIndicator);
		((SysLogerITI_18_110112) loger).setQueryUUID(queryUUID);
		((SysLogerITI_18_110112) loger).setRequest(request);
		/** --- Source --- */
		((SysLogerITI_18_110112) loger).setReplyTo("http://www.w3.org/2005/08/addressing/anonymous");
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			((SysLogerITI_18_110112) loger).setLocalIPAddress(addr.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		OMElement element = loger.build();
		logger.info(element);
		
		RecordAuditEvent rae = new RecordAuditEvent();
		rae.AuditGenerator(element);
		
	}
}
