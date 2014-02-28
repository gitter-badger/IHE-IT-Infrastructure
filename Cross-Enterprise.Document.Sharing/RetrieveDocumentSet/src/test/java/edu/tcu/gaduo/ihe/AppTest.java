package edu.tcu.gaduo.ihe;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.dao.DocumentRequest;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	public static Logger logger = Logger.getLogger(AppTest.class);

	private String SIZE_1K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20131218082353.0";
	private String SIZE_5K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131027163625.7";
	private String SIZE_10K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131102143358.5";
	private String SIZE_20K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131102151342.2";
	private String SIZE_50K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131102224953.1";
	private String SIZE_100K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131103125522.13";
	private String SIZE_200K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131103231417.17";
	private String SIZE_500K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131104164903.8";
	private String SIZE_1024K = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.119.20131109233555.0";

	private String oid = "1.3.6.1.4.1.21367.2010.1.2.1125.112.132.531";
	
	private void send(){
		RetrieveDocumentSet rds = new RetrieveDocumentSet();
		rds.setRepositoryUrl("http://203.64.84.214:8020/axis2/services/xdsrepositoryb?wsdl");
		List<DocumentRequest> documentIdList = new ArrayList<DocumentRequest>();

		documentIdList.add(new DocumentRequest("1.3.6.1.4.1.21367.2010.1.2.1125.112", oid, ""));

		OMElement response = rds.RetrieveGenerator(documentIdList);
		logger.info(response);
	}
	public AppTest(String testName) {
		super(testName);
	}

	protected void setUp() {
	}

	public void test01(){
		send();
	}

}
