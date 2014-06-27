package edu.tcu.gaduo.ihe.parallel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.ihe.RetrieveDocumentSetResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.DocumentRequest;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

public class RetrieveDocumentSetWorker implements Runnable {
	public static Logger logger = Logger.getLogger(RetrieveDocumentSetWorker.class);
	private CountDownLatch latch;
	private int index;

	public RetrieveDocumentSetWorker(int index, CountDownLatch latch) {
		this.index = index;
		this.latch = latch;
	}

	public void run() {
		Thread t = Thread.currentThread();
		long timestamp = System.currentTimeMillis();
		logger.info("@@@@@@\t" + t.getName() + "\t" + timestamp + "\t@@@@@@");
		try {
			OneSubmit(1, "0010k (" + (index % 10) + ").xml");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		latch.countDown();
		long timestamp02 = System.currentTimeMillis();
		logger.info("$$$$$$\t" + t.getName() + "\t" + timestamp02 + "\t$$$$$$");
		timestamp = timestamp02 - timestamp;
		logger.info("######\t" + t.getName() + "\t" + timestamp + "\t######");
		
	}
	private String oid = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20140515071841.0";

	private void OneSubmit(int numberOfDocument, String FileName) throws UnsupportedEncodingException, JAXBException {
//		IAxiomUtil axiom = AxiomUtil.getInstance();
		long timestamp = System.currentTimeMillis();

		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");

		RetrieveDocumentSet rds = new RetrieveDocumentSet();
		rds.setRepositoryUrl("http://203.64.84.214:8020/axis2/services/xdsrepositoryb?wsdl");
		Set<DocumentRequest> documentIdList = new HashSet<DocumentRequest>();
		documentIdList.add(new DocumentRequest("1.3.6.1.4.1.21367.2010.1.2.1125.103", oid, ""));
		OMElement response = rds.RetrieveGenerator(documentIdList);
		
		InputStream is = new ByteArrayInputStream(response.toString().getBytes("utf-8"));
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RetrieveDocumentSetResponseType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		RetrieveDocumentSetResponseType rdst = (RetrieveDocumentSetResponseType) jaxbUnmarshaller.unmarshal(is);
	
		logger.info(Thread.currentThread().getName() + "====" + rdst.getRegistryResponseType().getStatus());
		double time = System.currentTimeMillis() - timestamp;
		System.out.println(time);
		
		
	}

}