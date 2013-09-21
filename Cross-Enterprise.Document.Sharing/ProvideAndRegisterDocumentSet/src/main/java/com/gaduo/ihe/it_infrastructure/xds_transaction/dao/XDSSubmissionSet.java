package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import java.util.HashMap;
import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;
import com.gaduo.ihe.constants.SubmissionSetConstants;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.Common;
import com.gaduo.ihe.utility.PnRCommon;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility._interface.ICommon;

public class XDSSubmissionSet extends XDSEntry {
	public XDSSubmissionSet(OMElement request) {
		super(EbXML.RegistryPackage);
		ICommon common = new PnRCommon();
		IAxiomUtil axiom = new AxiomUtil();
		String entryUUID = common.createUUID();
		this.setId(entryUUID.toString());
		this.setObjectType(ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_OBJECT);
		// --submissionTime
		String submissionTime = common.createTime();
		if (submissionTime != null) {
			OMElement slot = this.addSlot(
					SubmissionSetConstants.SUBMISSION_TIME,
					new String[] { submissionTime });
			root.addChild(slot);
		}

		// // ---------------------Main
		String title = extractTitle(request);
		if (title != null) {
			OMElement name = addNameOrDescription(title, EbXML.Name);
			root.addChild(name);
		}
		String description = extractDescription(request);
		if (description != null) {
			OMElement name = addNameOrDescription(description,
					EbXML.Description);
			root.addChild(name);
		}
		String sourcePatientId = extractSourcePatientId(request);
		// ---------------------Classification
		HashMap<String, String> authors = extractAuthors(request);
		if (!authors.isEmpty()) {
			buildAuthors(authors, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_AUTHOR);
		}
		// ---ContentTypeCode
		String value = axiom.getValueOfType("ContentTypeCode", request);
		if (value != null) {
			buildClassification(
					"contentTypeCode",
					value,
					SubmissionSetConstants.CODING_SCHEME,
					this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSION_SET_CONTENT_TYPE_CODE);
		}
		// ---------------------ExternalIdentifier
		OMElement name;
		name = addNameOrDescription(SubmissionSetConstants.PATIENT_ID,
				EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSION_SET_PATIENT_IDENTIFICATION_SCHEME,
				this.getId(), sourcePatientId, name);

		String uniqueId = PnRCommon.OID + "." + Common.IP + "."
				+ PnRCommon.bootTimestamp + "." + Common.count;
		PnRCommon.count++;
		name = addNameOrDescription(SubmissionSetConstants.UNIQUE_ID,
				EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSION_SET_UNIQUE_IDENTIFICATION_SCHEME,
				this.getId(), uniqueId, name);
		setUniqueId(uniqueId);

		String sourceId = PnRCommon.SourceID;
		name = addNameOrDescription(SubmissionSetConstants.SOURCE_ID,
				EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSION_SET_SOURCE_IDENTIFICATION_SCHEME,
				this.getId(), sourceId, name);

	}
}
