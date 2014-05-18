package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Documents")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentsType {
	@XmlElement(name="Document")
	protected List<DocumentType> list;
	
	public DocumentsType(){
		list = new ArrayList<DocumentType>();
	}

	public void addDocument(DocumentType document) {
		list.add(document);
	}
	
	public List<DocumentType> getList(){
		return list;
	}
}
