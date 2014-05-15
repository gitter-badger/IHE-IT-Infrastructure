package edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.tcu.gaduo.ihe.constants.DocumentEntryConstants;
import edu.tcu.gaduo.ihe.constants.DocumentRelationshipsConstants;
import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;
import edu.tcu.gaduo.ihe.utility.ws.Soap;
import edu.tcu.gaduo.ihe.utility.ws.SoapWithAttachment;
import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;
import gov.nist.registry.common2.io.Sha1Bean;

@XmlType(name = "Document")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentType extends General  {
	public static final int RPLC = 11974;
	public static final int XFRM = 11975;
	public static final int XFRM_RPLC = 11976;
	public static final int APND = 11977;
	public static final int SIGNS = 11978;
	
	@XmlElement(name="Title")
	protected String title;
	@XmlElement(name="Description")
	protected String description;
	@XmlElement(name="CreationTime")
	protected String creationTime;
	@XmlElement(name="MimeType")
	protected String mimeType;
	@XmlElement(name="Content")
	protected String content;
	@XmlElement(name="DocumentAuthors")
	protected DocumentAuthorsType authors;
	@XmlElement(name="ClassCode")
	protected String classCode;
	@XmlElement(name="FormatCode")
	protected String formatCode;
	@XmlElement(name="HealthcareFacilityTypeCode")
	protected String healthcareFacilityTypeCode;
	@XmlElement(name="PracticeSettingCode")
	protected String practiceSettingCode;
	@XmlElement(name="TypeCode")
	protected String typeCode;
	@XmlElement(name="ConfidentialityCodes")
	protected CodesType confidentialityCode;
	@XmlElement(name="EventCodeList")
	protected CodesType eventCodeList;
	@XmlElement(name="Operation")
	protected int operation = 0;
	@XmlElement(name="ExistingDocumentId")
	protected String existingDocumentId; 
	
	@XmlTransient
	private String sourcePatientId;
	@XmlTransient
	private PatientInfoType patientInfo;
	@XmlTransient
	private final String objectType = ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_OBJECT;

	public DocumentType(){
		super();
		this.setId(createUUID());
		authors = new DocumentAuthorsType();
		confidentialityCode = new CodesType();
		eventCodeList = new CodesType();
	}
	
	public String getExistingDocumentId() {
		return existingDocumentId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	/**
	 * 
	 * @param content
	 * Content 可以是檔案.
	 * Content can be a Filte.
	 */
	public void setContent(File file) {
		FileInputStream fis;
		try {
//			this.setTitle(file.getName());
//			this.setDescription(file.getName());
			fis = new FileInputStream(file);
			byte[] input = IOUtils.toByteArray(fis);
			setContent(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		creationTime = extractCreationTime(file);
	}
	
	
	/**
	 * 
	 * @param base64
	 * Content 可以是 Base64 字串.
	 * Content can be a base64 String.
	 */
	public void setContent(String base64) {
		byte[] input = Base64.decodeBase64(base64.getBytes());
		setContent(input);
	}
	
	/**
	 * 
	 * @param input
	 * Content 可以是 byte 陣列.
	 * Content can be a byte array.
	 */
	public void setContent(byte[] input){
		mimeType = extractMimeType(title);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(input, mimeType));
		ISoap soap = ProvideAndRegisterDocumentSet.soap;
		boolean isSWA = (soap != null) ? soap.isSWA() : false;
		if (!isSWA) {
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMText binaryData = fac.createOMText(handler, true);
			binaryData.setOptimize(true);
			this.content = binaryData.getText();
		} else {
			if(ProvideAndRegisterDocumentSet.soap instanceof SoapWithAttachment){
				this.content = ((SoapWithAttachment)ProvideAndRegisterDocumentSet.soap).addAttachment(input, mimeType);
			}else if(ProvideAndRegisterDocumentSet.soap instanceof Soap){
				this.content = ((Soap)ProvideAndRegisterDocumentSet.soap).addAttachment(handler);
			}
		}
	}

	private String extractCreationTime(File file) {
		String value = null;
		if (file != null) {
			value = new Timestamp(file.lastModified()).toString();
			value = value.replaceAll("\\D+", "").substring(0, 14);
		}
		return value;
	}
	
	public void setMimeType(String mimeType){
		this.mimeType = mimeType;
	}
	
	public void setSourcePatientId(String sourcePatientId) {
		this.sourcePatientId = sourcePatientId;
	}

	public void setPatientInfo(PatientInfoType patientInfo){
		this.patientInfo = patientInfo;
	}

	public void addAuthor(DocumentAuthorType author){
		this.authors.addAuthor(author);
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public void setFormatCode(String formatCode) {
		this.formatCode = formatCode;
	}
	public void setHealthcareFacilityTypeCode(String healthcareFacilityTypeCode) {
		this.healthcareFacilityTypeCode = healthcareFacilityTypeCode;
	}
	public void setPracticeSettingCode(String practiceSettingCode) {
		this.practiceSettingCode = practiceSettingCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public void addConfidentialityCode(String e){
		this.confidentialityCode.addCode(e);
	}
	public void addEventCodeList(String e){
		this.eventCodeList.addCode(e);
	}
	
	public void setExistingDocumentId(String existingDocumentId) {
		this.existingDocumentId = existingDocumentId;
	}

	public void setOperation(int flag) throws Exception{
		if( flag != DocumentType.APND && 
			flag != DocumentType.RPLC &&
			flag != DocumentType.XFRM && 
			flag != DocumentType.XFRM_RPLC &&
			flag != DocumentType.SIGNS)
			throw new Exception("");
		this.operation = flag;
	}
	
	@Override
	public boolean validate() {
		if( operation == 0 && existingDocumentId == null)
			return true;			
		if(	operation != DocumentType.APND && 
			operation != DocumentType.RPLC &&
			operation != DocumentType.XFRM && 
			operation != DocumentType.XFRM_RPLC && 
			operation != DocumentType.SIGNS){ // 
			logger.info("No Operation" + operation);
			return false;
		}else{
			if(existingDocumentId != null && !existingDocumentId.equals("")) {// 有 Relationship
				logger.info("Has Relationship =\t" + operation);
				return true;
			}else{
				return false;
			}
		}
	}
	
	@Override
	public OMElement buildEbXML() {
		if(!validate()){
			return null;
		}
		OMElement root = axiom.createOMElement(EbXML.ExtrinsicObject, Namespace.RIM3);
		root.addAttribute("id", this.getId(), null);
		root.addAttribute("objectType", objectType, null);
		MetadataType.ObjectRef.add(objectType);
		if(mimeType == null)
			mimeType = extractMimeType(title);
		root.addAttribute("mimeType", mimeType, null);// MimeType
		root.addAttribute("status", Namespace.APPROVED.getNamespace(), null);
		
		if(creationTime == null)
			creationTime = createTime();
		if (creationTime != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.CREATION_TIME, new String[] { creationTime });
			root.addChild(slot);
		}
		
		String serviceStartTime = createTime();
		if (serviceStartTime != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.SERVICE_START_TIME, new String[] { serviceStartTime });
			root.addChild(slot);
		}
		String serviceStopTime = createTime();
		if (serviceStopTime != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.SERVICE_STOP_TIME, new String[] { serviceStopTime });
			root.addChild(slot);
		}
		String languageCode = "zh-tw";
		if (languageCode != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.LANGUAGE_CODE, new String[] { languageCode });
			root.addChild(slot);
		}
		if (sourcePatientId != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.SOURCE_PATIENT_ID, new String[] { sourcePatientId });
			root.addChild(slot);
		}
		
		if(patientInfo != null){
			OMElement pInfo = patientInfo.buildEbXML();
			root.addChild(pInfo);
		}
		
		if (content != null && !content.contains("http")) {
			String hash = extractHash(content);
			if (hash != null) {
				OMElement slot = this.addSlot(DocumentEntryConstants.HASH, new String[] { hash.toString() });
				root.addChild(slot);
			}

			long size = extractSize(content);
			if (size > 0) {
				OMElement slot = this.addSlot(DocumentEntryConstants.SIZE, new String[] { size + "" });
				root.addChild(slot);
			}
		} else {
			String[] url = extractURL(content);
			if (url != null) {
				OMElement slot = this.addSlot(DocumentEntryConstants.URI, url);
				root.addChild(slot);
			}
		}
		// ---------------------Main
		if (title != null) {
			OMElement name = this.addNameOrDescription(title, EbXML.Name);// Title
			root.addChild(name);
		}
		if (description != null) {
			OMElement name = this.addNameOrDescription(description, EbXML.Description);
			root.addChild(name);
		}
		// ---------------------Author
		Iterator<DocumentAuthorType> iterator = authors.list.iterator();
		while(iterator.hasNext()){
			DocumentAuthorType author = iterator.next();
			author.setEntryUUID(this.getId());
			OMElement element = author.buildEbXML();
			root.addChild(element);
		}
		// ---------------------Classification

		// ---ConfidentialityCode
		Iterator<String> cIterator = confidentialityCode.list.iterator();
		while(cIterator.hasNext()){
			String value = cIterator.next();
			if (value != null) {
				value = value.trim();
				OMElement classification = buildClassification("confidentialityCode", value, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_CONFIDENTIALITY_CODE);
				root.addChild(classification);
			}
		}
		// ---EventCodeList
		Iterator<String> eIterator = eventCodeList.list.iterator();
		while(eIterator.hasNext()){
			String value = eIterator.next();
			if (value != null) {
				value = value.trim();
				OMElement classification = buildClassification("eventCodeList", value, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_EVENT_CODE);
				root.addChild(classification);
			}
		}
		// ---ClassCode
		if (classCode != null) {
			classCode = classCode.trim();
			OMElement classification = buildClassification("classCode", classCode, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_CLASS_CODE);
			root.addChild(classification);
		}
		
		// ---FormatCode
		if (formatCode != null) {
			formatCode = formatCode.trim();
			OMElement classification = buildClassification("formatCode", formatCode, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_FORMAT_CODE);
			root.addChild(classification);
		}
		// ---HealthcareFacilityTypeCode
		if (healthcareFacilityTypeCode != null) {
			healthcareFacilityTypeCode = healthcareFacilityTypeCode.trim();
			OMElement classification = buildClassification("healthcareFacilityTypeCode", healthcareFacilityTypeCode, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_HEALTH_CARE_FACILITY_CODE);
			root.addChild(classification);
		}
		// ---PracticeSettingCode
		if (practiceSettingCode != null) {
			practiceSettingCode = practiceSettingCode.trim();
			OMElement classification = buildClassification("practiceSettingCode", practiceSettingCode, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_PRACTICE_SETTING_CODE);
			root.addChild(classification);
		}
		// ---TypeCode
		if (typeCode != null) {
			typeCode = typeCode.trim();
			OMElement classification = buildClassification("typeCode", typeCode, DocumentEntryConstants.CODING_SCHEME, this.getId(), ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_TYPE_CODE);
			root.addChild(classification);
		}

		// ---------------------ExternalIdentifier
		// ---PATIENT_ID
		OMElement name;
		name = addNameOrDescription(DocumentEntryConstants.PATIENT_ID, EbXML.Name);
		OMElement externalIdentifier01 = addExternalIdentifier(ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_PATIENT_IDENTIFICATION_SCHEME, getId(), sourcePatientId, name);
		root.addChild(externalIdentifier01);
		String sourceId = MetadataType.SourceID;
		// ---UNIQUE_ID
		String uniqueId = sourceId + "." + MetadataType.IP + "." + MetadataType.bootTimestamp + "." + MetadataType.count;
		MetadataType.count++;
		name = addNameOrDescription(DocumentEntryConstants.UNIQUE_ID, EbXML.Name);
		OMElement externalIdentifier02 = addExternalIdentifier(ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME, getId(), uniqueId, name);
		root.addChild(externalIdentifier02);
		return root;
	}
	
	public OMElement buildEbXMLRelationship(){
		OMElement element = null;
		AssociationType association;
		switch(operation){
		case 11974:
			// Relationship RPLC 11974
			association = new AssociationType();
			association.setSourceObject(getId());
			association.setTargetObject(existingDocumentId);
			association.setAssociation(DocumentRelationshipsConstants.RPLC);
			element = association.buildEbXML();
			break;
		case 11975:
			// Relationship XFRM 11975
			association = new AssociationType();
			association.setSourceObject(getId());
			association.setTargetObject(existingDocumentId);
			association.setAssociation(DocumentRelationshipsConstants.XFRM);
			element = association.buildEbXML();
			break;
		case 11976:
			// Relationship XFRM_RPLC 11976
			association = new AssociationType();
			association.setSourceObject(getId());
			association.setTargetObject(existingDocumentId);
			association.setAssociation(DocumentRelationshipsConstants.XFRM_RPLC);
			element = association.buildEbXML();
			break;
		case 11977:
			// Relationship APND 11977
			association = new AssociationType();
			association.setSourceObject(getId());
			association.setTargetObject(existingDocumentId);
			association.setAssociation(DocumentRelationshipsConstants.APND);
			element = association.buildEbXML();
			break;
		case 11978:
			// Relationship Signs 
			association = new AssociationType();
			association.setSourceObject(getId());
			association.setTargetObject(existingDocumentId);
			association.setAssociation(DocumentRelationshipsConstants.Signs);
			element = association.buildEbXML();
			break;
		}
		return element;
	}
	
	public OMElement buildEbXMLDocument(){
		if (content != null && content.contains("http")) {
			logger.info(content);
			return null;
		}

		if(!validate()){
			return null;
		}
		OMElement root = axiom.createOMElement(EbXML.Document, Namespace.IHE);
		root.addAttribute("id", this.getId(), null);
		ISoap soap = ProvideAndRegisterDocumentSet.soap;
		boolean swa = (soap != null) ? soap.isSWA() : false;
		if (!swa) {
			root.setText(content);
		} else {
			OMNamespace xop = axiom.createNamespace("http://www.w3.org/2004/08/xop/include", "xop");
			OMElement inclue = axiom.createOMElement("Include", xop);
			inclue.addAttribute("href", content, null);
			root.addChild(inclue);
		}
		return root;
	}
	
	private String extractMimeType(String uri) {
		String type = null;
		int dotPos = uri.lastIndexOf(".");
		String extension = uri.substring(dotPos + 1).toLowerCase();
		Node node = null;
		node = MetadataType.codes.QueryNode("Codes/CodeType[@name='mimeType']/Code[@ext='" + extension + "']");
		if (node != null) {
			if (node != null) {
				NamedNodeMap attrs = node.getAttributes();
				if (attrs != null) {
					type = attrs.getNamedItem("code").getNodeValue();
					return type;
				}
			}
		}
		node = MetadataType.web.QueryNode("web-app/mime-mapping/extension[text()='" + extension + "']");
		if (node != null) {
			node = node.getParentNode();
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				if (node.getNodeName().equalsIgnoreCase(DocumentEntryConstants.MIME_TYPE)) {
					return node.getTextContent();
				}
			}
		}
		return null;
	}
	
	private long extractSize(String content) {
		byte[] bytes = Base64.decodeBase64(content.getBytes());
		return bytes.length;
	}

	private String extractHash(String content) {
		byte[] bytes = Base64.decodeBase64(content.getBytes());
		String hash_value = null;
		try {
			hash_value = (new Sha1Bean()).getSha1(bytes);
		} catch (Exception e) {
		}
		return hash_value;
	}
	
	private String[] extractURL(String content) {
		int block = 128;
		int size = (content.length() / 128) + 1;
		String[] token = new String[size];
		for (int i = 0; i < token.length; i++) {
			if ((i + 1) * block <= this.content.length())
				token[i] = (i+1) + "|" + this.content.substring(i * block, (i + 1) * block);
			else
				token[i] = (i+1) + "|" + this.content.substring(i * block);
		}
		return token;
	}
}
