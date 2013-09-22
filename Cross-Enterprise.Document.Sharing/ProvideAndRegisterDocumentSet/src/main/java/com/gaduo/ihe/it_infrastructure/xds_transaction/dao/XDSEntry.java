package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.Namespace;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.PnRCommon;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility._interface.ICommon;

public class XDSEntry {
	protected OMElement root;
	protected ICommon common = null;
	protected IAxiomUtil axiom = null;
	protected OMElement UniqueId = null;

	public XDSEntry() {
		this.common = new PnRCommon();
		this.axiom = new AxiomUtil();
	}

	XDSEntry(EbXML rootTag) {
		this.common = new PnRCommon();
		this.axiom = new AxiomUtil();
		root = axiom.createOMElement(rootTag, Namespace.RIM3);
	}

	public String getId() {
		QName qname = new QName("id");
		String id = root.getAttributeValue(qname);
		return id;
	}

	void setId(String entryUUID) {
		root.addAttribute("id", entryUUID, null);
	}

	public OMElement getUniqueId() {
		return UniqueId;
	}

	void setUniqueId(String UniqueId) {
		IAxiomUtil axiom = new AxiomUtil();
		this.UniqueId = axiom.createOMElement("UniquerId", null, "");
		this.UniqueId.setText(UniqueId);
	}

	void setMimeType(String mimeType) {
		root.addAttribute("mimeType", mimeType, null);
	}

	void setStatus(String status) {
		root.addAttribute("status", status, null);
	}

	String getObjectType() {
		QName qname = new QName("objectType");
		return root.getAttributeValue(qname);
	}

	void setObjectType(String objectType) {
		root.addAttribute("objectType", objectType, null);
		PnRCommon.ObjectRef.add(objectType);
	}

	void buildClassification(String type, String value, String coding_scheme,
			String entryUUID, String uuid) {
		HashMap<String, String> code = extractCode(type, value);
		if (code != null) {
			String localizedStringValue = code.get("Name");
			String slotValue = code.get("codeSystemName");
			String nodeRepresentation = code.get("nodeRepresentation");
			OMElement name = addNameOrDescription(localizedStringValue,
					EbXML.Name);
			OMElement slot = addSlot(coding_scheme, new String[] { slotValue });
			addClassification(uuid, entryUUID, nodeRepresentation, name,
					new OMElement[] { slot });
		}
	}

	void addClassification(String uuid, String entryUUID,
			String nodeRepresentation, OMElement name, OMElement[] slot) {
		PnRCommon.ObjectRef.add(uuid);
		/* classification */
		OMElement classification = axiom.createOMElement(EbXML.Classification,
				Namespace.RIM3);
		classification.addAttribute("id", common.createUUID(), null);
		classification.addAttribute("classificationScheme", uuid, null);
		classification.addAttribute("classifiedObject", entryUUID, null);
		classification.addAttribute("nodeRepresentation", nodeRepresentation,
				null);
		for (OMElement s : slot) {
			classification.addChild(s);
		}
		if (name != null) {
			classification.addChild(name);
		}
		/* classification */
		this.root.addChild(classification);
	}

	void addExternalIdentifier(String uuid, String entryUUID, String value,
			OMElement name) {
		PnRCommon.ObjectRef.add(uuid);

		/* ExternalIdentifier */
		OMElement externalIdentifier = axiom.createOMElement(
				EbXML.ExternalIdentifier, Namespace.RIM3);
		externalIdentifier.addAttribute("id", common.createUUID(), null);
		externalIdentifier.addAttribute("identificationScheme", uuid, null);
		externalIdentifier.addAttribute("registryObject", entryUUID, null);
		if (value != null)
			externalIdentifier.addAttribute("value", value, null);
		if (name != null) {
			externalIdentifier.addChild(name);
		}
		this.root.addChild(externalIdentifier);
		/* ExternalIdentifier */
	}

	OMElement addNameOrDescription(String text, EbXML NameOrDescription) {
		/* LocalizedString */
		OMElement localizedString = axiom.createOMElement(
				EbXML.LocalizedString, Namespace.RIM3);
		if (!text.equals("")) {
			localizedString.addAttribute("value", text, null);
		}
		/* LocalizedString */

		/* Name */
		OMElement name = axiom.createOMElement(NameOrDescription,
				Namespace.RIM3);
		if (localizedString != null) {
			name.addChild(localizedString);
		}
		/* Name */
		return name;
	}

