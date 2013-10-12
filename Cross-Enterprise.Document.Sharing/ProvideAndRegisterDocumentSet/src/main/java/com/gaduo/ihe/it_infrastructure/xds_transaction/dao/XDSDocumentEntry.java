package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import gov.nist.registry.common2.io.Sha1Bean;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMText;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gaduo.ihe.constants.DocumentEntryConstants;
import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.Namespace;
import com.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;
import com.gaduo.ihe.it_infrastructure.xds_transaction.service.ProvideAndRegisterDocumentSet;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.Common;
import com.gaduo.ihe.utility.PnRCommon;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility._interface.ICommon;
import com.gaduo.webservice._interface.ISoap;

public class XDSDocumentEntry extends XDSEntry {
	private File doc = null;
	private String content = null;
	private String mimeType;

	public XDSDocumentEntry(String uri, OMElement request, OMElement document) {
		super(EbXML.ExtrinsicObject);
		ICommon common = new PnRCommon();
		IAxiomUtil axiom = new AxiomUtil();
		mimeType = extractMimeType(uri);
		QName qname = new QName("Content");
		OMElement e = document.getFirstChildWithName(qname);
		ISoap soap = ProvideAndRegisterDocumentSet.soap;
		boolean isMTOM = (soap != null) ? soap.isMTOM_XOP() : true;
		setContent(e.getText(), isMTOM);

		doc = (uri != null) ? new File(uri) : null;
		String title = extractTitle(document);

		String entryUUID = common.createUUID();
		if (entryUUID != null) {
			this.setId(entryUUID);
		}

		if (mimeType != null) {
			this.setMimeType(mimeType); // MimeType
		}
		this.setStatus(Namespace.APPROVED.getNamespace()); // AvailabilityStatus
		this.setObjectType(ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_OBJECT);
		// // ---------------------Slot
		String creationTime = extractCreationTime(doc);
		if (creationTime != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.CREATION_TIME,
					new String[] { creationTime });
			root.addChild(slot);
		}
		String serviceStartTime = common.createTime();
		if (serviceStartTime != null) {
			OMElement slot = this.addSlot(
					DocumentEntryConstants.SERVICE_START_TIME,
					new String[] { serviceStartTime });
			root.addChild(slot);
		}
		String serviceStopTime = common.createTime();
		if (serviceStopTime != null) {
			OMElement slot = this.addSlot(
					DocumentEntryConstants.SERVICE_STOP_TIME,
					new String[] { serviceStopTime });
			root.addChild(slot);
		}
		String languageCode = extractLanguageCode();
		if (languageCode != null) {
			OMElement slot = this.addSlot(DocumentEntryConstants.LANGUAGE_CODE,
					new String[] { languageCode });
			root.addChild(slot);
		}
		String sourcePatientId = extractSourcePatientId(request);
		if (sourcePatientId != null) {
			OMElement slot = this.addSlot(
					DocumentEntryConstants.SOURCE_PATIENT_ID,
					new String[] { sourcePatientId });
			root.addChild(slot);
		}
		ArrayList<String> sourcePatientInfo = extractSourcePatientInfo(request);

		if (!sourcePatientInfo.isEmpty()) {
			OMElement slot = this.addSlot(
					DocumentEntryConstants.SOURCE_PATIENT_INFO,
					sourcePatientInfo.toArray(new String[1]));
			root.addChild(slot);
		}
		if (title != null && !title.contains("http")) {
			String hash = extractHash();
			if (hash != null) {
				OMElement slot = this.addSlot(DocumentEntryConstants.HASH,
						new String[] { hash.toString() });
				root.addChild(slot);
			}

			long size = extractSize();
			if (size > 0) {
				OMElement slot = this.addSlot(DocumentEntryConstants.SIZE,
						new String[] { size + "" });
				root.addChild(slot);
			}
		} else {
			String[] url = extractURL(document);
			if (url != null) {
				OMElement slot = this.addSlot(DocumentEntryConstants.URI, url);
				root.addChild(slot);
			}
		}

