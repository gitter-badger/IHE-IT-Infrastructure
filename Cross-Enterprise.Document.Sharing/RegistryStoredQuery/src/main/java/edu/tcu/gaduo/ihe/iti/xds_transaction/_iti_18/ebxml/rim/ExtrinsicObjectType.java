package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="ExtrinsicObject", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class ExtrinsicObjectType{

	@XmlAttribute(required=true)
	protected String id;
	@XmlAttribute
	protected String home;
	@XmlAttribute
	protected String objectType;
	@XmlAttribute
	protected String mimeType;
	@XmlAttribute
	protected String status;

	@XmlElement(name="Name", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected NameType name;
	
	@XmlElement(name="Description", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected DescriptionType description;
	
	@XmlElement(name="Slot", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<SlotType> slots;

	@XmlElement(name="Classification", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<ClassificationType> classifications;
	
	@XmlElement(name="ExternalIdentifier", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
	protected List<ExternalIdentifierType> externalIdentifiers;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the home
	 */
	public String getHome() {
		return home;
	}

	/**
	 * @return the objectType
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the names
	 */
	public NameType getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public DescriptionType getDescription() {
		return description;
	}

	/**
	 * @return the slots
	 */
	public List<SlotType> getSlots() {
		return slots;
	}

	/**
	 * @return the classifications
	 */
	public List<ClassificationType> getClassifications() {
		return classifications;
	}

	/**
	 * @return the externalIdentifiers
	 */
	public List<ExternalIdentifierType> getExternalIdentifiers() {
		return externalIdentifiers;
	}
	
	
	
	
	
}
