package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.lcm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryObjectListType;

@XmlType(name = "RegistryRequest", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class RegistryRequestType {
	
	protected RegistryObjectListType registryObjectList;
}
