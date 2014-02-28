package edu.tcu.gaduo.ihe;

import java.util.Iterator;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility.LoadTesDatatUtil;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.Response_ITI_18;

public class GetDocumentsTest extends TestCase {
	public static Logger logger = Logger.getLogger(GetDocumentsTest.class);
	LoadTesDatatUtil load;

	public GetDocumentsTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}

	public void testApp() {
		long timestamp = System.currentTimeMillis();
		Certificate cert = new Certificate();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		OMElement source = load
				.loadTestDataToOMElement("template/GetDocuments.xml");

		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		logger.info(response);
		double time = System.currentTimeMillis() - timestamp;
		logger.info(time);
	}

	public void testAppByLoadUUID() {
		int tokenNumber = 50;
		Response_ITI_18 parser = new Response_ITI_18();

		OMElement uuids = load.loadTestDataToOMElement("template/uuid300.xml");
		parser.parser(uuids);
		TreeSet<String> list = (TreeSet<String>) parser.getList();
		Iterator<String> iterator = list.descendingIterator();
		int count = list.size();

		long timestamp = System.currentTimeMillis();
		Certificate cert = new Certificate();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		for (int i = 0; i < count; i += tokenNumber) {
			OMElement source = load.loadTestDataToOMElement("template/GetDocuments_02.xml");
			AxiomUtil axiom = new AxiomUtil();
			OMElement Parameter = axiom.createOMElement("Parameter", null);
			Parameter.addAttribute("name", "$XDSDocumentEntryEntryUUID", null);
			for(int j = 0; j < tokenNumber && iterator.hasNext(); j++){
				String uuid = iterator.next();
				OMElement Value = axiom.createOMElement("Value", null);
				Value.setText("'" + uuid + "'");
				Parameter.addChild(Value);
			}
			source.addChild(Parameter);
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			OMElement response = rsq.QueryGenerator(source);
		}

		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
	}
}
