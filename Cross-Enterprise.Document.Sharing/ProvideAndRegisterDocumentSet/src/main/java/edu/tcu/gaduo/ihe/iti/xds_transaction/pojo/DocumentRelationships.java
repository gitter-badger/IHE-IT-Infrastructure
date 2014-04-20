package edu.tcu.gaduo.ihe.iti.xds_transaction.pojo;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.utility.PnRCommon;
import edu.tcu.gaduo.ihe.utility._interface.ICommon;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;

@Deprecated
public class DocumentRelationships extends XDSEntry {
	ICommon common = null;
	public DocumentRelationships(String sourceObject, String targetObject, String association) {
		super(EbXML.Association);
		this.common = new PnRCommon();
		String entryUUID = common.createUUID();
		if (entryUUID != null) {
			this.setId(entryUUID);
		}
		setSourceObject(sourceObject);
		setTargetObject(targetObject);
		setAssociationType(association);
		set_ObjectType(ProvideAndRegistryDocumentSet_B_UUIDs.ASSOCIATION);
	}

	public DocumentRelationships(String sourceObject, String targetObject, String notes, String association) {
		super(EbXML.Association);
		this.common = new PnRCommon();
		String entryUUID = common.createUUID();
		if (entryUUID != null) {
			this.setId(entryUUID);
		}
		setSourceObject(sourceObject);
		setTargetObject(targetObject);
		setAssociationType(association);
		set_ObjectType(ProvideAndRegistryDocumentSet_B_UUIDs.ASSOCIATION);

		OMElement slot = addSlot("SubmissionSetStatus", new String[] { notes });
		this.root.addChild(slot);
	}

	private void setSourceObject(String sourceObject) {
		root.addAttribute("sourceObject", sourceObject, null);
	}

	private void setTargetObject(String targetObject) {
		root.addAttribute("targetObject", targetObject, null);
	}

	private void setAssociationType(String associationType) {
		root.addAttribute("associationType", associationType, null);
	}

	private void set_ObjectType(String objectType) {
		root.addAttribute("objectType", objectType, null);
	}
}
