package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
	protected Set<String> authorPersons;
	@XmlElement(name="authorInstitution")
	protected Set<String> authorInstitutions;
	@XmlElement(name="authorRole")
	protected Set<String> authorRoles;
	@XmlElement(name="authorSpecialty")
	protected Set<String> authorSpecialtys;
//	@XmlElement(name="authorTelecommunication")
//	protected Set<String> authorTelecommunications;
	
	public AuthorType(){
		super();
		uuid = ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_AUTHOR;

		authorPersons = new TreeSet<String>();
		authorInstitutions = new TreeSet<String>();
		authorRoles = new TreeSet<String>();
		authorSpecialtys = new TreeSet<String>();
//		authorTelecommunications = new TreeSet<String>();
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

		Iterator<String> apIterator = authorPersons.iterator();
		while(apIterator.hasNext()){
			String authorPerson = apIterator.next();
			if(authorPerson != null){
				OMElement slot = addSlot("authorPerson", new String[]{authorPerson});
				classification.addChild(slot);
			}	
		}
		Iterator<String> aiIterator = authorInstitutions.iterator();
		while(aiIterator.hasNext()){
			String authorInstitution = aiIterator.next();
			if(authorInstitution != null){
				OMElement slot = addSlot("authorInstitution", new String[]{authorInstitution});
				classification.addChild(slot);
			}
		}
		Iterator<String> arIterator = authorRoles.iterator();
		while(arIterator.hasNext()){
			String authorRole = arIterator.next();
			if(authorRole != null){
				OMElement slot = addSlot("authorRole", new String[]{authorRole});
				classification.addChild(slot);
			}
		}
		Iterator<String> asIterator = authorSpecialtys.iterator();
		while(arIterator.hasNext()){
			String authorSpecialty = asIterator.next();
			if(authorSpecialty != null){
				OMElement slot = addSlot("authorSpecialty", new String[]{authorSpecialty});
				classification.addChild(slot);
			}
		}
//		Iterator<String> atIterator = authorTelecommunications.iterator();
//		while(atIterator.hasNext()){
//			String authorTelecommunication = atIterator.next();
//			if(authorTelecommunication != null){
//				OMElement slot = addSlot("authorTelecommunication", new String[]{authorTelecommunication});
//				classification.addChild(slot);
//			}
//		}
		/* classification */
		return classification;
	}

	public void setEntryUUID(String entryUUID) {
		this.entryUUID = entryUUID;
	}
	
	public void addAuthorPerson(String authorPerson) {
		this.authorPersons.add(authorPerson);
	}
	public void addAuthorInstitution(String authorInstitution) {
		this.authorInstitutions.add(authorInstitution);
	}
	public void addAuthorRole(String authorRole) {
		this.authorRoles.add(authorRole);
	}
	public void addAuthorSpecialty(String authorSpecialty) {
		this.authorSpecialtys.add(authorSpecialty);
	}
}
