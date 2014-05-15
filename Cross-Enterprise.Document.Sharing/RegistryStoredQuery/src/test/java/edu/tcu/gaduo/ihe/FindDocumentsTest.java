package edu.tcu.gaduo.ihe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ObjectRefType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.RegistryUrlType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ValueType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class FindDocumentsTest{
	public static Logger logger = Logger.getLogger(FindDocumentsTest.class);
	IAxiomUtil axiom ;

	@Before
	public void init() {
		axiom = AxiomUtil.getInstance();
	}

	@Test
	public void testApp() {
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");

		long timestamp = System.currentTimeMillis();
		
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMElement source = axiom.resourcesToOMElement("template/FindFolders.xml");
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		InputStream is = new ByteArrayInputStream(response.toString().getBytes());

		parse(is);	
		
		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
	}


	@Test
	public void testObject() throws JAXBException{

		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		
		QueryType query = new QueryType();
		query.setRegistryUrl(new RegistryUrlType("http://203.64.84.214:8010/axis2/services/xdsregistryb?wsdl"));
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.FIND_DOCUMENTS_UUID));
		query.setReturnType(new ReturnTypeType("ObjectRef"));
		ParameterType p1 = new ParameterType(StoredQueryConstants.DE_PATIENT_ID);
		p1.addValues(new ValueType("'637d7758405942c^^^&1.3.6.1.4.1.21367.2010.1.2.300&ISO'"));
		query.addParameters(p1);
		ParameterType p2 = new ParameterType(StoredQueryConstants.DE_STATUS);
		p2.addValues(new ValueType("'urn:oasis:names:tc:ebxml-regrep:StatusType:Approved'"));
		query.addParameters(p2);
		

//		JAXBContext jaxbContext = JAXBContext.newInstance(QueryType.class);
//		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//		jaxbMarshaller.marshal(query, System.out);
		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);
		
		System.out.println(response.toString());
		
		InputStream is = new ByteArrayInputStream(response.toString().getBytes());
		parse(is);		
	}
	
	private void parse(InputStream is){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			List<ObjectRefType> list = aqs.getRegistryObjectList().getObjectRefs();
			if(list == null) return;
			Iterator<ObjectRefType> iterator = list.iterator();
			while(iterator.hasNext()){
				ObjectRefType next = iterator.next();
				System.out.println(next.getId());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
//	public void testAppMutiArgs() {
//		ICertificate cert = CertificateDetails.getInstance();
//		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
//		for (int i = 0; i < 1; i++) {
//			long timestamp = System.currentTimeMillis();
//			OMElement source = load.loadTestDataToOMElement("template/FindDocuments_02.xml");
//			RegistryStoredQuery rsq = new RegistryStoredQuery();
//			OMElement response = rsq.QueryGenerator(source);
//			logger.info(response);
//			double time = System.currentTimeMillis() - timestamp;
//			System.out.println(time);
//		}
//	}
}
