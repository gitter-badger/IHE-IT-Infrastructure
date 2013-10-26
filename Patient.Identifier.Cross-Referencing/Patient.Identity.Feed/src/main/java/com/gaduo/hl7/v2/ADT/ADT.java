package com.gaduo.hl7.v2.ADT;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.datatype.IS;
import ca.uhn.hl7v2.model.v231.datatype.TS;
import ca.uhn.hl7v2.model.v231.datatype.TSComponentOne;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.MRG;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import ca.uhn.hl7v2.model.v231.segment.PV1;


public class ADT {
	protected char Start_Block = '\u000b';
	protected char End_Block = '\u001c';
	protected char Carriage_Return = 13;
    protected MSH msh;
    protected EVN evn;
    protected PID pid;
    protected PV1 pv1;
    protected MRG mrg;
    
    public EVN getEvn(EVN evn){
    	this.evn = evn;
		try {
	    	String timestamp = System.currentTimeMillis() + "";
			TS ts = evn.getEvn2_RecordedDateTime(); 
			TSComponentOne componentOne = ts.getTs1_TimeOfAnEvent();
			componentOne.setValue(timestamp);
			return evn;
		} catch (DataTypeException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public PV1 getPv1(PV1 pv1){
    	this.pv1 = pv1;
		try {
			IS is = pv1.getPv12_PatientClass();
			is.setValue("o");
			return pv1;
		} catch (DataTypeException e) {
			e.printStackTrace();
		}
		return null;
    }
}
