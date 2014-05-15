package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryObjectListType;

@XmlRootElement(name = "AdhocQueryResponse", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class AdhocQueryResponseType {
	@XmlAttribute(required=true)
	protected String status;
	@XmlElement(name="RegistryObjectList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected RegistryObjectListType registryObjectList;
	
	
	
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @return the registryObjectList
	 */
	public RegistryObjectListType getRegistryObjectList() {
		return registryObjectList;
	}
	
}
