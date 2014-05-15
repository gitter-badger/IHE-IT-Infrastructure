package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name="Value", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class ValueType {

	@XmlValue
	protected String text;

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
}
