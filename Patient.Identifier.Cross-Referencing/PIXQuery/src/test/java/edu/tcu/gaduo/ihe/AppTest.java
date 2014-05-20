package edu.tcu.gaduo.ihe;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.hl7.v2.QBP.Q23;
import edu.tcu.gaduo.ihe.hl7v2.message.sender.SocketSender;
import edu.tcu.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;
import edu.tcu.gaduo.ihe.hl7v25.info.MessageHeader;
import edu.tcu.gaduo.ihe.hl7v25.info.QueryParameterDefinition;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private String host;
	private Integer port;

	@Before
	public void init(){
		Class<AppTest> clazz = AppTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("PIXQuery.properties");
		Properties pro = new Properties();
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		host = pro.getProperty("pix.manager.host");
		port = Integer.valueOf(pro.getProperty("pix.manager.port"));

//		ICertificate cert = CertificateDetails.getInstance();
//		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", 
//				"openxds_2010/OpenXDS_2010_Truststore.jks", "password");

		String certificate = loader.getResource("certificate/openxds_2010/OpenXDS_2010_Keystore.p12").toString().replace("file:/", "");
		System.setProperty("javax.net.ssl.keyStore", certificate);
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
	}
	
	@Test
	public void testQBP_Q23() throws ParseException{
		MessageHeader msh = new MessageHeader();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		
		QueryParameterDefinition qpd = new QueryParameterDefinition();
		qpd.setQpd01("IHE PIX Query");
        qpd.setQpd02("QRY1248968460880");
		qpd.setQpd03("XYZ10501^^^XREF2005&1.3.6.1.4.1.21367.2005.1.2&ISO^PI");
		qpd.setQpd04("ALPHA^ALAN");
		
		Q23 q23 = new Q23(qpd, msh);
		ISocketSender sender = SocketSender.getInstance();
		sender.send(host, port, q23.toString());
		System.gc();
	}
}
