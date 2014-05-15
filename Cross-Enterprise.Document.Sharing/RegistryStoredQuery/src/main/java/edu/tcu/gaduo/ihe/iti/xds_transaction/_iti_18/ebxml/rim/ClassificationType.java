package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Classification", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class ClassificationType {
	@XmlAttribute
	protected String id;
	@XmlAttribute(name="classificationScheme")
	protected String classificationScheme;
	@XmlAttribute(required=true)
	protected String classifiedObject;
	@XmlAttribute(name="nodeRepresentation")
	protected String nodeRepresentation;
	
	@XmlElement(name="Slot", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<SlotType> slots;
	
	@XmlElement(name="name", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected NameType name;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the classificationScheme
	 */
	public String getClassificationScheme() {
		return classificationScheme;
	}

	/**
	 * @return the classifiedObject
	 */
	public String getClassifiedObject() {
		return classifiedObject;
	}

	/**
	 * @return the nodeRepresentation
	 */
	public String getNodeRepresentation() {
		return nodeRepresentation;
	}

	/**
	 * @return the slots
	 */
	public List<SlotType> getSlots() {
		return slots;
	}

	/**
	 * @return the name
	 */
	public NameType getName() {
		return name;
	}

	
	
}
