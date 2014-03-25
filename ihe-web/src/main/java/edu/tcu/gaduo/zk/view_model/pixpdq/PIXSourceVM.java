/**
 * 
 */
package edu.tcu.gaduo.zk.view_model.pixpdq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import edu.tcu.gaduo.hl7.info.MRGSegment;
import edu.tcu.gaduo.hl7.info.MSHSegment;
import edu.tcu.gaduo.hl7.info.PIDSegment;
import edu.tcu.gaduo.hl7.v2.ADT.A01;
import edu.tcu.gaduo.hl7.v2.ADT.A04;
import edu.tcu.gaduo.hl7.v2.ADT.A05;
import edu.tcu.gaduo.hl7.v2.ADT.A08;
import edu.tcu.gaduo.hl7.v2.ADT.A40;
import edu.tcu.gaduo.hl7.v2.ADT.ADT;

import edu.tcu.gaduo.hl7.pid.AffinityDomain;
import edu.tcu.gaduo.ihe.hl7v2.message.sender.SocketSender;
import edu.tcu.gaduo.ihe.hl7v2.message.sender._interface.ISocketSender;
import edu.tcu.gaduo.zk.view_model.AffinityDomainVM;

/**
 * @author Gaduo
 */
public class PIXSourceVM {
	public static Logger logger = Logger.getLogger(PIXSourceVM.class);
	private String ip;
	private int port;
	private String messageRequest;
	private String messageResponse;

	private MSHSegment msh;
	private PIDSegment pid;
	private MRGSegment mrg;
	private String operator;
	private List<AffinityDomain> affinityDomains;
	private AffinityDomain affinityDomainPid;
	private AffinityDomain affinityDomainMrg;

	@Init
	public void init() throws ParseException {
		setIp("203.64.84.112");
		setPort(3602);
		setOperator("ADT^A01");
		AffinityDomainVM ad = new AffinityDomainVM();
		ad.init();
		setAffinityDomains(ad.getAffinityDomains());

		this.setAffinityDomainPid(affinityDomains.get(3));
		this.setAffinityDomainMrg(affinityDomains.get(3));
		// ----------------------------------------------------
		setMsh(new MSHSegment());
		msh.setSendingApplication("foxb1249");
		msh.setSendingFacility("PIXSource");
		msh.setReceivingApplication("MESA_XREF");
		msh.setReceivingFacility("XYZ_HOSPITAL");
		msh.setMessageControlID("NIST-090528110022806");
		// ----------------------------------------------------
		setPid(new PIDSegment());
		pid.setPid03("2013031143^^^");
		pid.setPid05("Wang^Dai-Wei^^^");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("19661109");
		pid.setPid07(date);
		pid.setPid08("F");
		pid.setPid11("Sec.2, Linong Street^^ Taipei ^112^ Taiwan");
		// ----------------------------------------------------
		setMrg(new MRGSegment());
		mrg.setMrg01("2013101143^^^");
		mrg.setMrg07("");
		System.gc();
	}

	@Command
	@NotifyChange({ "messageRequest", "messageResponse" })
	public void submit() {
		ADT adt = null;
		String pid03 = pid.getPid03();
		pid.setPid03(pid03 + affinityDomainPid);
		String mrg01 = mrg.getMrg01();
		mrg.setMrg01(mrg01 + affinityDomainMrg);
		if (operator.equals("ADT^A01")) {
			adt = new A01(pid, msh);
		}
		if (operator.equals("ADT^A04")) {
			adt = new A04(pid, msh);
		}
		if (operator.equals("ADT^A05")) {
			adt = new A05(pid, msh);
		}
		if (operator.equals("ADT^A08")) {
			adt = new A08(pid, msh);
		}
		if (operator.equals("ADT^A40")) {
			adt = new A40(pid, msh, mrg);
		}
		if (adt != null) {
			setMessageRequest(adt.toString());
			ISocketSender sender = SocketSender.getInstance();
			logger.info(ip + ":" + port);
			String response = sender.send(ip, port, adt.toString());
			setMessageResponse(response);
		}
		pid03 = pid.getPid03();
		pid.setPid03(pid03.replace(affinityDomainPid.toString(), ""));
		mrg01 = mrg.getMrg01();
		mrg.setMrg01(mrg01.replace(affinityDomainMrg.toString(), ""));
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<AffinityDomain> getAffinityDomains() {
		return affinityDomains;
	}

	public void setAffinityDomains(List<AffinityDomain> affinityDomains) {
		this.affinityDomains = affinityDomains;
	}

	// -------------------------------

	public PIDSegment getPid() {
		return pid;
	}

	public MSHSegment getMsh() {
		return msh;
	}

	public void setMsh(MSHSegment msh) {
		this.msh = msh;
	}

	public void setPid(PIDSegment pid) {
		this.pid = pid;
	}

	public MRGSegment getMrg() {
		return mrg;
	}

	public void setMrg(MRGSegment mrg) {
		this.mrg = mrg;
	}

	public AffinityDomain getAffinityDomainPid() {
		return affinityDomainPid;
	}

	public void setAffinityDomainPid(AffinityDomain affinityDomainPid) {
		this.affinityDomainPid = affinityDomainPid;
	}

	public AffinityDomain getAffinityDomainMrg() {
		return affinityDomainMrg;
	}

	public void setAffinityDomainMrg(AffinityDomain affinityDomainMrg) {
		this.affinityDomainMrg = affinityDomainMrg;
	}

}
