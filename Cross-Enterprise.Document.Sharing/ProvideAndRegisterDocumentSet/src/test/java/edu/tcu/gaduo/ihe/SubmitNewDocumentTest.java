package edu.tcu.gaduo.ihe;

import java.io.IOException;
import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility.test.LoadTesDatatUtil;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;

/**
 * Unit test for simple App.
 */
public class SubmitNewDocumentTest extends TestCase {
	public static Logger logger = Logger.getLogger(SubmitNewDocumentTest.class);
	LoadTesDatatUtil load;

	public SubmitNewDocumentTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}
	
	private void OneSubmit(int numberOfDocument) {
		ProvideAndRegisterDocumentSet.swa = !true;
		
		long timestamp = System.currentTimeMillis();
		IAxiomUtil axiom = AxiomUtil.getInstance();
		ICertificate cert = Certificate.getInstance();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = load.loadTestDataToOMElement("template/submit_new_document.xml");
		OMElement documents = axiom.createOMElement("Documents", null);

		String FileName = "debug.txt";
		String Description = FileName;
		byte[] array;
		try {
			array = load.loadTestDataToByteArray("test_data/" + FileName);
			String base64 = new String(Base64.encodeBase64(array));
			for (int i = 0; i < numberOfDocument; i++) {
				OMElement document = axiom.createOMElement("Document", null);
				OMElement title = axiom.createOMElement("Title", null);
				title.setText(FileName);
				OMElement description = axiom.createOMElement("Description", null);
				description.setText(Description);
				OMElement content = axiom.createOMElement("Content", null);
				content.setText(base64);

				document.addChild(title);
				document.addChild(description);
				document.addChild(content);
				documents.addChild(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		source.addChild(documents);
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
//		logger.info(source);
		OMElement response = pnr.MetadataGenerator(source);
		logger.info(response);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		

        double time = System.currentTimeMillis() - timestamp;
//        time /= 1000.0;
        logger.info("ITI-41 spend " + time + "second!");
	}
	

	public void test01() {
		for(int i = 0 ; i < 1; i++)
			OneSubmit(1);
	}
	
//	public void test02() {
//		OneSubmit(1);
//	}
//	
//	public void test03() {
//		OneSubmit(1);
//	}
//	
//	public void test04() {
//		OneSubmit(1);
//	}
//
//	public void test05() {
//		OneSubmit(1);
//	}
//	
//	public void test06() {
//		OneSubmit(1);
//	}
//	
//	public void test07() {
//		OneSubmit(1);
//	}
//	
//	public void test08() {
//		OneSubmit(1);
//	}
//	
//	public void test09() {
//		OneSubmit(1);
//	}
//	
//	public void test10() {
//		OneSubmit(1);
//	}
//	
//	public void test11() {
//		OneSubmit(1);
//	}
//	
//	public void test12() {
//		OneSubmit(1);
//	}
//	
//	public void test13() {
//		OneSubmit(1);
//	}
//	
//	public void test14() {
//		OneSubmit(1);
//	}
//
//	public void test15() {
//		OneSubmit(1);
//	}
//	
//	public void test16() {
//		OneSubmit(1);
//	}
//	
//	public void test17() {
//		OneSubmit(1);
//	}
//	
//	public void test18() {
//		OneSubmit(1);
//	}
//	
//	public void test19() {
//		OneSubmit(1);
//	}
//	
//	public void test20() {
//		OneSubmit(1);
//	}
//	
//	public void test21() {
//		OneSubmit(1);
//	}
//	
//	public void test22() {
//		OneSubmit(1);
//	}
//	
//	public void test23() {
//		OneSubmit(1);
//	}
//	
//	public void test24() {
//		OneSubmit(1);
//	}
//
//	public void test25() {
//		OneSubmit(1);
//	}
//	
//	public void test26() {
//		OneSubmit(1);
//	}
//	
//	public void test27() {
//		OneSubmit(1);
//	}
//	
//	public void test28() {
//		OneSubmit(1);
//	}
//	
//	public void test29() {
//		OneSubmit(1);
//	}
//	
//	public void test30() {
//		OneSubmit(1);
//	}
//	
	
	
}
