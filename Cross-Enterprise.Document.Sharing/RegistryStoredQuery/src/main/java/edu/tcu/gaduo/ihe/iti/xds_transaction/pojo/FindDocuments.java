package edu.tcu.gaduo.ihe.iti.xds_transaction.pojo;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;


public class FindDocuments extends StoredQuery {
	
	public FindDocuments(String queryUUID, OMElement request) {
		super(queryUUID);
		
		@SuppressWarnings("unchecked")
		Iterator<OMElement> ParameterList = request
				.getChildrenWithName(new QName("Parameter"));
		while (ParameterList.hasNext()) {
			OMElement p = ParameterList.next();
			OMElement slot = null;
			String Parameter = p.getAttributeValue(new QName("name"));
			if (Parameter != null) {
				if(this.isContainParameter(Parameter)){
					slot = this.addSlot(p);
					if (slot != null) {
						addParameters(slot);
					}
				}
			}
		}
	}
	
	protected void setParameterSet(){
		this.ParameterSet.put(StoredQueryConstants.DE_PATIENT_ID, "R,-");/*PatientUID*/
		this.ParameterSet.put(StoredQueryConstants.DE_CLASS_CODE, "O,M");/*XDSDocumentEntryClassCode*/
		this.ParameterSet.put(StoredQueryConstants.DE_CLASS_CODE_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.DE_PRAC_SETTING_CODE, "O,M");/*XDSDocumentEntryPracticeSettingCode*/
		this.ParameterSet.put(StoredQueryConstants.DE_PRAC_SETTING_CODE_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.DE_CREATION_TIME_FROM, "O,-");/*XDSDocumentEntryCreationTimeFrom */
		this.ParameterSet.put(StoredQueryConstants.DE_CREATION_TIME_TO, "O,-");/*XDSDocumentEntryCreationTimeTo */
		this.ParameterSet.put(StoredQueryConstants.DE_SERVICE_START_TIME_FROM, "O,-");/*XDSDocumentEntryServiceStartTimeFrom */
		this.ParameterSet.put(StoredQueryConstants.DE_SERVICE_START_TIME_TO, "O,-");/*XDSDocumentEntryServiceStartTimeTo */
		this.ParameterSet.put(StoredQueryConstants.DE_SERVICE_STOP_TIME_FROM, "O,-");/*XDSDocumentEntryServiceStopTimeFrom */
		this.ParameterSet.put(StoredQueryConstants.DE_SERVICE_STOP_TIME_TO, "O,-");/*XDSDocumentEntryServiceStopTimeTo */
		this.ParameterSet.put(StoredQueryConstants.DE_HC_FACILITY_CODE, "O,M");/*XDSDocumentEntryHealthcareFacilityTypeCode*/
		this.ParameterSet.put(StoredQueryConstants.DE_HC_FACILITY_CODE_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.DE_EVENT_CODE, "O,M");/*XDSDocumentEntryEventCodeList*/
		this.ParameterSet.put(StoredQueryConstants.DE_EVENT_CODE_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.DE_CONF_CODE, "O,M");/*XDSDocumentEntryConfidentialityCode*/
		this.ParameterSet.put(StoredQueryConstants.DE_CONF_CODE_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.DE_FORMAT_CODE, "O,M");/*XDSDocumentEntryFormatCode*/
		this.ParameterSet.put(StoredQueryConstants.DE_STATUS, "R,M");/*XDSDocumentEntryStatus */
	}
}
