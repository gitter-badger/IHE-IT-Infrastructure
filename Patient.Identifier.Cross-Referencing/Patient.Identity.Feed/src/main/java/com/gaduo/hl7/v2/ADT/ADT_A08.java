package com.gaduo.hl7.v2.ADT;

import ca.uhn.hl7v2.model.v231.segment.PID;


public class ADT_A08 extends ADT {
	private PID pid;
	private final String type = "ADT^A08";

	public ADT_A08() {
		super();
	}

	public ADT_A08(PID pid, String SendingApplication, String SendingFacility,
			String ReceivingApplication, String ReceivingFacility) {
		super();
		this.pid = pid;
		this.setSendingApplication(SendingApplication);
		this.setSendingFacility(SendingFacility);
		this.setReceivingApplication(ReceivingApplication);
		this.setReceivingFacility(ReceivingFacility);
	}

	public PID getPid() {
		return pid;
	}

	public void setPid(PID pid) {
		this.pid = pid;
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
