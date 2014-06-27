package edu.tcu.gaduo.ihe.hl7v231.info;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.segment.PID;

/**
 * @author Gaduo
 */
public class PatientIdentification {
	private String pid01 = ""; // 
	private String pid02 = "";
	private String pid03 = ""; // id
	private String pid04 = "";
	private String pid05 = "";
	private String pid06 = "";
	private String pid07 = "";
	private String pid08 = "";
	private String pid09 = "";
	private String pid10 = "";
	private String pid11 = "";
	private String pid12 = "";
	private String pid13 = "";
	private String pid14 = "";
	private String pid15 = "";
	private String pid16 = "";
	private String pid17 = "";
	private String pid18 = "";
	private String pid19 = "";
	private String pid20 = "";
	
	private PID pid;
	
	public String getPid01() {
		return this.pid01;
	}

	public void setPid01(String pid01) {
		this.pid01 = pid01;
	}

	public String getPid02() {
		return this.pid02;
	}

	public void setPid02(String pid02) {
		this.pid02 = pid02;
	}

	public String getPid03() {
		return this.pid03;
	}

	public void setPid03(String pid03) {
		this.pid03 = pid03;
	}

	public String getPid04() {
		return this.pid04;
	}

	public void setPid04(String pid04) {
		this.pid04 = pid04;
	}

	public String getPid05() {
		return this.pid05;
	}

	public void setPid05(String pid05) {
		this.pid05 = pid05;
	}

	public String getPid06() {
		return this.pid06;
	}

	public void setPid06(String pid06) {
		this.pid06 = pid06;
	}

	public Date getPid07() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			return df.parse(this.pid07);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setPid07(Date pid07) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		this.pid07 = df.format(pid07);
	}

	public String getPid08() {
		return this.pid08;
	}

	public void setPid08(String pid08) {
		this.pid08 = pid08;
	}

	public String getPid09() {
		return this.pid09;
	}

	public void setPid09(String pid09) {
		this.pid09 = pid09;
	}

	public String getPid10() {
		return this.pid10;
	}

	public void setPid10(String pid10) {
		this.pid10 = pid10;
	}

	public String getPid11() {
		return this.pid11;
	}

	public void setPid11(String pid11) {
		this.pid11 = pid11;
	}

	public String getPid12() {
		return pid12;
	}

	public String getPid13() {
		return pid13;
	}

	public String getPid14() {
		return pid14;
	}

	public String getPid15() {
		return pid15;
	}

	public String getPid16() {
		return pid16;
	}

	public String getPid17() {
		return pid17;
	}

	public String getPid18() {
		return pid18;
	}

	public String getPid19() {
		return pid19;
	}

	public String getPid20() {
		return pid20;
	}

	public void setPid12(String pid12) {
		this.pid12 = pid12;
	}

	public void setPid13(String pid13) {
		this.pid13 = pid13;
	}

	public void setPid14(String pid14) {
		this.pid14 = pid14;
	}

	public void setPid15(String pid15) {
		this.pid15 = pid15;
	}

	public void setPid16(String pid16) {
		this.pid16 = pid16;
	}

	public void setPid17(String pid17) {
		this.pid17 = pid17;
	}

	public void setPid18(String pid18) {
		this.pid18 = pid18;
	}

	public void setPid19(String pid19) {
		this.pid19 = pid19;
	}

	public void setPid20(String pid20) {
		this.pid20 = pid20;
	}
	
	public void setPid(PID pid){
		this.pid = pid;
	}
	
	public PID getPid() throws HL7Exception{
		pid.getPid1_SetIDPID().parse(pid01);
		pid.getPid2_PatientID().parse(pid02);
		pid.getPid3_PatientIdentifierList(0).parse(pid03);
		pid.getPid4_AlternatePatientIDPID(0).parse(pid04);
		pid.getPid5_PatientName(0).parse(pid05);
		pid.getPid6_MotherSMaidenName(0).parse(pid06);
		pid.getPid7_DateTimeOfBirth().parse(pid07);
		pid.getPid8_Sex().parse(pid07);
		pid.getPid9_PatientAlias(0).parse(pid09);
		pid.getPid10_Race(0).parse(pid10);
		pid.getPid11_PatientAddress(0).parse(pid11);
		pid.getPid12_CountyCode().parse(pid12);
		pid.getPid13_PhoneNumberHome(0).parse(pid13);
		pid.getPid14_PhoneNumberBusiness(0).parse(pid14);
		pid.getPid15_PrimaryLanguage().parse(pid15);
		pid.getPid16_MaritalStatus().parse(pid16);
		pid.getPid17_Religion().parse(pid17);
		pid.getPid18_PatientAccountNumber().parse(pid18);
		pid.getPid19_SSNNumberPatient().parse(pid19);
		pid.getPid20_DriverSLicenseNumberPatient().parse(pid20);
		return pid; 
	}
	
}
