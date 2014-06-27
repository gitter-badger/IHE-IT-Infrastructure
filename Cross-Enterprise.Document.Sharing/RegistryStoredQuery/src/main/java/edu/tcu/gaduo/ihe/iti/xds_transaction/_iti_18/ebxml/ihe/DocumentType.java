package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.ihe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name = "Document", namespace="urn:ihe:iti:xds-b:2007")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentType {
	@XmlAttribute(required=true)
	protected String id;
	
	@XmlValue
	protected String value;

}
