/**
 * 
 */
package com.gaduo.zk.model.iti_18;

import java.util.List;

import org.apache.axiom.om.OMElement;

/**
 * @author Gaduo
 * 
 */
public class GetFoldersForDocument extends StoredQuery implements IParameter {
	private String id;
	private String associationTypes;;

	public GetFoldersForDocument() {
		super();
		setAssociationTypes("HashMember");
	}

	public List<OMElement> getParameters() {
		if (id != null) {
			if (associationTypes != null) {
				super.list.add(super.addParameter("$XDSAssociationStatus",
						associationTypes));
			}
			if (id != null) {
				super.list.add(super.addParameter("$XDSDocumentEntryUniqueId",
						id));
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

	public String getAssociationTypes() {
		return associationTypes;
	}

	public void setAssociationTypes(String associationTypes) {
		this.associationTypes = associationTypes;
	}

}
