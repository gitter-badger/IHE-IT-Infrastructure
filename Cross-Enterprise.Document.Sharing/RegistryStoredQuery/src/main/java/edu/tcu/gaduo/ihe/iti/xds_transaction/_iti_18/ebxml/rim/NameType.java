package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Name", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class NameType {
	
	@XmlElement(name="LocalizedString", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected LocalizedStringType localizedString;

	/**
	 * @return the localizedString
	 */
	public LocalizedStringType getLocalizedString() {
		return localizedString;
	}

}
