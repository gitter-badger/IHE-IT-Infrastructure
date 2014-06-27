package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.axiom.om.OMElement;


@XmlType(name = "PatientInfo")
public class PatientInfoType extends General {
	// should not include value for PID-2, PID-4, PID-12(country code), PID-19(ssn)
	@XmlElement(name="PID-3")
	String pid03; // id
	@XmlElement(name="PID-5")
	String pid05; // name
	@XmlElement(name="PID-7")
	String pid07; // birthday
	@XmlElement(name="PID-8")
	String pid08; // gender
	@XmlElement(name="PID-11")
	String pid11; // address


	@Override
	public boolean validate() {
		return false;
	}
	
	@Override
	public OMElement buildEbXML(){
		OMElement slot = addSlot("sourcePatientInfo", 
					new String[] {
						"PID-3|" + pid03, 
						"PID-5|" + pid05, 
						"PID-7|" + pid07,
						"PID-8|" + pid08,
						"PID-11|" + pid11 });
		return slot;
	}
	

	public void setPid03(String pid03) {
		this.pid03 = pid03;
	}

	public void setPid05(String pid05) {
		this.pid05 = pid05;
	}

	public void setPid07(String pid07) {
		this.pid07 = pid07;
	}

	public void setPid08(String pid08) {
		this.pid08 = pid08;
	}

	public void setPid11(String pid11) {
		this.pid11 = pid11;
	}
	
}
