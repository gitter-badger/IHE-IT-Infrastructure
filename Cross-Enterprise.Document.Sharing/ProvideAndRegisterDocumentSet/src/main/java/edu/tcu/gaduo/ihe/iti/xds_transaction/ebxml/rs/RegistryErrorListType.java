package edu.tcu.gaduo.ihe.iti.xds_transaction.ebxml.rs;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RegistryErrorList")
@XmlAccessorType (XmlAccessType.FIELD)
public class RegistryErrorListType {

	@XmlElement(name = "RegistryError")
	protected List<RegistryErrorType> registryError;
}
