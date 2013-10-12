package com.gaduo.hl7.v2.ADT;

import com.gaduo.hl7.pid.PID;

public class A08 extends ADT {
	private PID pid;
	private final String type = "ADT^A08^ADT_A01";

	public A08(PID pid, String SendingApplication, String SendingFacility,
			String ReceivingApplication, String ReceivingFacility,
			String messageControlID) {
		super();
		this.pid = pid;
		this.setSendingApplication(SendingApplication);
		this.setSendingFacility(SendingFacility);
		this.setReceivingApplication(ReceivingApplication);
		this.setReceivingFacility(ReceivingFacility);
		this.setMessageControlID(messageControlID);
	}

	public String toString() {
		StringBuffer bs = new StringBuffer();
		char Start_Block = '\u000b';
		char End_Block = '\u001c';
		char Carriage_Return = 13;
		String ADT_A01_1 = "MSH|^~\\&|" + getSendingApplication() + "|"
				+ getSendingFacility() + "|" + getReceivingApplication() + "|"
				+ getReceivingFacility() + "|" + getTimestamp() + "||" + type
				+ "|" + getMessageControlID() + "|P|2.3.1";
		String ADT_A01_2 = "EVN||" + getTimestamp();
		String ADT_A01_3 = "PID" + pid;
		String ADT_A01_4 = "PV1||O";
		bs.append(Start_Block).append(ADT_A01_1).append(Carriage_Return);
		bs.append(ADT_A01_2).append(Carriage_Return);
		bs.append(ADT_A01_3).append(Carriage_Return);
		bs.append(ADT_A01_4).append(Carriage_Return);
		bs.append(End_Block).append(Carriage_Return);

		return bs.toString();
	}

}
