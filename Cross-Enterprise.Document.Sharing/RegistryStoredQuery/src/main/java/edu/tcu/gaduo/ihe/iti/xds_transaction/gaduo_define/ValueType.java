package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Value")
@XmlAccessorType (XmlAccessType.FIELD)
public class ValueType extends TextType{

	public ValueType(){
		
	}
	
	public ValueType(String value) {
		super.value = value;
	}


}
