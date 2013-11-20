package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.StoredQueryConstants;


public class FindSubmissionSets extends StoredQuery {

	public FindSubmissionSets(String queryUUID, OMElement request) {
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
		this.ParameterSet.put(StoredQueryConstants.SS_PATIENT_ID, "R,-");/*PatientUID */
		this.ParameterSet.put(StoredQueryConstants.SS_SOURCE_ID, "O,M");/*XDSSubmissionSetSourceId*/
		this.ParameterSet.put(StoredQueryConstants.SS_SUBMISSION_TIME_FROM, "O,-");/*XDSSubmissionSetSubmissionTimeFrom*/
		this.ParameterSet.put(StoredQueryConstants.SS_SUBMISSION_TIME_TO, "O,-");/*XDSSubmissionSetSubmissionTimeTo*/
		this.ParameterSet.put(StoredQueryConstants.SS_AUTHOR_PERSON, "O,-");/*XDSSubmissionSetAuthorPerson*/
		this.ParameterSet.put(StoredQueryConstants.SS_CONTENT_TYPE, "O,M");/*XDSSubmissionSetContentType*/
		this.ParameterSet.put(StoredQueryConstants.SS_STATUS, "R,M");/*XDSFolderStatus */
	}
}
