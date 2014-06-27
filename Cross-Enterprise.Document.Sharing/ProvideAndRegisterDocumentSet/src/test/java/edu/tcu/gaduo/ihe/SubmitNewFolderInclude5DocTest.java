package edu.tcu.gaduo.ihe;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.AuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.DocumentAuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.DocumentType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.FolderType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.PatientInfoType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

public class SubmitNewFolderInclude5DocTest {

	public static Logger logger = Logger.getLogger(SubmitNewFolderInclude5DocTest.class);
	
	String sourcePatientId = "20140606^^^&1.3.6.1.4.1.21367.2005.3.7&ISO";
	PatientInfoType pInfo ;
	
	@Before
	public void init(){
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", 
				"openxds_2010/OpenXDS_2010_Truststore.jks", "password");
	}
	
	
	@Test
	public void test() {
		Submit();
	}
	
	public void Submit(){

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");

		MetadataType md = MetadataType.getInstance();
		md.setContentTypeCode("Communication"); //SubmissionSet 分類

		pInfo = new PatientInfoType();
		pInfo.setPid03("20140606^^^&1.3.6.1.4.1.21367.2005.3.7&ISO");
		pInfo.setPid05("王大尾");
		pInfo.setPid07("19990801000000");
		pInfo.setPid08("M");
		md.setPatientInfo(pInfo);
		md.setSourcePatientId(sourcePatientId);
		
		AuthorType a = new AuthorType();
		a.addAuthorRole("行政");
		a.addAuthorPerson("Gaduo");
		a.addAuthorInstitution("台北醫學大學附設醫院");
		a.addAuthorSpecialty("行政");
		md.addAuthor(a);
		

		FolderType folder = new FolderType();
		folder.setTitle("FF01");
		folder.setDescription("FF01");
		folder.setSourcePatientId(sourcePatientId);
		folder.addFolderCodeList("Referrals");
		folder.addFolderCodeList("Referrals");
		md.addFolder(folder);
		
		Class<SubmitNewFolderInclude5DocTest> clazz = SubmitNewFolderInclude5DocTest.class;
		ClassLoader loader = clazz.getClassLoader();
		{
			String title = "出院病摘_1010222_V101.0_Signed.xml";
			InputStream is = loader.getResourceAsStream("公告範例_已簽章/" + title);

			DocumentType document = new DocumentType();
			document.setSoap(pnr.getSoap());
			document.setTitle(title);
			document.setDescription(title);
			document.setSourcePatientId(sourcePatientId);
			document.setContent(is);
			document.setPatientInfo(pInfo);
			DocumentAuthorType author = new DocumentAuthorType();
			author.addAuthorRole("主治醫師");
			author.addAuthorPerson("黃柏榮醫師");
			author.addAuthorInstitution("台北醫學大學附設醫院");
			author.addAuthorSpecialty("乳房專科醫師");
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
			folder.addDocument(document);
		}
		{
			String title = "血液檢驗_1010222_V101.0_Signed.xml";
			InputStream is = loader.getResourceAsStream("公告範例_已簽章/" + title);

			DocumentType document = new DocumentType();
			document.setSoap(pnr.getSoap());
			document.setTitle(title);
			document.setDescription(title);
			document.setSourcePatientId(sourcePatientId);
			document.setContent(is);
			document.setPatientInfo(pInfo);
			DocumentAuthorType author = new DocumentAuthorType();
			author.addAuthorRole("主治醫師");
			author.addAuthorPerson("黃柏榮醫師");
			author.addAuthorInstitution("台北醫學大學附設醫院");
			author.addAuthorSpecialty("乳房專科醫師");
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
			folder.addDocument(document);
		}

		{
			String title = "門診用藥紀錄_1020814_V101.0_Signed.xml";
			InputStream is = loader.getResourceAsStream("公告範例_已簽章/" + title);

			DocumentType document = new DocumentType();
			document.setSoap(pnr.getSoap());
			document.setTitle(title);
			document.setDescription(title);
			document.setSourcePatientId(sourcePatientId);
			document.setContent(is);
			document.setPatientInfo(pInfo);
			DocumentAuthorType author = new DocumentAuthorType();
			author.addAuthorRole("主治醫師");
			author.addAuthorPerson("黃柏榮醫師");
			author.addAuthorInstitution("台北醫學大學附設醫院");
			author.addAuthorSpecialty("乳房專科醫師");
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
			folder.addDocument(document);
		}

		{
			String title = "門診病歷單_1020821_121_Signed.xml";
			InputStream is = loader.getResourceAsStream("公告範例_已簽章/" + title);

			DocumentType document = new DocumentType();
			document.setSoap(pnr.getSoap());
			document.setTitle(title);
			document.setDescription(title);
			document.setSourcePatientId(sourcePatientId);
			document.setContent(is);
			document.setPatientInfo(pInfo);
			DocumentAuthorType author = new DocumentAuthorType();
			author.addAuthorRole("主治醫師");
			author.addAuthorPerson("黃柏榮醫師");
			author.addAuthorInstitution("台北醫學大學附設醫院");
			author.addAuthorSpecialty("乳房專科醫師");
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
			folder.addDocument(document);
		}

		{
			String title = "醫療影像及報告_1010221_V101.0_Signed.xml";
			InputStream is = loader.getResourceAsStream("公告範例_已簽章/" + title);

			DocumentType document = new DocumentType();
			document.setSoap(pnr.getSoap());
			document.setTitle(title);
			document.setDescription(title);
			document.setSourcePatientId(sourcePatientId);
			document.setContent(is);
			document.setPatientInfo(pInfo);
			DocumentAuthorType author = new DocumentAuthorType();
			author.addAuthorRole("主治醫師");
			author.addAuthorPerson("黃柏榮醫師");
			author.addAuthorInstitution("台北醫學大學附設醫院");
			author.addAuthorSpecialty("乳房專科醫師");
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
			folder.addDocument(document);
		}
		
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		logger.info(Thread.currentThread().getName() + "====" + response);
	}

}
