package edu.tcu.gaduo.ihe.hl7v231.info;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.segment.MRG;


public class MergePatientInformation {
	private String mrg01;
	private String mrg02;
	private String mrg03;
	private String mrg04;
	private String mrg05;
	private String mrg06;
	private String mrg07;
	
	private MRG mrg;
	
	public void setMrg(MRG mrg) {
		this.mrg = mrg;
	}

	public MRG getMrg(){
		try {
			mrg.getMrg1_PriorPatientIdentifierList(0).parse(mrg01);
			mrg.getMrg7_PriorPatientName(0).parse(mrg07);
		} catch (HL7Exception e) {
			e.printStackTrace();
		}
		return mrg;
	}

	public String getMrg01() {
		return mrg01;
	}

	public String getMrg02() {
		return mrg02;
	}

	public String getMrg03() {
		return mrg03;
	}

	public String getMrg04() {
		return mrg04;
	}

	public String getMrg05() {
		return mrg05;
	}

	public String getMrg06() {
		return mrg06;
	}

	public String getMrg07() {
		return mrg07;
	}

	public void setMrg01(String mrg01) {
		this.mrg01 = mrg01;
	}

	public void setMrg02(String mrg02) {
		this.mrg02 = mrg02;
	}

	public void setMrg03(String mrg03) {
		this.mrg03 = mrg03;
	}

	public void setMrg04(String mrg04) {
		this.mrg04 = mrg04;
	}

	public void setMrg05(String mrg05) {
		this.mrg05 = mrg05;
	}

	public void setMrg06(String mrg06) {
		this.mrg06 = mrg06;
	}

	public void setMrg07(String mrg07) {
		this.mrg07 = mrg07;
	}
}
