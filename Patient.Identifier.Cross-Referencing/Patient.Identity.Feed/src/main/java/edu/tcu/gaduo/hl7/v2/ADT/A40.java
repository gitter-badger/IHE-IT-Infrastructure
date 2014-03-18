package edu.tcu.gaduo.hl7.v2.ADT;

import java.io.IOException;

import org.apache.log4j.Logger;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.message.ADT_A40;
import edu.tcu.gaduo.hl7.info.MRGSegment;
import edu.tcu.gaduo.hl7.info.MSHSegment;
import edu.tcu.gaduo.hl7.info.PIDSegment;

public class A40 extends ADT {
	public static Logger logger = Logger.getLogger(A01.class);

	private ADT_A40 adt;

	/**
	 * ITI-08 transaction message, ADT^A40：合併病患訊息, merge patient - patient identifier list
	 * @param pidSegment
	 * @param mshSegment
	 * @param mrgSegment
	 */
	public A40(PIDSegment pidSegment, MSHSegment mshSegment, MRGSegment mrgSegment) {
		try {
			adt = new ADT_A40();
			adt.initQuickstart("ADT", "A40", "P");			
			mshSegment.setMsh(adt.getMSH());
			msh = mshSegment.getMsh();
			evn = adt.getEVN();
			evn = this.getEvn(evn);
			pidSegment.setPid(adt.getPIDPD1MRGPV1().getPID());
			pid = pidSegment.getPid();
			mrgSegment.setMrg(adt.getPIDPD1MRGPV1().getMRG());
			mrg = mrgSegment.getMrg();
			pv1 = adt.getPIDPD1MRGPV1().getPV1();
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
			bs.append(mrg.encode()).append(Carriage_Return);
			bs.append(pv1.encode()).append(Carriage_Return);
			bs.append(End_Block).append(Carriage_Return);
		} catch (HL7Exception e) {
			e.printStackTrace();
		}
		return bs.toString();
	}

}
