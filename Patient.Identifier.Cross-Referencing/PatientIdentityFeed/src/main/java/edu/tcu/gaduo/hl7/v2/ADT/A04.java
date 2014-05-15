package edu.tcu.gaduo.hl7.v2.ADT;

import java.io.IOException;

import org.apache.log4j.Logger;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.message.ADT_A01;
import edu.tcu.gaduo.hl7.info.MSHSegment;
import edu.tcu.gaduo.hl7.info.PIDSegment;

public class A04 extends ADT {
	public static Logger logger = Logger.getLogger(A01.class);

	private ADT_A01 adt;

	/**
	 * ITI-08 transaction message, ADT^A04：門診病患掛號 , Register a patient
	 * 
	 * @param pSegment
	 * @param mshSegment
	 */
	public A04(PIDSegment pSegment, MSHSegment mshSegment) {
		try {
			adt = new ADT_A01();
			adt.initQuickstart("ADT", "A04", "P");
			mshSegment.setMsh(adt.getMSH());
			msh = mshSegment.getMsh();
			evn = adt.getEVN();
			evn = this.getEvn(evn);
			pSegment.setPid(adt.getPID());
			pid = pSegment.getPid();
			pv1 = adt.getPV1();
			pv1 = this.getPv1(pv1);
		} catch (HL7Exception e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		StringBuffer bs = new StringBuffer();
		try {
			bs.append(Start_Block);
			bs.append(msh.encode()).append(Carriage_Return);
			bs.append(evn.encode()).append(Carriage_Return);
			bs.append(pid.encode()).append(Carriage_Return);
			bs.append(pv1.encode()).append(Carriage_Return);
			bs.append(End_Block).append(Carriage_Return);
		} catch (HL7Exception e) {
			e.printStackTrace();
		}
		return bs.toString();
	}

}
