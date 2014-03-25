package edu.tcu.gaduo.ihe;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.test.LoadTesDatatUtil;

public class GetFolderAndContentsTest extends TestCase {
	public static Logger logger = Logger.getLogger(GetFolderAndContentsTest.class);
	LoadTesDatatUtil load;

	public GetFolderAndContentsTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}

	public void testApp() {
		long timestamp = System.currentTimeMillis();
		ICertificate cert = Certificate.getInstance();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		OMElement source = load.loadTestDataToOMElement("template/GetFolderAndContents.xml");

		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		logger.info(response);
		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
	}
}
