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
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryPackageType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.SlotType;
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

public class FindFoldersTest {
	public static Logger logger = Logger.getLogger(FindFoldersTest.class);
	private IAxiomUtil axiom;

	@Before
	public void setUp() {
		axiom = AxiomUtil.getInstance();
	}
	
//	@Test
	public void testApp() {
		long timestamp = System.currentTimeMillis();
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = axiom.resourcesToOMElement("template/FindFolders.xml");

		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		logger.info(response);
		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
	}	
	
	@Test
	public void testObject(){
		QueryType query = new QueryType();
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.FIND_FOLDERS_UUID));
		query.setReturnType(new ReturnTypeType("LeafClass"));
		ParameterType p1 = new ParameterType(StoredQueryConstants.FOL_PATIENT_ID);
		p1.addValues(new ValueType("'20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO'"));
		query.addParameters(p1);
		ParameterType p2 = new ParameterType(StoredQueryConstants.FOL_STATUS);
		p2.addValues(new ValueType("'urn:oasis:names:tc:ebxml-regrep:StatusType:Approved'"));
		query.addParameters(p2);
		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);
		
		InputStream is = new ByteArrayInputStream(response.toString().getBytes());
		parse(is);		
	}
	
	private void parse(InputStream is){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			List<RegistryPackageType> list = aqs.getRegistryObjectList().getRegistryPackages();
			if(list == null) return;
			Iterator<RegistryPackageType> iterator = list.iterator();
			while(iterator.hasNext()){
				RegistryPackageType next = iterator.next();
				System.out.println(next.getName().getLocalizedString().getValue());
				List<SlotType> sList = next.getSlots();
				Iterator<SlotType> sIterator = sList.iterator();
				while(sIterator.hasNext()){
					SlotType sNext = sIterator.next();
					List<edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType> vList = sNext.getValueList().getValues();
					Iterator<edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType> vIterator = vList.iterator();
					while(vIterator.hasNext()){
						edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType vNext = vIterator.next();
						System.out.println(sNext.getName() + ":" + vNext.getText());
					}
				}
				
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
//	
//	public void testAppMutiArgs() {
//		long timestamp = System.currentTimeMillis();
//		ICertificate cert = CertificateDetails.getInstance();
//		// cert.setCertificate();
//		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
//				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
//				"password");
////		OMElement source = load.loadTestDataToOMElement("template/FindFolders_02.xml");
////
////		RegistryStoredQuery rsq = new RegistryStoredQuery();
////		OMElement response = rsq.QueryGenerator(source);
////		logger.info(response);
////		double time = System.currentTimeMillis() - timestamp;
////		System.out.println(time);
//	}
}
