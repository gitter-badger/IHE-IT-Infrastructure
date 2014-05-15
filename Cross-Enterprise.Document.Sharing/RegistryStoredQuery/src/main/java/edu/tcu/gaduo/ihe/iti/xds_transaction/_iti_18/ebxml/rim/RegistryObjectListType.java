
package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="RegistryObjectList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class RegistryObjectListType {

	public RegistryObjectListType(){
		extrinsicObjects = new ArrayList<ExtrinsicObjectType>();
	}
	
	@XmlElement(name="ExtrinsicObject", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<ExtrinsicObjectType> extrinsicObjects;
	
	@XmlElement(name="RegistryPackage", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<RegistryPackageType> registryPackages;
	

	// [0 ... *]
	@XmlElement(name="ObjectRef", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<ObjectRefType> objectRefs;
	
	@XmlElement(name="Association", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<AssociationType> associations;
	
	@XmlElement(name="Classification")
	protected List<ClassificationType> classifications;

	/**
	 * @return the extrinsicObjects
	 */
	public List<ExtrinsicObjectType> getExtrinsicObjects() {
		return extrinsicObjects;
	}

	/**
	 * @return the registryPackages
	 */
	public List<RegistryPackageType> getRegistryPackages() {
		return registryPackages;
	}

	/**
	 * @return the objectRefs
	 */
	public List<ObjectRefType> getObjectRefs() {
		return objectRefs;
	}

	/**
	 * @return the associations
	 */
	public List<AssociationType> getAssociations() {
		return associations;
	}

	/**
	 * @return the classifications
	 */
	public List<ClassificationType> getClassifications() {
		return classifications;
	}

	
}
