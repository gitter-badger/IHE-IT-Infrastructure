package edu.tcu.gaduo.ihe.ebxml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.ihe.ProvideAndRegisterDocumentSetRequestType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.RegistryUrlType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ValueType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;



public class EbXMLTest  {

	public static Logger logger = Logger.getLogger(EbXMLTest.class);

	@Test
	public void testApp() {
		try{
//			Class<EbXMLTest> clazz = EbXMLTest.class;
//			ClassLoader loader = clazz.getClassLoader();
//			InputStream is = loader.getResourceAsStream("response.xml");
			
			QueryType query = new QueryType();
			query.setRegistryUrl(new RegistryUrlType("http://203.64.84.214:8010/axis2/services/xdsregistryb?wsdl"));
			query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_FOLDER_AND_CONTENTS_UUID));
			query.setReturnType(new ReturnTypeType("LeafClass"));
			ParameterType p1 = new ParameterType(StoredQueryConstants.FOL_ENTRY_UUID);
			p1.addValues(new ValueType("'urn:uuid:61a029d4-37f4-4599-b532-527b293b9a60'")); //urn:uuid:1ded6009-68e4-4a4f-9cd5-48f6be1eaa7f
			query.addParameters(p1);
			
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			OMElement response = rsq.QueryGenerator(query);
			InputStream is = new ByteArrayInputStream(response.toString().trim().getBytes("utf-8"));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(aqs, System.out);
		}catch(JAXBException e){
			logger.error(e.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAppMetadata() {
		try{
			Class<EbXMLTest> clazz = EbXMLTest.class;
			ClassLoader loader = clazz.getClassLoader();
			InputStream is = loader.getResourceAsStream("metadata.xml");
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ProvideAndRegisterDocumentSetRequestType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ProvideAndRegisterDocumentSetRequestType pnr = (ProvideAndRegisterDocumentSetRequestType) jaxbUnmarshaller.unmarshal(is);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(pnr, System.out);
		}catch(JAXBException e){
			logger.error(e.toString());
		}
	}
}
