package edu.tcu.gaduo.ihe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.ihe.RetrieveDocumentSetResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.DocumentRequest;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;

/**
 * Unit test for simple App.
 */
public class AppTest {
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

	private String oid = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20140515071841.0";
	
	private void send() throws JAXBException, UnsupportedEncodingException{
		RetrieveDocumentSet rds = new RetrieveDocumentSet();
		rds.setRepositoryUrl("http://203.64.84.214:8020/axis2/services/xdsrepositoryb?wsdl");
		Set<DocumentRequest> documentIdList = new HashSet<DocumentRequest>();
		documentIdList.add(new DocumentRequest("1.3.6.1.4.1.21367.2010.1.2.1125.103", oid, ""));
		OMElement response = rds.RetrieveGenerator(documentIdList);
		logger.info(response);
		
		InputStream is = new ByteArrayInputStream(response.toString().getBytes("utf-8"));
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RetrieveDocumentSetResponseType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		RetrieveDocumentSetResponseType rdst = (RetrieveDocumentSetResponseType) jaxbUnmarshaller.unmarshal(is);
	
		logger.info(rdst.getRegistryResponseType().getStatus());
		
	}

	
	@Test
	public void test01(){
		try {
			send();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
