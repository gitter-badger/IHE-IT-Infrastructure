package edu.tcu.gaduo.ihe;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.AuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.DocumentAuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.DocumentType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.PatientInfoType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

public class SubmitNewDocumentTest {
	public static Logger logger = Logger.getLogger(SubmitNewDocumentTest.class);

	String sourcePatientId = "20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO";
	PatientInfoType pInfo ;

	@Before
	public void init(){
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", 
				"openxds_2010/OpenXDS_2010_Truststore.jks", "password");
	}
	
	
	@Test
	public void testMultiDocument() {
		multiDocument(1);
	}
	
	public void multiDocument(int numberOfDocument){
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(true);
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");

		MetadataType md = pnr.getMetadataInstance();
		md.setContentTypeCode("Communication"); //SubmissionSet 分類

		pInfo = new PatientInfoType();
		pInfo.setPid03("20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO");
		pInfo.setPid05("王大尾");
		pInfo.setPid07("19990801000000");
		pInfo.setPid08("M");
		md.setPatientInfo(pInfo);
		md.setSourcePatientId(sourcePatientId);
		
		AuthorType a = new AuthorType();
		a.setAuthorRole("行政");
		a.setAuthorPerson("Gaduo");
		a.setAuthorInstitution("台北醫學大學附設醫院");
		a.setAuthorSpecialty("行政");
		md.addAuthor(a);
		
		for(int i = 0; i < numberOfDocument; i++){
			DocumentType document = new DocumentType();
			document.setSoap(pnr.getSoap());
			document.setTitle("0050k.xml");
			document.setDescription("0050k.xml");
			document.setSourcePatientId(sourcePatientId);
			document.setContent("PGNkYT4NCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkJDQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5CQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkNCgkxMjM0NTY3ODkJDQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5DQoJMTIzNDU2Nzg5CQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQ0KCTEyMzQ1Njc4OQkNCgkxMjM0NTY3");
			document.setPatientInfo(pInfo);
			DocumentAuthorType author = new DocumentAuthorType();
			author.setAuthorRole("主治醫師");
			author.setAuthorPerson("黃柏榮醫師");
			author.setAuthorInstitution("台北醫學大學附設醫院");
			author.setAuthorSpecialty("乳房專科醫師");
			document.addAuthor(author);
			document.setClassCode("10160-0");
			document.setFormatCode("urn:ihe:pcc:apr:lab:2008");
			document.setHealthcareFacilityTypeCode("281PC2000N");
			document.setPracticeSettingCode("394802001");
			document.setTypeCode("34096-8");
			document.addConfidentialityCode("N");
			document.addConfidentialityCode("N");
			document.addEventCodeList("T-D4909");
			document.addEventCodeList("TRID1001");
			md.addDocument(document);
		}
		

		OMElement response = pnr.MetadataGenerator(md);
		if(!pnr.isSWA())
			assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		logger.info(Thread.currentThread().getName() + "====" + response);
	}
	
	
	@Test
	public void testBase64(){
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		MetadataType md = pnr.getMetadataInstance();
		md.setContentTypeCode("Communication"); //SubmissionSet 分類

		pInfo = new PatientInfoType();
		pInfo.setPid03("20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO");
		pInfo.setPid05("王大尾");
		pInfo.setPid07("19990801000000");
		pInfo.setPid08("M");
		md.setPatientInfo(pInfo);
		md.setSourcePatientId(sourcePatientId);
		
		AuthorType a = new AuthorType();
		a.setAuthorRole("行政");
		a.setAuthorPerson("Gaduo");
		a.setAuthorInstitution("台北醫學大學附設醫院");
		a.setAuthorSpecialty("行政");
		md.addAuthor(a);
		
		DocumentType document = new DocumentType();
		document.setTitle("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setDescription("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setSourcePatientId(sourcePatientId);
		document.setContent("VGhpcyBpcyBteSBkb2N1bWVudC4NCg0KSXQgaXMgZ3JlYXQh");
		document.setPatientInfo(pInfo);
		DocumentAuthorType author = new DocumentAuthorType();
		author.setAuthorRole("主治醫師");
		author.setAuthorPerson("黃柏榮醫師");
		author.setAuthorInstitution("台北醫學大學附設醫院");
		author.setAuthorSpecialty("乳房專科醫師");
		document.addAuthor(author);
		document.setClassCode("10160-0");
		document.setFormatCode("urn:ihe:pcc:apr:lab:2008");
		document.setHealthcareFacilityTypeCode("281PC2000N");
		document.setPracticeSettingCode("394802001");
		document.setTypeCode("34096-8");
		document.addConfidentialityCode("N");
		document.addConfidentialityCode("N");
		document.addEventCodeList("T-D4909");
		document.addEventCodeList("TRID1001");
		md.addDocument(document);
		
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	@Test
	public void testSubmitFile(){
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		MetadataType md = pnr.getMetadataInstance();
		md.setContentTypeCode("Communication");

		pInfo = new PatientInfoType();
		pInfo.setPid03("20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO");
		pInfo.setPid05("王大尾");
		pInfo.setPid07("19990801000000");
		pInfo.setPid08("M");
		md.setPatientInfo(pInfo);
		md.setSourcePatientId(sourcePatientId);
		
		AuthorType a = new AuthorType();
		a.setAuthorRole("行政");
		a.setAuthorPerson("Gaduo");
		a.setAuthorInstitution("台北醫學大學附設醫院");
		a.setAuthorSpecialty("行政");
		md.addAuthor(a);
		
		DocumentType document = new DocumentType();
		document.setTitle("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setDescription("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setSourcePatientId(sourcePatientId);
		document.setContent(new File("M:\\My_Code\\Dropbox\\IHE-Technical-Frameworks\\IntegratingTheHealthcareEnterprise\\pom.xml"));
		document.setPatientInfo(pInfo);
		DocumentAuthorType author = new DocumentAuthorType();
		author.setAuthorRole("主治醫師");
		author.setAuthorPerson("黃柏榮醫師");
		author.setAuthorInstitution("台北醫學大學附設醫院");
		author.setAuthorSpecialty("乳房專科醫師");
		document.addAuthor(author);
		document.setClassCode("10160-0");
		document.setFormatCode("urn:ihe:pcc:apr:lab:2008");
		document.setHealthcareFacilityTypeCode("281PC2000N");
		document.setPracticeSettingCode("394802001");
		document.setTypeCode("34096-8");
		document.addConfidentialityCode("N");
		document.addConfidentialityCode("N");
		document.addEventCodeList("T-D4909");
		document.addEventCodeList("TRID1001");
		md.addDocument(document);
		
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}

	

	public String testSubmitADocument(){
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		MetadataType md = pnr.getMetadataInstance();
		md.setContentTypeCode("Communication"); //SubmissionSet 分類

		pInfo = new PatientInfoType();
		pInfo.setPid03("20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO");
		pInfo.setPid05("王大尾");
		pInfo.setPid07("19990801000000");
		pInfo.setPid08("M");
		md.setPatientInfo(pInfo);
		md.setSourcePatientId(sourcePatientId);
		
		AuthorType a = new AuthorType();
		a.setAuthorRole("行政");
		a.setAuthorPerson("Gaduo");
		a.setAuthorInstitution("台北醫學大學附設醫院");
		a.setAuthorSpecialty("行政");
		md.addAuthor(a);
		
		DocumentType document = new DocumentType();
		document.setTitle("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setDescription("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setSourcePatientId(sourcePatientId);
		document.setContent("VGhpcyBpcyBteSBkb2N1bWVudC4NCg0KSXQgaXMgZ3JlYXQh");
		document.setPatientInfo(pInfo);
		DocumentAuthorType author = new DocumentAuthorType();
		author.setAuthorRole("主治醫師");
		author.setAuthorPerson("黃柏榮醫師");
		author.setAuthorInstitution("台北醫學大學附設醫院");
		author.setAuthorSpecialty("乳房專科醫師");
		document.addAuthor(author);
		document.setClassCode("10160-0");
		document.setFormatCode("urn:ihe:pcc:apr:lab:2008");
		document.setHealthcareFacilityTypeCode("281PC2000N");
		document.setPracticeSettingCode("394802001");
		document.setTypeCode("34096-8");
		document.addConfidentialityCode("N");
		document.addConfidentialityCode("N");
		document.addEventCodeList("T-D4909");
		document.addEventCodeList("TRID1001");
		md.addDocument(document);
		
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		return document.getId();
	}
	
	
}
