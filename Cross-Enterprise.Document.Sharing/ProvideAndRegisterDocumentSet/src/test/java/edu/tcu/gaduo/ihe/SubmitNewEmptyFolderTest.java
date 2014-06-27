package edu.tcu.gaduo.ihe;

import static org.junit.Assert.*;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.MetadataGenerator_2_0;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.AuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.FolderType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.PatientInfoType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

public class SubmitNewEmptyFolderTest {
	public static Logger logger = Logger.getLogger(SubmitNewEmptyFolderTest.class);

	String sourcePatientId = "20140606^^^&1.3.6.1.4.1.21367.2005.3.7&ISO";
	PatientInfoType pInfo ;
	
	@Before
	public void init(){
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", 
				"openxds_2010/OpenXDS_2010_Truststore.jks", "password");
	}
	
	
	@Test
	public void testSubmitEmptyFolder(){
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		
		MetadataType md = pnr.getMetadataInstance();
		md.setContentTypeCode("Communication");

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
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
	}
	
	
	public String testSubmitFolder(){
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		
		MetadataType md = pnr.getMetadataInstance();
		md.setContentTypeCode("Communication");

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
		MetadataGenerator_2_0 mg = new MetadataGenerator_2_0();
		logger.info(mg.execution(md));
		
		OMElement response = pnr.MetadataGenerator(md);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		
		return folder.getId();
	}
}
