package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.lcm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryObjectListType;


@XmlType(name = "SubmitObjectsRequest", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class SubmitObjectsRequestType {
	
	@XmlElement(name="RegistryObjectList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected RegistryObjectListType registryObjectList;
}
