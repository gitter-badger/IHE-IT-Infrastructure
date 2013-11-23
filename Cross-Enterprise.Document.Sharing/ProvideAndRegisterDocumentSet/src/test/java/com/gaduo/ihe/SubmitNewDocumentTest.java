package com.gaduo.ihe;

import java.io.IOException;
import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.gaduo.ihe.it_infrastructure.xds_transaction.service.ProvideAndRegisterDocumentSet;
import com.gaduo.ihe.security.Certificate;
import com.gaduo.ihe.utility.LoadTesDatatUtil;
import com.gaduo.ihe.utility.AxiomUtil;

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

	public void test01() {
		OneSubmit(1);
	}

	public void test10() {
		OneSubmit(10);
	}

	public void test20() {
		OneSubmit(20);
	}

	public void test30() {
		OneSubmit(30);
	}


	private void OneSubmit(int numberOfDocument) {
		AxiomUtil axiom = new AxiomUtil();
		Certificate cert = new Certificate();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		OMElement source = load
				.loadTestDataToOMElement("template/submit_new_document.xml");
		OMElement documents = axiom.createOMElement("Documents", null);

		String FileName = "0001k.xml";
		String Description = FileName;
		byte[] array;
		try {
			array = load.loadTestDataToByteArray("test_data/" + FileName);
			String base64 = new String(Base64.encodeBase64(array));
			for (int i = 0; i < numberOfDocument; i++) {
				OMElement document = axiom.createOMElement("Document", null);
				OMElement title = axiom.createOMElement("Title", null);
				title.setText(FileName);
				OMElement description = axiom.createOMElement("Description",
						null);
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
		OMElement response = pnr.MetadataGenerator(source);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		logger.info(response);
		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
