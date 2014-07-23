package edu.tcu.gaduo.ihe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ObjectRefType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;
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
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
	}

//	@Test
	public void testTemplate() {
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

//	@Test
	public void test200(){
		for(int i = 0; i < 200; i++){
			try {
				testObject();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testObject() throws JAXBException{

		QueryType query = new QueryType();
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.FIND_DOCUMENTS_UUID));
		query.setReturnType(new ReturnTypeType(StoredQueryConstants.OBJECTREF));
		
		ParameterType p1 = new ParameterType(StoredQueryConstants.DE_PATIENT_ID);
		p1.addValues(new ValueType("'637d7758405942c^^^&1.3.6.1.4.1.21367.2010.1.2.300&ISO'"));
		query.addParameters(p1);
		ParameterType p2 = new ParameterType(StoredQueryConstants.DE_STATUS);
		p2.addValues(new ValueType("'urn:oasis:names:tc:ebxml-regrep:StatusType:Approved'"));
		query.addParameters(p2);

		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);
		
logger.info("\n" + Thread.currentThread().getName() + " === ParsingBegin: === " + " === " + System.currentTimeMillis() + " === ");
		InputStream is = new ByteArrayInputStream(response.toString().getBytes());
		parse(is);		
logger.info("\n" + Thread.currentThread().getName() + " === ParsingEnd: === " + " === " + System.currentTimeMillis() + " === ");
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
	
}
