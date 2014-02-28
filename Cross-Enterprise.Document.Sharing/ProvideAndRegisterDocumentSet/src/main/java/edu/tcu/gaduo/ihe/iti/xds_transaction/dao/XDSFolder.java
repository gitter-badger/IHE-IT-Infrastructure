package edu.tcu.gaduo.ihe.iti.xds_transaction.dao;

import java.sql.Timestamp;
import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility.Common;
import edu.tcu.gaduo.ihe.utility.PnRCommon;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.ICommon;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.FolderConstants;
import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;

public class XDSFolder extends XDSEntry {
	public XDSFolder(final OMElement request, OMElement folder) {
		super(EbXML.RegistryPackage);
		ICommon common = new PnRCommon();
		IAxiomUtil axiom = new AxiomUtil();
		String entryUUID = common.createUUID();
		this.setId(entryUUID);
		this.setObjectType(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_OBJECT);
		// --Folder Time
		String lastUpdateTime = extractLastUpdateTime();
		if (lastUpdateTime != null) {
			OMElement slot = this.addSlot(FolderConstants.LAST_UPDATE_TIME,
					new String[] { lastUpdateTime });
			root.addChild(slot);
		}
		
		// // ---------------------Main
		String title = extractTitle(folder);
		if (title != null) {
			OMElement name = addNameOrDescription(title, EbXML.Name);
			root.addChild(name);
		}
		String description = extractDescription(folder);
		if (description != null) {
			OMElement name = addNameOrDescription(description,
					EbXML.Description);
			root.addChild(name);
		}
		// ---------------------Classification
		// --------FolderCodeList
		String value = axiom.getValueOfType("FolderCodeList", request);
		if (value != null) {
			buildClassification("folderCodeList", value,
					FolderConstants.CODING_SCHEME, this.getId(),
					ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_CODE);
		}
		// ---------------------ExternalIdentifier
		// --Folder SourcePatienId
		String sourcePatientId = extractSourcePatientId(request);
		OMElement name;
		name = addNameOrDescription(FolderConstants.PATIENT_ID, EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_PATIENT_IDENTIFICATION_SCHEME,
				this.getId(), sourcePatientId, name);

		String uniqueId = PnRCommon.OID + "." + Common.IP + "." + PnRCommon.bootTimestamp + "." + Common.count;
		PnRCommon.count++;
		name = addNameOrDescription(FolderConstants.UNIQUE_ID, EbXML.Name);
		addExternalIdentifier(
				ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_UNIQUE_IDENTIFICATION_SCHEME,
				this.getId(), uniqueId, name);
		setUniqueId(uniqueId);
	}

	private String extractLastUpdateTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}

}
