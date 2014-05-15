package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ValueList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class ValueListType {
	@XmlElement(name="Value", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<ValueType> values;

	/**
	 * @return the values
	 */
	public List<ValueType> getValues() {
		return values;
	}
	
	
}
