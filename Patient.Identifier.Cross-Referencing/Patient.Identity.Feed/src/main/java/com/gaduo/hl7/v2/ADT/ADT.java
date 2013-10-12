package com.gaduo.hl7.v2.ADT;


public class ADT {
	protected String SendingApplication;
	protected String SendingFacility;
	protected String ReceivingApplication;
	protected String ReceivingFacility;
	protected String MessageControlID;
	protected long timestamp;
	
	public ADT(){
		this.setTimestamp(System.currentTimeMillis());
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getReceivingFacility() {
		return ReceivingFacility;
	}
	public void setReceivingFacility(String receivingFacility) {
		ReceivingFacility = receivingFacility;
	}
	public String getSendingFacility() {
		return SendingFacility;
	}
	public void setSendingFacility(String sendingFacility) {
		SendingFacility = sendingFacility;
	}
	public String getSendingApplication() {
		return SendingApplication;
	}
	public void setSendingApplication(String sendingApplication) {
		SendingApplication = sendingApplication;
	}
	public String getReceivingApplication() {
		return ReceivingApplication;
	}
	public void setReceivingApplication(String receivingApplication) {
		ReceivingApplication = receivingApplication;
	}

	public String getMessageControlID() {
		return MessageControlID;
	}

	public void setMessageControlID(String messageControlID) {
		MessageControlID = messageControlID;
	}

}
