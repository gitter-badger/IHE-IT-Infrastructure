package edu.tcu.gaduo.ihe;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExtrinsicObjectType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryObjectListType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.QueryService;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class GetDocumentsTest extends TestCase {
	public static Logger logger = Logger.getLogger(GetDocumentsTest.class);
	private IAxiomUtil axiom;

	public GetDocumentsTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		axiom = AxiomUtil.getInstance();
	}

	public void testApp() {
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = axiom.resourcesToOMElement("template/GetDocuments.xml");
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		logger.info(response);
		

		try{
			InputStreamReader is = new InputStreamReader(IOUtils.toInputStream(response.toString()));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqr = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			
			
			QueryService<ExtrinsicObjectType> query = new QueryService<ExtrinsicObjectType>();
			RegistryObjectListType registryObjectList = aqr.getRegistryObjectList();
			List<ExtrinsicObjectType> extrinsicObjects = registryObjectList.getExtrinsicObjects();
			Iterator<ExtrinsicObjectType> eIterator = extrinsicObjects.iterator();
			while(eIterator.hasNext()){
				ExtrinsicObjectType extrinsicObject = eIterator.next();
				String fp = query.getValue(extrinsicObject, QueryService.QueryKey.FOLDER_PATIENT);
				logger.info(fp);
			}
			
			
			
			
		}catch(JAXBException e){
			logger.error(e.toString());
		}
			
			
//			
//			RegistryObjectListType registryObjectList = aqr.getRegistryObjectList();
//			
//			List<ExtrinsicObjectType> extrinsicObjects = registryObjectList.getExtrinsicObjects();
//			Iterator<ExtrinsicObjectType> eIterator = extrinsicObjects.iterator();
//			while(eIterator.hasNext()){
//				ExtrinsicObjectType extrinsicObject = eIterator.next();
//				extrinsicObject.getId();
//				extrinsicObject.getMimeType();
//				extrinsicObject.getStatus();	
//				{
//					NameType name = extrinsicObject.getName();
//					LocalizedStringType localizedString = name.getLocalizedString();
//					localizedString.getValue();
//				}
//				extrinsicObject.getDescription();
//				{
//					List<SlotType> slots = extrinsicObject.getSlots();
//					Iterator<SlotType> sIterator = slots.iterator();
//					while(sIterator.hasNext()){
//						SlotType slot = sIterator.next();
//						String name = slot.getName();
//						ValueListType valueList = slot.getValueList();
//						List<ValueType> values = valueList.getValues();
//						Iterator<ValueType> vIterator = values.iterator();
//						while(vIterator.hasNext()){
//							ValueType value = vIterator.next();
//							String text = value.getText();
//						}
//					}
//				}
//				
//				List<ClassificationType> classifications = extrinsicObject.getClassifications();
//				Iterator<ClassificationType> cIterator = classifications.iterator();
//				while(cIterator.hasNext()){
//					ClassificationType classification = cIterator.next();
//					classification.getId();
//					{
//						NameType name = classification.getName();
//						LocalizedStringType localizedString = name.getLocalizedString();
//						localizedString.getValue();
//					}
//					classification.getNodeRepresentation();
//					List<SlotType> slots = classification.getSlots();
//					Iterator<SlotType> sIterator = slots.iterator();
//					while(sIterator.hasNext()){
//						SlotType slot = sIterator.next();
//						String name = slot.getName();
//						ValueListType valueList = slot.getValueList();
//						List<ValueType> values = valueList.getValues();
//						Iterator<ValueType> vIterator = values.iterator();
//						while(vIterator.hasNext()){
//							ValueType value = vIterator.next();
//							String text = value.getText();
//						}
//					}
//				}
//			}
		
		
	}

//	public void testAppByLoadUUID() {
//		int tokenNumber = 50;
//		Response_ITI_18 parser = new Response_ITI_18();
//
//		OMElement uuids = load.loadTestDataToOMElement("template/uuid300.xml");
//		parser.parser(uuids);
//		TreeSet<String> list = (TreeSet<String>) parser.getList();
//		Iterator<String> iterator = list.descendingIterator();
//		int count = list.size();
//
//		long timestamp = System.currentTimeMillis();
//		ICertificate cert = Certificate.getInstance();
//		// cert.setCertificate();
//		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
//				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
//				"password");
//		for (int i = 0; i < count; i += tokenNumber) {
//			OMElement source = load.loadTestDataToOMElement("template/GetDocuments_02.xml");
//			IAxiomUtil axiom = AxiomUtil.getInstance();
//			OMElement Parameter = axiom.createOMElement("Parameter", null);
//			Parameter.addAttribute("name", "$XDSDocumentEntryEntryUUID", null);
//			for(int j = 0; j < tokenNumber && iterator.hasNext(); j++){
//				String uuid = iterator.next();
//				OMElement Value = axiom.createOMElement("Value", null);
//				Value.setText("'" + uuid + "'");
//				Parameter.addChild(Value);
//			}
//			source.addChild(Parameter);
//			RegistryStoredQuery rsq = new RegistryStoredQuery();
//			OMElement response = rsq.QueryGenerator(source);
//		}
//
//		double time = System.currentTimeMillis() - timestamp;
//		System.out.println(time);
//	}
}
