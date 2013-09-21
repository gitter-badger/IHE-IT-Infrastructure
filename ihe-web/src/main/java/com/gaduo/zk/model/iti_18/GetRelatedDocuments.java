/**
 * 
 */
package com.gaduo.zk.model.iti_18;

import java.util.List;

import org.apache.axiom.om.OMElement;

/**
 * @author Gaduo
 */
public class GetRelatedDocuments extends StoredQuery implements IParameter {

	private String id;
	private String type;
	private String associationTypes;

	public GetRelatedDocuments() {
		super();
		this.setType("EntryUUID");
		this.setId("urn:uuid:a5dbc44d-de63-4bb1-9352-3f43d84ba859");
		this.setAssociationTypes("HashMember");
	}

	public List<OMElement> getParameters() {
		if (id != null) {
			if (this.type.equals("EntryUUID")) {
				super.list.add(super.addParameter("$XDSDocumentEntryEntryUUID",
						id));
			}
			if (this.type.equals("UniqueId")) {
				super.list.add(super.addParameter("$XDSDocumentEntryUniqueId",
						id));
			}
			if (associationTypes != null) {
				super.list.add(super.addParameter("$AssociationTypes",
						associationTypes));
			}
			return super.list;
		}

		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssociationTypes() {
		return associationTypes;
	}

	public void setAssociationTypes(String associationTypes) {
		this.associationTypes = associationTypes;
	}

}
