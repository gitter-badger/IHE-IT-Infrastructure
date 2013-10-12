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
public class AppTest extends TestCase {
	public static Logger logger = Logger.getLogger(AppTest.class);
	LoadTesDatatUtil load;

	public AppTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}

	public void testAddDocuments() throws IOException {
		AxiomUtil axiom = new AxiomUtil();
		Certificate cert = new Certificate();
		cert.setCertificate();
		OMElement source = load
				.loadTestDataToOMElement("metadata_template.xml");
		OMElement documents = axiom.createOMElement("Documents", null);

		String FileName = "1k.xml";
		String Description = FileName;
		byte[] array = load.loadTestDataToByteArray("addOneDocument/"
				+ FileName);
		String base64 = new String(Base64.encodeBase64(array));
		for (int i = 0; i < 2; i++) {
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
		source.addChild(documents);
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		OMElement response = pnr.MetadataGenerator(source);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		logger.info(response);
	}

	public void testSyncAddOneDocument() {
		AxiomUtil axiom = new AxiomUtil();
		final OMElement source = load
				.loadTestDataToOMElement("metadata_template.xml");
		OMElement documents = axiom.createOMElement("Documents", null);
		String FileName = "1k.xml";
		String Description = FileName;
		try {
			byte[] array = load.loadTestDataToByteArray("addOneDocument/"
					+ FileName);
			String base64 = new String(Base64.encodeBase64(array));
			for (int i = 0; i < 1; i++) {
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
			source.addChild(documents);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Runnable run = new Runnable() {
			public synchronized void run() {
				Certificate cert = new Certificate();
				cert.setCertificate();
				ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
				if (source != null) {
					OMElement response = pnr.MetadataGenerator(source);
					assertEquals(
							"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
							response.toString());
				} else {
					logger.info("Source is null");
				}
			}
		};
		Thread[] t = new Thread[10];
		for (int i = 0; i < 10; i++) {
			t[i] = new Thread(run);
			t[i].start();
		}
		for (int i = 0; i < 10; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
