package com.gaduo.zk.view_model.pixpdq;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Init;

import com.gaduo.hl7.info.PIDSegment;
import com.gaduo.hl7.pid.AffinityDomain;

public class PIXConsumerVM {
	public static Logger logger = Logger.getLogger(PIXConsumerVM.class);
	private String ip;
	private int port;
	private String messageRequest;
	private String messageResponse;
	private String sendingApplication;
	private String sendingFacility;
	private String receivingApplication;
	private String receivingFacility;
	private String messageControlID;
	private PIDSegment person01;
	private PIDSegment person02;
	private String operator;
	private List<AffinityDomain> affinityDomains;


	@Init
	public void init() {
		
	}


	public String getIp() {
		return ip;
	}


	public int getPort() {
		return port;
	}


	public String getMessageRequest() {
		return messageRequest;
	}


	public String getMessageResponse() {
		return messageResponse;
	}


	public String getSendingApplication() {
		return sendingApplication;
	}


	public String getSendingFacility() {
		return sendingFacility;
	}


	public String getReceivingApplication() {
		return receivingApplication;
	}


	public String getReceivingFacility() {
		return receivingFacility;
	}


	public String getMessageControlID() {
		return messageControlID;
	}


	public PIDSegment getPerson01() {
		return person01;
	}


	public PIDSegment getPerson02() {
		return person02;
	}


	public String getOperator() {
		return operator;
	}


	public List<AffinityDomain> getAffinityDomains() {
		return affinityDomains;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public void setMessageRequest(String messageRequest) {
		this.messageRequest = messageRequest;
	}


	public void setMessageResponse(String messageResponse) {
		this.messageResponse = messageResponse;
	}


	public void setSendingApplication(String sendingApplication) {
		this.sendingApplication = sendingApplication;
	}


	public void setSendingFacility(String sendingFacility) {
		this.sendingFacility = sendingFacility;
	}


	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}


	public void setReceivingFacility(String receivingFacility) {
		this.receivingFacility = receivingFacility;
	}


	public void setMessageControlID(String messageControlID) {
		this.messageControlID = messageControlID;
	}


	public void setPerson01(PIDSegment person01) {
		this.person01 = person01;
	}


	public void setPerson02(PIDSegment person02) {
		this.person02 = person02;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public void setAffinityDomains(List<AffinityDomain> affinityDomains) {
		this.affinityDomains = affinityDomains;
	}
	
	
}