		/*
		 * String repositoryUniqueId = extractRepositoryUniqueId(request); if
		 * (repositoryUniqueId != null) { OMElement slot = this.addSlot(
		 * DocumentEntryConstants.REPOSITORY_UNIQUE_ID, new String[] {
		 * repositoryUniqueId }); root.addChild(slot); }
		 */
		// // ---------------------Main
		if (title != null) {
			OMElement name = this.addNameOrDescription(title, EbXML.Name);// Title
			this.root.addChild(name);
		}
		String description = extractDescription(document);
		if (description != null) {
			OMElement name = this.addNameOrDescription(description,
					EbXML.Description);
			this.root.addChild(name);
		}
		/*OMElement versionInfo = addVersionInfo("1.1");
		this.root.addChild(versionInfo);*/
		// ---------------------Classification
		HashMap<String, String> authors = extractAuthors(request);
		if (!authors.isEmpty()) {
			buildAuthors(authors, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_AUTHOR);
		}

		HashMap<String, String> eventCode = extractEventCode();
		if (eventCode != null) {
			buildAuthors(authors, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_EVENT_CODE);
		}
		String value = null;

		// ---ClassCode
		value = axiom.getValueOfType("ClassCode", request);
		if (value != null) {
			value = value.trim();
			buildClassification("classCode", value,
					DocumentEntryConstants.CODING_SCHEME, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_CLASS_CODE);
		}
		// ---ConfidentialityCode
		value = axiom.getValueOfType("ConfidentialityCode", request);
		if (value != null) {
			value = value.trim();
			buildClassification(
					"confidentialityCode",
					value,
					DocumentEntryConstants.CODING_SCHEME,
					this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_CONFIDENTIALITY_CODE);
		}
		// ---EventCodeList
		value = axiom.getValueOfType("EventCodeList", request);
		if (value != null) {
			value = value.trim();
			buildClassification("eventCodeList", value,
					DocumentEntryConstants.CODING_SCHEME, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_EVENT_CODE);
		}
		// ---FormatCode
		value = axiom.getValueOfType("FormatCode", request);
		if (value != null) {
			value = value.trim();
			buildClassification("formatCode", value,
					DocumentEntryConstants.CODING_SCHEME, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_FORMAT_CODE);
		}
		// ---HealthcareFacilityTypeCode
		value = axiom.getValueOfType("HealthcareFacilityTypeCode", request);
		if (value != null) {
			value = value.trim();
			buildClassification(
					"healthcareFacilityTypeCode",
					value,
					DocumentEntryConstants.CODING_SCHEME,
					this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_HEALTH_CARE_FACILITY_CODE);
		}
		// ---PracticeSettingCode
		value = axiom.getValueOfType("PracticeSettingCode", request);
		if (value != null) {
			value = value.trim();
			buildClassification(
					"practiceSettingCode",
					value,
					DocumentEntryConstants.CODING_SCHEME,
					this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_PRACTICE_SETTING_CODE);
		}
		// ---TypeCode
		value = axiom.getValueOfType("TypeCode", request);
		if (value != null) {
			value = value.trim();
			buildClassification("typeCode", value,
					DocumentEntryConstants.CODING_SCHEME, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_TYPE_CODE);
		}

		// ---------------------ExternalIdentifier
		// ---PATIENT_ID
		OMElement name;
		name = addNameOrDescription(DocumentEntryConstants.PATIENT_ID,
				EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_PATIENT_IDENTIFICATION_SCHEME,
				entryUUID, sourcePatientId, name);

