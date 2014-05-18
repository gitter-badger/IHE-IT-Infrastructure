package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Codes")
@XmlAccessorType (XmlAccessType.FIELD)
public class CodesType {
	@XmlElement(name="Code")
	protected List<String> list;
	public CodesType(){
		list = new ArrayList<String>();
	}
	
	public void addCode(String code){
		list.add(code);
	}
	
	public List<String> getList(){
		if(list == null){
			list = new ArrayList<String>();
		}
		return list;
	}
}
