package com.gaduo.ihe;

import java.io.IOException;

import org.apache.log4j.Logger;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.message.ADT_A01;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import ca.uhn.hl7v2.model.v231.segment.PV1;

import com.gaduo.ihe.hl7v2.message.sender.SocketSender;
import com.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;
import com.gaduo.ihe.security.Certificate;

public class App {
	public static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws HL7Exception, IOException {
		String timestamp = System.currentTimeMillis() + "";
		ADT_A01 a01 = new ADT_A01();
		a01.initQuickstart("ADT", "A01", "P"); // MSH MSH
		MSH mshSegment = a01.getMSH();
		mshSegment.getSendingApplication().getNamespaceID()
				.setValue("foxb1249");
		mshSegment.getSendingFacility().getNamespaceID().setValue("Gaduo");
		mshSegment.getMsh5_ReceivingApplication().getHd1_NamespaceID()
				.setValue("MESA_XREF");
		mshSegment.getMsh6_ReceivingFacility().getHd1_NamespaceID()
				.setValue("XYZ_HOSPITAL");
		mshSegment.getDateTimeOfMessage().getTimeOfAnEvent()
				.setValue(timestamp);
		mshSegment.getMsh10_MessageControlID().setValue("NIST-090528110022806");

		// //EVN
		EVN evn = a01.getEVN();
		evn.getEvn2_RecordedDateTime().getTs1_TimeOfAnEvent()
				.setValue(timestamp); //
		// PID1
		PID pid = a01.getPID();
		pid.getPid1_SetIDPID().parse("");
		pid.getPid2_PatientID().parse("");
		pid.getPid3_PatientIdentifierList(0).parse(
				"2013031143^^^IHENA&1.3.6.1.4.1.21367.2010.1.2.300&ISO");
		pid.getPid4_AlternatePatientIDPID(0).parse("");
		pid.getPid5_PatientName(0).parse("Wang^Dai-Wei^^^");
		pid.getPid6_MotherSMaidenName(0).parse("");
		pid.getPid7_DateTimeOfBirth().parse("19661109");
		pid.getPid8_Sex().parse("F");
		pid.getPid9_PatientAlias(0).parse("");
		pid.getPid10_Race(0).parse("");
		pid.getPid11_PatientAddress(0).parse(
				"Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		pid.getPid12_CountyCode().parse("");
		pid.getPid13_PhoneNumberHome(0).parse("");
		pid.getPid14_PhoneNumberBusiness(0).parse("");
		pid.getPid15_PrimaryLanguage().parse("");
		pid.getPid16_MaritalStatus().parse("");
		pid.getPid17_Religion().parse("");
		pid.getPid18_PatientAccountNumber().parse("");
		pid.getPid19_SSNNumberPatient().parse("");
		pid.getPid20_DriverSLicenseNumberPatient().parse(""); // PV1

		PV1 pv1 = a01.getPV1();
		pv1.getPv12_PatientClass().setValue("O");

		logger.info("\n" + a01.encode());

		new Certificate().setCertificate();
		ISocketSender sender = new SocketSender();
		char Start_Block = '\u000b';
		char End_Block = '\u001c';
		sender.send("203.64.84.112", 3602, 
				Start_Block + a01.encode() + End_Block);
		System.gc();
	}
}
