package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Authors")
@XmlAccessorType (XmlAccessType.FIELD)
public class AuthorsType {
	
	@XmlElement(name="Author")
	protected List<AuthorType> list;
	
	
	public AuthorsType(){
		list = new ArrayList<AuthorType>();
	}
	
	public void addAuthor(AuthorType author){
		list.add(author);
	}
}
