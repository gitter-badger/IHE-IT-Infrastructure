package edu.tcu.gaduo.ihe.parallel;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility.test.LoadTesDatatUtil;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;

public class Worker implements Runnable {
	public static Logger logger = Logger.getLogger(Worker.class);
	private CountDownLatch latch;
	private int index;

	public Worker(int index, CountDownLatch latch) {
		this.index = index;
		this.latch = latch;
	}

	public void run() {
		OneSubmit(1, "0010k (" + (index % 10) + ").xml");
		latch.countDown();
	}

	private void OneSubmit(int numberOfDocument, String FileName) {
		LoadTesDatatUtil load = new LoadTesDatatUtil();
		IAxiomUtil axiom = AxiomUtil.getInstance();
		ICertificate cert = CertificateDetails.getInstance();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = load.loadTestDataToOMElement("template/submit_new_document.xml");
		OMNamespace namespace = null;
		OMElement documents = axiom.createOMElement("Documents", namespace);

		logger.info(FileName);
//		String Description = FileName;
//		byte[] array;
//		try {
//			array = load.loadTestDataToByteArray("parallel_test_data/" + FileName);
//			String base64 = new String(Base64.encodeBase64(array));
//			for (int i = 0; i < numberOfDocument; i++) {
//				OMElement document = axiom.createOMElement("Document", namespace);
//				OMElement title = axiom.createOMElement("Title", namespace);
//				title.setText(FileName);
//				OMElement description = axiom.createOMElement("Description", namespace);
//				description.setText(Description);
//				OMElement content = axiom.createOMElement("Content", namespace);
//				content.setText(base64);
//
//				document.addChild(title);
//				document.addChild(description);
//				document.addChild(content);
//				documents.addChild(document);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		source.addChild(documents);
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		OMElement response = pnr.MetadataGenerator(source);
		logger.info(response);
	}

}