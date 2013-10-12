/**
 * 
 */
package com.gaduo.zk.view_model.pixpdq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.gaduo.hl7.pid.AffinityDomain;
import com.gaduo.hl7.pid.PID;
import com.gaduo.hl7.v2.ADT.A01;
import com.gaduo.hl7.v2.ADT.A04;
import com.gaduo.hl7.v2.ADT.A05;
import com.gaduo.hl7.v2.ADT.A08;
import com.gaduo.hl7.v2.ADT.A40;
import com.gaduo.hl7.v2.ADT.ADT;
import com.gaduo.zk.view_model.AffinityDomainVM;

/**
 * @author Gaduo
 */
public class PIXSourceVM {
	public static Logger logger = Logger.getLogger(PIXSourceVM.class);
	private String ip;
	private int port;
	private String messageRequest;
	private String messageResponse;
	private String sendingApplication;
	private String sendingFacility;
	private String receivingApplication;
	private String receivingFacility;
	private String messageControlID;
	private PID person01;
	private PID person02;
	private String operator;
	private List<AffinityDomain> affinityDomains;

	@Init
	public void init() throws ParseException {
		this.setIp("203.64.84.112");
		this.setPort(3600);
		this.setOperator("ADT^A01");
		AffinityDomainVM ad = new AffinityDomainVM();
		ad.init();
		this.setAffinityDomains(ad.getAffinityDomains());
		// ----------------------------------------------------
		this.setPerson01(new PID());
		this.person01.setPid03("2013031143^^^");
		this.person01.setAffinityDomain(getAffinityDomains().get(3));
		this.person01.setPid05("Wang^Dai-Wei^^^");

		Date date = new SimpleDateFormat("yyyyMMdd").parse(String
				.valueOf(19661109));
		this.person01.setPid07(date);
		this.person01.setPid08("F");
		this.person01.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		// ----------------------------------------------------
		this.setPerson02(new PID());

		this.setSendingApplication("foxb1249");
		this.setSendingFacility("PIXSource");
		this.setReceivingApplication("MESA_XREF");
		this.setReceivingFacility("XYZ_HOSPITAL");
		this.setMessageControlID("NIST-090528110022806");
		System.gc();
	}

	@Command
	@NotifyChange({ "messageRequest", "messageResponse" })
	public void submit() {
		ADT adt = null;
		if (operator.equals("ADT^A01")) {
			adt = new A01(this.getPerson01(), sendingApplication,
					sendingFacility, receivingApplication, receivingFacility,
					messageControlID);
		}
		if (operator.equals("ADT^A04")) {
			adt = new A04(this.getPerson01(), sendingApplication,
					sendingFacility, receivingApplication, receivingFacility,
					messageControlID);
		}
		if (operator.equals("ADT^A05")) {
			adt = new A05(this.getPerson01(), sendingApplication,
					sendingFacility, receivingApplication, receivingFacility,
					messageControlID);
		}
		if (operator.equals("ADT^A08")) {
			adt = new A08(this.getPerson01(), sendingApplication,
					sendingFacility, receivingApplication, receivingFacility,
					messageControlID);
		}
		if (operator.equals("ADT^A40")) {
			adt = new A40(this.getPerson01(), this.getPerson02(),
					sendingApplication, sendingFacility, receivingApplication,
					receivingFacility, messageControlID);
		}
		if (adt != null) {
			this.setMessageRequest(adt.toString());
			this.send(this.getMessageRequest());
		}
	}

	private void send(String message) {
		logger.trace(this.operator);
		logger.trace(message);
		try {
			Socket socket = new Socket(ip, port);
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			byte[] bytes = message.getBytes();
			os.write(bytes);
			bytes = new byte[250];
			is.read(bytes);
			this.setMessageResponse(new String(bytes));
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Command
	public void checked(@BindingParam("checked") boolean checked) {

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getMessageRequest() {
		return messageRequest;
	}

	public void setMessageRequest(String message) {
		this.messageRequest = message;
	}

	public String getMessageResponse() {
		return messageResponse;
	}

	public void setMessageResponse(String message) {
		this.messageResponse = message;
	}

	public PID getPerson01() {
		return person01;
	}

	public void setPerson01(PID person01) {
		this.person01 = person01;
	}

	public PID getPerson02() {
		return person02;
	}

	public void setPerson02(PID person02) {
		this.person02 = person02;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSendingApplication() {
		return sendingApplication;
	}

	public void setSendingApplication(String sendingApplication) {
		this.sendingApplication = sendingApplication;
	}

	public String getSendingFacility() {
		return sendingFacility;
	}

	public void setSendingFacility(String sendingFacility) {
		this.sendingFacility = sendingFacility;
	}

	public String getReceivingFacility() {
		return receivingFacility;
	}

	public void setReceivingFacility(String receivingFacility) {
		this.receivingFacility = receivingFacility;
	}

	public String getReceivingApplication() {
		return this.receivingApplication;
	}

	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}

	public String getMessageControlID() {
		return messageControlID;
	}

	public void setMessageControlID(String messageControlID) {
		this.messageControlID = messageControlID;
	}

	public List<AffinityDomain> getAffinityDomains() {
		return affinityDomains;
	}

	public void setAffinityDomains(List<AffinityDomain> affinityDomains) {
		this.affinityDomains = affinityDomains;
	}
}
