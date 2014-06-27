package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public abstract class General {
	public static Logger logger = Logger.getLogger(General.class);
	
	protected IAxiomUtil axiom = null;
	
	private String id;
	
	public General(){
		axiom = AxiomUtil.getInstance();
	}
	public abstract OMElement buildEbXML();
	public abstract boolean validate();
	
	protected OMElement addSlot(String name, String[] text){
		/* valueList */
		OMElement valueList = axiom.createOMElement(EbXML.ValueList, Namespace.RIM3);

		/* value */
		for (String t : text) {
			OMElement value = axiom.createOMElement(EbXML.Value, Namespace.RIM3);
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
	
	
	protected OMElement addNameOrDescription(String text, EbXML NameOrDescription) {
		/* LocalizedString */
		OMElement localizedString = null;
		if (text != null && !text.equals("")) {
			localizedString = axiom.createOMElement(
					EbXML.LocalizedString, Namespace.RIM3);
			localizedString.addAttribute("value", text, null);
		}
		/* LocalizedString */

		/* Name or Description*/
		OMElement name = axiom.createOMElement(NameOrDescription,
				Namespace.RIM3);
		if (localizedString != null ) {
			name.addChild(localizedString);
		}
		/* Name or Description*/
		return name;
	}
	

	protected OMElement buildClassification(String type, String value, String coding_scheme, String entryUUID, String uuid) {
		HashMap<String, String> code = extractCode(type, value);
		OMElement classification = null;
		if (code != null) {
			String localizedStringValue = code.get("Name");
			String slotValue = code.get("codeSystemName");
			String nodeRepresentation = code.get("nodeRepresentation");
			OMElement name = addNameOrDescription(localizedStringValue, EbXML.Name);
			
			OMElement slot = addSlot(coding_scheme, new String[] { slotValue });
			classification = addClassification(uuid, entryUUID, nodeRepresentation, name, new OMElement[] { slot });
		}
		return classification;
	}
	
	private HashMap<String, String> extractCode(String name, String code) {
		HashMap<String, String> valuelist = new HashMap<String, String>();
		String query = "Codes/CodeType[@name='" + name + "']/Code[@code='" + code + "']";
		Node node = MetadataType.codes.QueryNode(query);
		if(node == null){
			
			return null;
		}
		NamedNodeMap attrs = node.getAttributes();
		if (attrs != null) {
			valuelist.put("nodeRepresentation", attrs.getNamedItem("code").getNodeValue());
		}
		if (attrs != null) {
			valuelist.put("Name", attrs.getNamedItem("display").getNodeValue());
		}
		if (attrs != null) {
			valuelist.put("codeSystemName", attrs.getNamedItem("codingScheme").getNodeValue());
		}
		if (attrs != null) {
			valuelist.put("codeSystemVersion", "");
		}
		return valuelist;
	}

	private OMElement addClassification(String uuid, String entryUUID, String nodeRepresentation, OMElement name, OMElement[] slot) {
		MetadataType.ObjectRef.add(uuid);
		/* classification */
		OMElement classification = axiom.createOMElement(EbXML.Classification,Namespace.RIM3);
		classification.addAttribute("id", createUUID(), null);
		classification.addAttribute("classificationScheme", uuid, null);
		classification.addAttribute("classifiedObject", entryUUID, null);
		classification.addAttribute("nodeRepresentation", nodeRepresentation,null);
		for (OMElement s : slot) {
			classification.addChild(s);
		}
		if (name != null) {
			classification.addChild(name);
		}
		/* classification */
		return classification;
	}
	
	protected OMElement addExternalIdentifier(String uuid, String entryUUID, String value, OMElement name) {
		MetadataType.ObjectRef.add(uuid);

		/* ExternalIdentifier */
		OMElement externalIdentifier = axiom.createOMElement(EbXML.ExternalIdentifier, Namespace.RIM3);
		externalIdentifier.addAttribute("id", createUUID(), null);
		externalIdentifier.addAttribute("identificationScheme", uuid, null);
		externalIdentifier.addAttribute("registryObject", entryUUID, null);
		if (value != null)
			externalIdentifier.addAttribute("value", value, null);
		if (name != null) {
			externalIdentifier.addChild(name);
		}
		/* ExternalIdentifier */
		return externalIdentifier;
	}


	protected String createUUID() {
		UUID uid = UUID.randomUUID();
		return "urn:uuid:" + uid.toString();
	}

	protected String createTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}
	
	@XmlTransient
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