	OMElement addSlot(String name, String[] text) {
		/* valueList */
		OMElement valueList = axiom.createOMElement(EbXML.ValueList,
				Namespace.RIM3);

		/* value */
		for (String t : text) {
			OMElement value = axiom
					.createOMElement(EbXML.Value, Namespace.RIM3);
			value.setText(t);
			valueList.addChild(value);
		}
		/* value */
		/* valueList */

		/* Slot */
		OMElement slot = axiom.createOMElement(EbXML.Slot, Namespace.RIM3);
		slot.addAttribute("name", name, null);
		slot.addChild(valueList);
		/* Slot */
		return slot;
	}

	HashMap<String, String> extractCode(String name, String display) {
		HashMap<String, String> valuelist = new HashMap<String, String>();
		String query = "Codes/CodeType[@name='" + name + "']/Code[@code='"
				+ display + "']";
		Node node = PnRCommon.codes.QueryNode(query);
		NamedNodeMap attrs = node.getAttributes();
		if (attrs != null) {
			valuelist.put("nodeRepresentation", attrs.getNamedItem("code")
					.getNodeValue());
		}
		if (attrs != null) {
			valuelist.put("Name", attrs.getNamedItem("display").getNodeValue());
		}
		if (attrs != null) {
			valuelist.put("codeSystemName", attrs.getNamedItem("codingScheme")
					.getNodeValue());
		}
		if (attrs != null) {
			valuelist.put("codeSystemVersion", "");
		}
		return valuelist;
	}

	String extractSourcePatientId(OMElement request) {
		String value = null;
		value = axiom.getValueOfType("SourcePatientId", request);
		return value;
	}

	void buildAuthors(HashMap<String, String> authors, String entryUUID,
			String uuid) {
		ArrayList<OMElement> slotlist = new ArrayList<OMElement>();
		Iterator<String> iterator = authors.keySet().iterator();
		while (iterator.hasNext()) {
			ArrayList<String> valuelist = new ArrayList<String>();
			String key = iterator.next();
			String value = authors.get(key);
			if (value != null) {
				valuelist.add(value);
				OMElement slot = addSlot(key, new String[] { value });
				slotlist.add(slot);
			}
		}
		addClassification(uuid, entryUUID, "", null,
				slotlist.toArray(new OMElement[1]));
	}

	HashMap<String, String> extractAuthors(OMElement request) {
		String value = "";
		HashMap<String, String> valuelist = new HashMap<String, String>();

		QName qname = new QName("Author");
		OMElement element = request.getFirstChildWithName(qname);

		/* AuthorPerson */
		value = axiom.getValueOfType("AuthorPerson", element);
		valuelist.put("authorPerson", value);
		/* AuthorInstitution */
		value = axiom.getValueOfType("AuthorInstitution", element);
		valuelist.put("authorInstitution", value);
		/* AuthorRole */
		value = axiom.getValueOfType("AuthorRole", element);
		valuelist.put("authorRole", value);
		/* AuthorSpecialty */
		value = axiom.getValueOfType("AuthorSpecialty", element);
		valuelist.put("authorSpecialty", value);
		return valuelist;
	}

	public String extractTitle(OMElement request) {
		QName qname;
		qname = new QName("Title");
		OMElement e = request.getFirstChildWithName(qname);
		String value = null;
		if (e != null) {
			value = e.getText();
		}
		return value;
	}

	public String extractDescription(OMElement request) {
		QName qname = new QName("Description");
		OMElement e = request.getFirstChildWithName(qname);
		String value = null;
		if (e != null) {
			value = e.getText();
		}
		return value;
	}

	public OMElement getClassifsication() {
		/* classification */
		OMElement classification = axiom.createOMElement(EbXML.Classification,
				Namespace.RIM3);
		classification.addAttribute("id", common.createUUID(), null);
		classification.addAttribute("classificationNode", this.getObjectType(),
				null);
		classification.addAttribute("classifiedObject", this.getId(), null);
		classification.addAttribute("nodeRepresentation", "", null);
		/* classification */
		return classification;
	}

	public OMElement addObjectRef(String uuid) {
		OMElement ObjectRef = axiom.createOMElement(EbXML.ObjectRef,
				Namespace.RIM3);
		ObjectRef.addAttribute("id", uuid, null);
		return ObjectRef;
	}

	public OMElement getRoot() {
		return root;
	}

	public void setRoot(OMElement root) {
		this.root = root;
	}

	public void setUniqueId(OMElement uniqueId) {
		UniqueId = uniqueId;
	}

}
