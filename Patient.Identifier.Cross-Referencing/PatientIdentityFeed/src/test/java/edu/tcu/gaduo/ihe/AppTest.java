package edu.tcu.gaduo.ihe;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;
import edu.tcu.gaduo.hl7v2.ADT.A01;
import edu.tcu.gaduo.hl7v2.ADT.A04;
import edu.tcu.gaduo.hl7v2.ADT.A05;
import edu.tcu.gaduo.hl7v2.ADT.A08;
import edu.tcu.gaduo.hl7v2.ADT.A40;
import edu.tcu.gaduo.ihe.hl7v2.message.sender.SocketSender;
import edu.tcu.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;
import edu.tcu.gaduo.ihe.hl7v231.info.MergePatientInformation;
import edu.tcu.gaduo.ihe.hl7v231.info.MessageHeader;
import edu.tcu.gaduo.ihe.hl7v231.info.PatientIdentification;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

/**
 * Unit test for simple App.
 */
public class AppTest  {
	public static Logger logger = Logger.getLogger(AppTest.class);
	private String host ;
	private int port;
	
	@Before
	public void init(){
		Class<AppTest> clazz = AppTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("PatientIdentityFeed.properties");
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
	public void testADT_A01() throws ParseException{
		MessageHeader msh = new MessageHeader();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PatientIdentification pid = new PatientIdentification();
		pid.setPid03("20140519^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A01 a01 = new A01(pid, msh);
		ISocketSender sender = SocketSender.getInstance();
		sender.send(host, port, a01.toString());
		System.gc();
	}
	
	@Test
	public void testADT_A04() throws ParseException{
		MessageHeader msh = new MessageHeader();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PatientIdentification pid = new PatientIdentification();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A04 a01 = new A04(pid, msh);
		ISocketSender sender = SocketSender.getInstance();
		sender.send(host, port, a01.toString());
		System.gc();
	}
	
	@Test
	public void testADT_A05() throws ParseException{
		MessageHeader msh = new MessageHeader();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PatientIdentification pid = new PatientIdentification();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A05 a01 = new A05(pid, msh);
		ISocketSender sender = SocketSender.getInstance();
		sender.send(host, port, a01.toString());
		System.gc();
	}
	
	@Test
	public void testADT_A08() throws ParseException{
		MessageHeader msh = new MessageHeader();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PatientIdentification pid = new PatientIdentification();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A08 a01 = new A08(pid, msh);
		ISocketSender sender = SocketSender.getInstance();
		sender.send(host, port, a01.toString());
		System.gc();
	}
	
	@Test
	public void testADT_A40() throws ParseException{
		MessageHeader msh = new MessageHeader();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PatientIdentification pid = new PatientIdentification();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		MergePatientInformation mrg = new MergePatientInformation();
		mrg.setMrg01("20140519^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		mrg.setMrg07("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		A40 a01 = new A40(pid, msh, mrg);
		ISocketSender sender = SocketSender.getInstance();
		sender.send(host, port, a01.toString());
		System.gc();
	}
	
	public void testHAPI(){
		
		try{
			MessageHeader msh = new MessageHeader();
			msh.setSendingApplication("foxb1249");
			msh.setSendingFacility("Gaduo");
			msh.setReceivingApplication("MESA_XREF");
			msh.setReceivingFacility("XYZ_HOSPITAL");
			msh.setMessageControlID("Gaduo-090528110022806");
			PatientIdentification pid = new PatientIdentification();
			pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
			pid.setPid05("Wang^Dai-Wei^^^");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse("19661109");
			pid.setPid07(date);
			pid.setPid08("F");
			pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
			A01 a01 = new A01(pid, msh);
			HapiContext context = new DefaultHapiContext();
	 		Connection connection = context.newClient(host, port, true);
	
	        Initiator initiator = connection.getInitiator();
	        Message response = initiator.sendAndReceive(a01.getADT_A01());
	        
	        Parser p = context.getPipeParser();
	        String responseString = p.encode(response);
		} catch(ca.uhn.hl7v2.HL7Exception e){
			e.printStackTrace();
		} catch (LLPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
