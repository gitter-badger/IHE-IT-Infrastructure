package edu.tcu.gaduo.ihe.ebxml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

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
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExtrinsicObjectType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ObjectRefType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.RegistryUrlType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;



public class FindDocumentTest  {

	public static Logger logger = Logger.getLogger(FindDocumentTest.class);

	@Test
	public void testApp() {
		try{
			ICertificate cert = CertificateDetails.getInstance();
			cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks",
					"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
					"password");
			QueryType query = new QueryType();
			query.setRegistryUrl(new RegistryUrlType("http://203.64.84.214:801/axis2/services/xdsregistryb?wsdl"));
			query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_FOLDER_AND_CONTENTS_UUID));
			query.setReturnType(new ReturnTypeType("ObjectRef"));
			
			ParameterType p1 = new ParameterType(StoredQueryConstants.FOL_ENTRY_UUID);
			p1.addValues(new ValueType("'urn:uuid:61a029d4-37f4-4599-b532-527b293b9a60'")); //urn:uuid:1ded6009-68e4-4a4f-9cd5-48f6be1eaa7f
			query.addParameters(p1);
			
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			OMElement response = rsq.QueryGenerator(query);
			InputStream is = new ByteArrayInputStream(response.toString().trim().getBytes("utf-8"));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(aqs, System.out);
		}catch(JAXBException e){
			logger.error(e.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testAppMetadata() {
		try{
			Class<FindDocumentTest> clazz = FindDocumentTest.class;
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
