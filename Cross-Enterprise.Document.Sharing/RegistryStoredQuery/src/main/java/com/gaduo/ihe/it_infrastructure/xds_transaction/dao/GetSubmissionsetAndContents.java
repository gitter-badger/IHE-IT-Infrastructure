package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.StoredQueryConstants;


public class GetSubmissionsetAndContents extends StoredQuery {
	public GetSubmissionsetAndContents(String queryUUID, OMElement request) {
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
				}
				if (slot != null) {
					addParameters(slot);
				}
			}
		}
	}
	
	protected void setParameterSet(){
		this.ParameterSet.put(StoredQueryConstants.SS_ENTRY_ID, "O,-");/*XDSSubmissionSetEntryUUID*/
		this.ParameterSet.put(StoredQueryConstants.SS_UNIQUE_ID, "O,-");/*XDSSubmissionSetUniqueId*/
		this.ParameterSet.put(StoredQueryConstants.DE_FORMAT_CODE, "O,M");/*XDSDocumentEntryFormatCode */
		this.ParameterSet.put(StoredQueryConstants.DE_CONF_CODE, "O,M");/*XDSDocumentEntryConfidentialityCode*/
	}
}
