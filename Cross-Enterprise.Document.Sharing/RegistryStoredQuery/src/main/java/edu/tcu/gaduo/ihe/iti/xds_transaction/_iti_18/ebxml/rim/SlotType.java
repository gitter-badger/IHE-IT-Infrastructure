package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="Slot", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class SlotType {
	
	@XmlAttribute
	protected String name;
	@XmlElement(name="ValueList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected ValueListType valueList;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the valueList
	 */
	public ValueListType getValueList() {
		return valueList;
	}
	
}
