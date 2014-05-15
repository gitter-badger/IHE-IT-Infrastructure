package edu.tcu.gaduo.ihe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

/**
 * Unit test for simple App.
 */
public class SyncSubmitNewDocumentTest extends TestCase {
	public static Logger logger = Logger
			.getLogger(SyncSubmitNewDocumentTest.class);
	public static Map<String, OMElement> result = new HashMap<String, OMElement>();
	private IAxiomUtil axiom;

	public SyncSubmitNewDocumentTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		axiom = AxiomUtil.getInstance();
	}

	public void test01() {
		OneSubmit(5);
	}
//
//	public void test10() {
//		OneSubmit(10);
//	}
//
//	public void test20() {
//		OneSubmit(20);
//	}
//
//	public void test30() {
//		OneSubmit(30);
//	}
//
//	public void test40() {
//		OneSubmit(40);
//	}
//
//	public void test50() {
//		OneSubmit(50);
//	}
//
//	public void test60() {
//		OneSubmit(60);
//	}
//
//	public void test70() {
//		OneSubmit(70);
//	}
//
//	public void test80() {
//		OneSubmit(80);
//	}
//
//	public void test81() {
//		OneSubmit(81);
//	}
//
//	public void test82() {
//		OneSubmit(82);
//	}

	private void OneSubmit(int number) {
		Runnable run = new Runnable() {
			public void run() {
				String name = Thread.currentThread().getName();
				result.put(name, null);
				IAxiomUtil axiom = AxiomUtil.getInstance();
				final OMElement source = axiom.resourcesToOMElement("template/submit_new_document.xml");
				OMNamespace namespace = null;
				OMElement documents = axiom.createOMElement("Documents", namespace);
				String FileName = "0001k.xml";
				String Description = FileName;
				byte[] array = axiom.resourcesToByteArray("test_data/" + FileName);
				String base64 = new String(Base64.encodeBase64(array));
				for (int i = 0; i < 1; i++) {
					OMElement document = axiom.createOMElement("Document", namespace);
					OMElement title = axiom.createOMElement("Title", namespace);
					title.setText(FileName);
					OMElement description = axiom.createOMElement("Description", namespace);
					description.setText(Description);
					OMElement content = axiom.createOMElement("Content", namespace);
					content.setText(base64);
					document.addChild(title);
					document.addChild(description);
					document.addChild(content);
					documents.addChild(document);
				}
				source.addChild(documents);
				ICertificate cert = CertificateDetails.getInstance();
				cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
				ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
				if (source != null) {
					OMElement response = pnr.MetadataGenerator(source);
					result.put(name, response);
					return;
				} else {
					logger.info("Source is null");
					result.put(name, axiom.createOMElement("Element", "empty"));
					return;
				}
			}
		};
		Thread[] t = new Thread[number];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Thread(run);
		}
		for (int i = 0; i < t.length; i++) {
			t[i].start();
		}
		for (int i = 0; i < t.length; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Iterator<String> iterator = result.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			OMElement value = result.get(key);
			logger.info(key + "\t" + value);
			assertEquals(
					"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
					value.toString());
		}
	}

}
