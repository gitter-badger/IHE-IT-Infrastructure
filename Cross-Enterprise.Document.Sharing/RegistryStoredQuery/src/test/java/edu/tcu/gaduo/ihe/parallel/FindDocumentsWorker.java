package edu.tcu.gaduo.ihe.parallel;

import java.util.concurrent.CountDownLatch;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility.LoadTesDatatUtil;

public class FindDocumentsWorker implements Runnable {
	public static Logger logger = Logger.getLogger(FindDocumentsWorker.class);
	private CountDownLatch latch;
	private int index;

	public FindDocumentsWorker(int index, CountDownLatch latch) {
		this.index = index;
		this.latch = latch;
	}

	public void run() {
		Thread t = Thread.currentThread();
		long timestamp = System.currentTimeMillis();
		logger.info("@@@@@@\t" + t.getName() + "\t" + timestamp + "\t@@@@@@");
		OneSubmit(1, "0010k (" + (index % 10) + ").xml");
		latch.countDown();
		long timestamp02 = System.currentTimeMillis();
		logger.info("$$$$$$\t" + t.getName() + "\t" + timestamp02 + "\t$$$$$$");
		timestamp = timestamp02 - timestamp;
		logger.info("######\t" + t.getName() + "\t" + timestamp + "\t######");
		
	}

	private void OneSubmit(int numberOfDocument, String FileName) {
		LoadTesDatatUtil load = new LoadTesDatatUtil();
		long timestamp = System.currentTimeMillis();

		AxiomUtil axiom = new AxiomUtil();
		Certificate cert = new Certificate();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		OMElement source = load.loadTestDataToOMElement("template/FindDocuments.xml");

		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
		
		
	}

}