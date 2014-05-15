package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;


@XmlType(name = "Author")
@XmlAccessorType (XmlAccessType.FIELD)
public class AuthorType extends General{
	public static final int IS_SUBMISSION_SET = 0;
	public static final int IS_DOCUMENT_ENTRY = 1;
	@XmlTransient
	protected String uuid;
	@XmlTransient
	protected String entryUUID;
	
	@XmlElement(name="authorPerson")
	protected String authorPerson;
	@XmlElement(name="authorInstitution")
	protected String authorInstitution;
	@XmlElement(name="authorRole")
	protected String authorRole;
	@XmlElement(name="authorSpecialty")
	protected String authorSpecialty;
	
	public AuthorType(){
		super();
		uuid = ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_AUTHOR;
	}
	
	@Override
	public boolean validate() {
		return false;
	}
	public OMElement buildEbXML(){
		MetadataType.ObjectRef.add(uuid);
		/* classification */
		OMElement classification = axiom.createOMElement(EbXML.Classification, Namespace.RIM3);
		classification.addAttribute("id", createUUID(), null);
		classification.addAttribute("classificationScheme", uuid, null);
		classification.addAttribute("classifiedObject", entryUUID, null);
		classification.addAttribute("nodeRepresentation", "", null);

		OMElement slot01 = addSlot("authorPerson", new String[]{authorPerson});
		classification.addChild(slot01);
		OMElement slot02 = addSlot("authorInstitution", new String[]{authorInstitution});
		classification.addChild(slot02);
		OMElement slot03 = addSlot("authorRole", new String[]{authorRole});
		classification.addChild(slot03);
		OMElement slot04 = addSlot("authorSpecialty", new String[]{authorSpecialty});
		classification.addChild(slot04);
		
		/* classification */
		return classification;
	}

	public void setEntryUUID(String entryUUID) {
		this.entryUUID = entryUUID;
	}
	
	public void setAuthorPerson(String authorPerson) {
		this.authorPerson = authorPerson;
	}
	public void setAuthorInstitution(String authorInstitution) {
		this.authorInstitution = authorInstitution;
	}
	public void setAuthorRole(String authorRole) {
		this.authorRole = authorRole;
	}
	public void setAuthorSpecialty(String authorSpecialty) {
		this.authorSpecialty = authorSpecialty;
	}
}
