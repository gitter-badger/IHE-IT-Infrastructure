package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RegistryUrl")
@XmlAccessorType (XmlAccessType.FIELD)
public class RegistryUrlType extends TextType{
	public RegistryUrlType(){
		
	}
	public RegistryUrlType(String value){
		super.value = value;
	}
}
