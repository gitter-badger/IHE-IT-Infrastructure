package edu.tcu.gaduo.ihe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;


import edu.tcu.gaduo.hl7.info.MRGSegment;
import edu.tcu.gaduo.hl7.info.MSHSegment;
import edu.tcu.gaduo.hl7.info.PIDSegment;
import edu.tcu.gaduo.hl7.v2.ADT.A01;
import edu.tcu.gaduo.hl7.v2.ADT.A04;
import edu.tcu.gaduo.hl7.v2.ADT.A05;
import edu.tcu.gaduo.hl7.v2.ADT.A08;
import edu.tcu.gaduo.hl7.v2.ADT.A40;
import edu.tcu.gaduo.ihe.AppTest;
import edu.tcu.gaduo.ihe.hl7v2.message.sender.SocketSender;
import edu.tcu.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public static Logger logger = Logger.getLogger(AppTest.class);


	public void testADT_A01() throws ParseException{
		MSHSegment msh = new MSHSegment();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PIDSegment pid = new PIDSegment();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A01 a01 = new A01(pid, msh);
		ISocketSender sender = new SocketSender();
		sender.send("203.64.84.112", 3602, a01.toString());
		System.gc();
	}
	
	public void testADT_A04() throws ParseException{
		MSHSegment msh = new MSHSegment();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PIDSegment pid = new PIDSegment();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A04 a01 = new A04(pid, msh);
		ISocketSender sender = new SocketSender();
		sender.send("203.64.84.112", 3602, a01.toString());
		System.gc();
	}
	
	public void testADT_A05() throws ParseException{
		MSHSegment msh = new MSHSegment();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PIDSegment pid = new PIDSegment();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A05 a01 = new A05(pid, msh);
		ISocketSender sender = new SocketSender();
		sender.send("203.64.84.112", 3602, a01.toString());
		System.gc();
	}
	
	public void testADT_A08() throws ParseException{
		MSHSegment msh = new MSHSegment();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PIDSegment pid = new PIDSegment();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		A08 a01 = new A08(pid, msh);
		ISocketSender sender = new SocketSender();
		sender.send("203.64.84.112", 3602, a01.toString());
		System.gc();
	}
	
	public void testADT_A40() throws ParseException{
		MSHSegment msh = new MSHSegment();
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("Gaduo");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("Gaduo-090528110022806");
		PIDSegment pid = new PIDSegment();
		pid.setPid03("2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.setPid05("Wang^Dai-Wei^^^");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		MRGSegment mrg = new MRGSegment();
		mrg.setMrg01("");
		mrg.setMrg07("");
		A40 a01 = new A40(pid, msh, mrg);
		ISocketSender sender = new SocketSender();
		sender.send("203.64.84.112", 3602, a01.toString());
		System.gc();
	}
}
