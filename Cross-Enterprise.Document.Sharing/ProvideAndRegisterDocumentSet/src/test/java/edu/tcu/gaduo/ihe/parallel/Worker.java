package edu.tcu.gaduo.ihe.parallel;

import java.util.concurrent.CountDownLatch;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.AuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.DocumentAuthorType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.DocumentType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.PatientInfoType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

public class Worker implements Runnable {
	public static Logger logger = Logger.getLogger(Worker.class);
	private CountDownLatch latch;
	private int index;
	private String content;

	public Worker(int index, String content, CountDownLatch latch) {
		this.index = index;
		this.content = content;
		this.latch = latch;
	}

	public void run() {
		OneSubmit(1, content);
		latch.countDown();
	}
	
	private String sourcePatientId = "20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO";
	private PatientInfoType pInfo ;
	private String endpoint = "http://203.64.84.214:8020/axis2/services/xdsrepositoryb?wsdl";
	
	private void OneSubmit(int numberOfDocument, String content) {

		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet(false);
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");

		MetadataType md = MetadataType.getInstance();
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
		document.setSoap(pnr.getSoap());
		document.setTitle("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setDescription("醫療影像及報告_1010221_V101.0_Signed.xml");
		document.setSourcePatientId(sourcePatientId);
		document.setContent(content);
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
		logger.info(index + "===\t" + Thread.currentThread().getName() + "====" + response);
	}

}