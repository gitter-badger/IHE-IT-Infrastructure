package com.gaduo.hl7.info;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.segment.MSH;

public class MSHSegment {

	private String SendingApplication;
	private String SendingFacility;
	private String ReceivingApplication;
	private String ReceivingFacility;
	private String MessageControlID;
	
	private MSH msh;
	
	public String getSendingApplication() {
		return SendingApplication;
	}
	public String getSendingFacility() {
		return SendingFacility;
	}
	public String getReceivingApplication() {
		return ReceivingApplication;
	}
	public String getReceivingFacility() {
		return ReceivingFacility;
	}
	public String getMessageControlID() {
		return MessageControlID;
	}
	public void setSendingApplication(String sendingApplication) {
		SendingApplication = sendingApplication;
	}
	public void setSendingFacility(String sendingFacility) {
		SendingFacility = sendingFacility;
	}
	public void setReceivingApplication(String receivingApplication) {
		ReceivingApplication = receivingApplication;
	}
	public void setReceivingFacility(String receivingFacility) {
		ReceivingFacility = receivingFacility;
	}
	public void setMessageControlID(String messageControlID) {
		MessageControlID = messageControlID;
	}

	public MSH getMsh() throws DataTypeException {
		String timestamp = System.currentTimeMillis()+"";
		msh.getSendingApplication().getNamespaceID().setValue(SendingApplication);
		msh.getSendingFacility().getNamespaceID().setValue(SendingFacility);
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(ReceivingApplication);
		msh.getMsh6_ReceivingFacility().getHd1_NamespaceID().setValue(ReceivingFacility);
		msh.getDateTimeOfMessage().getTimeOfAnEvent().setValue(timestamp);
		msh.getMsh10_MessageControlID().setValue(MessageControlID);
		return msh;
	}

	public void setMsh(MSH msh) {
		this.msh = msh;
	}	
}
