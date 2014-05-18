package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ReturnType")
@XmlAccessorType (XmlAccessType.FIELD)
public class ReturnTypeType  extends TextType{
	public ReturnTypeType(){
		
	}
	public ReturnTypeType(String value) {
		super.value = value;
	}

}
