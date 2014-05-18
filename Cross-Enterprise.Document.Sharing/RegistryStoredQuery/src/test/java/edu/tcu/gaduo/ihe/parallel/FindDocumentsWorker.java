package edu.tcu.gaduo.ihe.parallel;

import java.util.concurrent.CountDownLatch;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.RegistryUrlType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

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
		IAxiomUtil axiom = AxiomUtil.getInstance();
		long timestamp = System.currentTimeMillis();

		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		QueryType query = new QueryType();
		query.setRegistryUrl(new RegistryUrlType("http://203.64.84.214:8010/axis2/services/xdsregistryb?wsdl"));
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_FOLDER_AND_CONTENTS_UUID));
		query.setReturnType(new ReturnTypeType("ObjectRef"));
		
		ParameterType p1 = new ParameterType(StoredQueryConstants.FOL_ENTRY_UUID);
		p1.addValues(new ValueType("'urn:uuid:61a029d4-37f4-4599-b532-527b293b9a60'")); //urn:uuid:1ded6009-68e4-4a4f-9cd5-48f6be1eaa7f
		query.addParameters(p1);
		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);
		logger.info(Thread.currentThread().getName() + "====" + response);
		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
		
		
	}

}