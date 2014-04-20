package edu.tcu.gaduo.ihe.iti.xds_transaction.ebxml.ihe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Document")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentType {
	@XmlAttribute(required=true)
	protected String id;
	
	protected String value;

}