		// ---UNIQUE_ID
		String uniqueId = PnRCommon.OID + "." + Common.IP + "."
				+ PnRCommon.bootTimestamp + "." + PnRCommon.count;
		PnRCommon.count++;
		name = addNameOrDescription(DocumentEntryConstants.UNIQUE_ID,
				EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME,
				entryUUID, uniqueId, name);
		setUniqueId(uniqueId);
	}

	private HashMap<String, String> extractEventCode() {
		return null;
	}

	private String extractMimeType(String uri) {
		String type = null;
		int dotPos = uri.lastIndexOf(".");
		String extension = uri.substring(dotPos + 1).toLowerCase();
		Node node = null;
		node = PnRCommon.codes
				.QueryNode("Codes/CodeType[@name='mimeType']/Code[@ext='"
						+ extension + "']");
		if (node != null) {
			if (node != null) {
				NamedNodeMap attrs = node.getAttributes();
				if (attrs != null) {
					type = attrs.getNamedItem("code").getNodeValue();
					return type;
				}
			}
		}
		node = PnRCommon.web
				.QueryNode("web-app/mime-mapping/extension[text()='"
						+ extension + "']");
		if (node != null) {
			node = node.getParentNode();
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				if (node.getNodeName().equalsIgnoreCase("mime-type")) {
					return node.getTextContent();
				}
			}
		}
		return null;
	}

	private String extractLanguageCode() {
		String value = "zh-tw";
		return value;
	}

	private String extractCreationTime(File file) {
		String value = null;
		if (file != null) {
			value = new Timestamp(file.lastModified()).toString();
			value = value.replaceAll("\\D+", "").substring(0, 14);
		}
		return value;
	}

	private ArrayList<String> extractSourcePatientInfo(OMElement request) {
		ArrayList<String> valuelist = new ArrayList<String>();
		QName qname = new QName("SourcePatientInfo");
		OMElement e = request.getFirstChildWithName(qname);
		this.getPID(valuelist, "PID-3", e);
		this.getPID(valuelist, "PID-5", e);
		this.getPID(valuelist, "PID-7", e);
		this.getPID(valuelist, "PID-8", e);
		this.getPID(valuelist, "PID-11", e);
		return valuelist;
	}

	private ArrayList<String> getPID(ArrayList<String> valuelist, String pid,
			OMElement e) {
		QName qname = new QName(pid);
		OMElement child = e.getFirstChildWithName(qname);
		String value = (child != null) ? child.getText() : null;
		if (value != null && value.length() > 0) {
			value = pid + "|" + value;
			valuelist.add(value);
		}
		return valuelist;
	}

	private long extractSize() {
		byte[] bytes = base64ToByteArray();
		return bytes.length;
	}

	private String extractHash() {
		byte[] bytes = base64ToByteArray();
		String hash_value = null;
		try {
			hash_value = (new Sha1Bean()).getSha1(bytes);
		} catch (Exception e) {
		}
		return hash_value;
	}

	private byte[] base64ToByteArray() {
		return Base64.decodeBase64(this.content.getBytes());
	}

	private String[] extractURL(OMElement request) {
		int block = 128;
		int size = (this.content.length() / 128) + 1;
		String[] token = new String[size];
		for (int i = 0; i < token.length; i++) {
			if ((i + 1) * block <= this.content.length())
				token[i] = i + "|"
						+ this.content.substring(i * block, (i + 1) * block);
			else
				token[i] = i
						+ "|"
						+ this.content.substring(i * block,
								this.content.length());
		}
		return token;
	}

	/*
	 * private String extractRepositoryUniqueId(OMElement request) { QName qname
	 * = new QName("RepositoryUniqueId"); OMElement e =
	 * request.getFirstChildWithName(qname); String value = null; if(e != null)
	 * value = e.getText(); return value; }
	 */

	public String getContent() {
		return content;
	}

	public void setContent(String Content, boolean isOptimize) {
		byte[] input = Base64.decodeBase64(Content.getBytes());
		DataHandler handler = new DataHandler(new ByteArrayDataSource(input,
				mimeType));
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMText binaryData = fac.createOMText(handler, isOptimize);
		binaryData.setOptimize(isOptimize);
		if (isOptimize) {
			this.content = binaryData.getText();
		} else {
			handler = (DataHandler) binaryData.getDataHandler();
			this.content = ProvideAndRegisterDocumentSet.soap
					.addAttachment(handler);
		}
	}

	// private OMText getBinaryNode(OMElement document) {
	// @SuppressWarnings("unchecked")
	// Iterator<OMNode> childrenIterator = document.getChildren();
	// while (childrenIterator.hasNext())
	// {
	// OMNode container = childrenIterator.next();
	// if (container instanceof OMText /*&&
	// StringUtils.isNotBlank(((OMText)container).getText())*/)
	// {
	// return (OMText)container;
	// }
	// }
	// return null;
	// }

}
