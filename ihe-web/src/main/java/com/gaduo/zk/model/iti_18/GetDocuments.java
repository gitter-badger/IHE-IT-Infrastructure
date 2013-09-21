/**
 * 
 */
package com.gaduo.zk.model.iti_18;

import java.util.List;

import org.apache.axiom.om.OMElement;

/**
 * @author Gaduo
 */
public class GetDocuments extends StoredQuery implements IParameter {

	private String id;
	private String type;

	public GetDocuments() {
		super();
		this.setType("EntryUUID");
		this.setId("urn:uuid:a5dbc44d-de63-4bb1-9352-3f43d84ba859");
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

}
