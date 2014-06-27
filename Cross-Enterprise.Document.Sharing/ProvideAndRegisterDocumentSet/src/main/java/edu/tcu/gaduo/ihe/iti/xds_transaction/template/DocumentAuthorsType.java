package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DocumentAuthors")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentAuthorsType {
	
	@XmlElement(name="DocumentAuthor")
	protected List<DocumentAuthorType> list;
	
	
	public DocumentAuthorsType(){
		list = new ArrayList<DocumentAuthorType>();
	}
	
	public void addAuthor(DocumentAuthorType author){
		list.add(author);
	}
}
