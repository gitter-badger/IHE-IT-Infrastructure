package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="ExternalIdentifier", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class ExternalIdentifierType {

	@XmlAttribute
	protected String id;
	@XmlAttribute
	protected String identificationScheme;
	@XmlAttribute
	protected String registryObject;
	@XmlAttribute
	protected String value;
	
	@XmlElement(name="Name", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected NameType name;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the identificationScheme
	 */
	public String getIdentificationScheme() {
		return identificationScheme;
	}

	/**
	 * @return the registryObject
	 */
	public String getRegistryObject() {
		return registryObject;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the name
	 */
	public NameType getName() {
		return name;
	}
	
	
	
}
