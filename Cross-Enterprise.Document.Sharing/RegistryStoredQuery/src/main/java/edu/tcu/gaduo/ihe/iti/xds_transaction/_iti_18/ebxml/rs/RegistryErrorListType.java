package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rs;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="RegistryErrorList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class RegistryErrorListType {


	@XmlElement(name="RegistryError", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0")
	protected List<RegistryErrorType> registryErrors;
}
