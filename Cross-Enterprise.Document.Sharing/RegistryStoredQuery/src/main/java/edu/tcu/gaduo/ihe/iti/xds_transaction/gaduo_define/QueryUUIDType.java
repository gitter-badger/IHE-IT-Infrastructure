package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


@XmlType(name="QueryUUID")
@XmlAccessorType (XmlAccessType.FIELD)
public class QueryUUIDType  extends TextType{

	public QueryUUIDType(){
		
	}
	
	public QueryUUIDType(String value){
		super.value = value;
	}
}
