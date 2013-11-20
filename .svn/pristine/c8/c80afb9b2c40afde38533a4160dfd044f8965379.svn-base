package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;
import com.gaduo.ihe.utility.PnRCommon;
import com.gaduo.ihe.utility._interface.ICommon;


public class DocumentRelationships extends XDSEntry {
	ICommon common = null;
	public DocumentRelationships(String sourceObject, String targetObject,
			String association) {
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

	public DocumentRelationships(String sourceObject, String targetObject,
			String notes, String association) {
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
