package edu.tcu.gaduo.ihe.ebxml;

import static org.junit.Assert.*;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.AuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.DocumentAuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.DocumentType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.FolderType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.PatientInfoType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.MetadataGenerator_2_0;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;

/**
 * Unit test for simple App.
 */
public class XDSTest {
	public static Logger logger = Logger.getLogger(XDSTest.class);


	String sourcePatientId = "20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO";
	PatientInfoType pInfo ;
	String endpoint = "http://203.64.84.214:8020/axis2/services/xdsrepositoryb?wsdl";
	
	@Test
	public void testBase64(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		
	}
	
	public void testSubmitFile(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		document.setContent(new File("M:\\My_Code\\Dropbox\\IHE-Technical-Frameworks\\IntegratingTheHealthcareEnterprise\\README.md"));
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
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	public void testSubmitFolder(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		
		FolderType folder = new FolderType();
		folder.setTitle("FF01");
		folder.setDescription("FF01");
		folder.setSourcePatientId(sourcePatientId);
		folder.addFolderCodeList("Referrals");
		folder.addFolderCodeList("Referrals");
		md.addFolder(folder);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	public void testSubmitFolderContainDocument(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		FolderType folder = new FolderType();
		folder.setTitle("Folder 01");
		folder.setDescription("Folder 01");
		folder.setSourcePatientId(sourcePatientId);
		folder.addFolderCodeList("Referrals");
		folder.addFolderCodeList("Referrals");
		folder.addDocument(document);
		
		md.addFolder(folder);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	public void testExistingDocumentEntryAddToExistingFolder(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		document.setExistingDocumentId("DE01");
		
		FolderType folder = new FolderType();
		folder.setExistingFolderId("F01");
		folder.addDocument(document);		
		md.addFolder(folder);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	public void testExistingDocumentEntryAddToFolder(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		document.setExistingDocumentId("urn:uuid:00f65e94-aa41-4abb-a46a-4b7e2e5734fe");
		
		FolderType folder = new FolderType();
		folder.setTitle("FF01");
		folder.setDescription("FF01");
		folder.setSourcePatientId(sourcePatientId);
		folder.addFolderCodeList("Referrals");
		folder.addFolderCodeList("Referrals");
		folder.addDocument(document);
		
		md.addFolder(folder);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MetadataType.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(md, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	@Test
	public void testDocumentEntrySubmittedAddedToExistingFolder(){
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		document.setTitle("2014-05-15 01 58 12.jpg");
		document.setDescription("2014-05-15 01 58 12.jpg");
		document.setSourcePatientId(sourcePatientId);
		document.setContent(new File("M:\\My_Code\\Dropbox\\IHE-Technical-Frameworks\\IntegratingTheHealthcareEnterprise\\Cross-Enterprise.Document.Sharing\\ProvideAndRegisterDocumentSet\\src\\test\\resources\\test_data\\2014-05-15 01 58 12.png"));
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
		
		FolderType folder = new FolderType();
		folder.setExistingFolderId("urn:uuid:1ded6009-68e4-4a4f-9cd5-48f6be1eaa7f");
		folder.addDocument(document);	
		
		md.addFolder(folder);
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	public void testAPND() {  /** 新文件附加至已存在文件下*/
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		document.setExistingDocumentId("DE02");
		try {
			document.setOperation(DocumentType.APND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		md.addDocument(document);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}

	public void testRPLC() {
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		document.setExistingDocumentId("urn:uuid:00f65e94-aa41-4abb-a46a-4b7e2e5734fe");
		try {
			document.setOperation(DocumentType.RPLC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		md.addDocument(document);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MetadataType.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(md, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}

	public void testXFRM() {
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		document.setExistingDocumentId("DE02");
		try {
			document.setOperation(DocumentType.XFRM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		md.addDocument(document);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());

	}

	public void testXFRM_RPLC() {
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		document.setExistingDocumentId("DE02");
		try {
			document.setOperation(DocumentType.XFRM_RPLC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		md.addDocument(document);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());

	}

	public void testSigns() {
		MetadataType md = MetadataType.getInstance();
		md.setRepositoryUrl(endpoint);
		md.setSourceID("1.3.6.1.4.1.21367.2010.1.2");
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
		
		document.setExistingDocumentId("DE02");
		try {
			document.setOperation(DocumentType.SIGNS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		md.addDocument(document);
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(md, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		pnr.setRepositoryUrl(md.getRepositoryUrl());
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
}
