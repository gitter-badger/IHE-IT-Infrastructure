package edu.tcu.gaduo.ihe;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.test.LoadTesDatatUtil;

public class FindDocumentsTest extends TestCase {
	public static Logger logger = Logger.getLogger(FindDocumentsTest.class);
	LoadTesDatatUtil load;

	public FindDocumentsTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}

	public void testApp() {
		ICertificate cert = Certificate.getInstance();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		for (int i = 0; i < 20; i++) {

			long timestamp = System.currentTimeMillis();
			OMElement source = load
					.loadTestDataToOMElement("template/FindDocuments.xml");

			RegistryStoredQuery rsq = new RegistryStoredQuery();
			OMElement response = rsq.QueryGenerator(source);
			logger.info(response);
			double time = System.currentTimeMillis() - timestamp;
			System.out.println(time);
		}
	}
}
