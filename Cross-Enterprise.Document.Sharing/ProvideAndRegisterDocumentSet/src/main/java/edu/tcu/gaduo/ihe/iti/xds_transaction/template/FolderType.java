package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.sql.Timestamp;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.FolderConstants;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;

@XmlType(name = "Folder")
@XmlAccessorType (XmlAccessType.FIELD)
public class FolderType extends General {
	@XmlElement(name="Title")
	private String title;
	@XmlElement(name="Description")
	private String description;
	@XmlElement(name="Documents")
	protected DocumentsType documents;
	@XmlElement(name="FolderCodeList")
	private CodesType folderCodeList;
	@XmlElement(name="ExistingFolderId")
	private String existingFolderId;
	
	@XmlTransient
	private String sourcePatientId;
	@XmlTransient
	private final String objectType = ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_OBJECT;
	public FolderType(){
		super();
		this.setId(createUUID());
		documents = new DocumentsType();
		folderCodeList = new CodesType();
	}

	
	public DocumentsType getDocuments(){
		return documents;
	}

	public String getExistingFolderId() {
		return existingFolderId;
	}

	public void setSourcePatientId(String sourcePatientId) {
		this.sourcePatientId = sourcePatientId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addFolderCodeList(String codeList) {
		this.folderCodeList.addCode(codeList);
	}

	public void addDocument(DocumentType document) {
		this.documents.addDocument(document);
	}

	public void setExistingFolderId(String existingFolderId) {
		this.existingFolderId = existingFolderId;
	}
	
	@Override
	public boolean validate() {
		return false;
	}
	
	@Override
	public OMElement buildEbXML(){
		if(sourcePatientId == null){
			
		}
		
		OMElement root = axiom.createOMElement(EbXML.RegistryPackage, Namespace.RIM3);
		root.addAttribute("id", this.getId(), null);
		root.addAttribute("objectType", objectType, null);
		MetadataType.ObjectRef.add(objectType);
		
		// --Folder Time
		String lastUpdateTime = extractLastUpdateTime();
		if (lastUpdateTime != null) {
			OMElement slot = addSlot(FolderConstants.LAST_UPDATE_TIME, new String[] { lastUpdateTime });
			root.addChild(slot);
		}
		// // ---------------------Main
		if (title != null) {
			OMElement name = addNameOrDescription(title, EbXML.Name);
			root.addChild(name);
		}
		if (description != null) {
			OMElement name = addNameOrDescription(description, EbXML.Description);
			root.addChild(name);
		}
		// ---------------------Classification
		// --------FolderCodeList
		Iterator<String> iterator = folderCodeList.list.iterator();
		while(iterator.hasNext()){
			String value = iterator.next();
			if (value != null) {
				OMElement classification = buildClassification("folderCodeList", value, FolderConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_CODE);
				root.addChild(classification);
			}
		}
		// ---------------------ExternalIdentifier
		// --Folder SourcePatienId
		OMElement name;
		name = addNameOrDescription(FolderConstants.PATIENT_ID, EbXML.Name);
		OMElement externalIdentifier01 = addExternalIdentifier(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_PATIENT_IDENTIFICATION_SCHEME, this.getId(), sourcePatientId, name);
		root.addChild(externalIdentifier01);
		
		String uniqueId = MetadataType.SourceID + "." + MetadataType.IP + "." + MetadataType.bootTimestamp + "." + MetadataType.count;
		MetadataType.count++;
		name = addNameOrDescription(FolderConstants.UNIQUE_ID, EbXML.Name);
		OMElement externalIdentifier02 = addExternalIdentifier(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_UNIQUE_IDENTIFICATION_SCHEME, this.getId(), uniqueId, name);
		root.addChild(externalIdentifier02);
		return root;
	}
	
	private String extractLastUpdateTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}
	
	public OMElement buildEbXMLClassification(){
		/* classification */
		OMElement classification = axiom.createOMElement(EbXML.Classification, Namespace.RIM3);
		classification.addAttribute("id", createUUID(), null);
		classification.addAttribute("classificationNode", objectType, null);
		classification.addAttribute("classifiedObject", getId(), null);
		classification.addAttribute("nodeRepresentation", "", null);
		/* classification */
		return classification;
	}

}
