package edu.tcu.gaduo.ihe.iti.xds_transaction.ebxml.rs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RegistryResponse")
@XmlAccessorType (XmlAccessType.FIELD)
public class RegistryResponseType {

	@XmlAttribute(name = "status")
	protected String status;
	@XmlElement(name = "RegistryErrorList")
	protected RegistryErrorListType registryErrorList;
}
