package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Codes")
@XmlAccessorType (XmlAccessType.FIELD)
public class CodesType {
	@XmlElement(name="Code")
	protected Set<String> list;
	public CodesType(){
		list = new HashSet<String>();
	}
	
	public void addCode(String code){
		list.add(code);
	}
	
	public Set<String> getList(){
		if(list == null){
			list = new HashSet<String>();
		}
		return list;
	}
}
