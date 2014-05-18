package edu.tcu.gaduo.ihe.iti.xds_transaction.pojo;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;


public class FindSubmissionSets extends StoredQuery {

	public FindSubmissionSets(String queryUUID, List<ParameterType> parameters) {
		super(queryUUID);

		Iterator<ParameterType> pList = parameters.iterator();
		while (pList.hasNext()) {
			ParameterType p = pList.next();
			String name = p.getName();
			OMElement slot = null;
			if (name != null) {
				if(this.isContainParameter(name)){
					List<ValueType> vList = p.getValues();
					slot = this.addSlot(name, vList);
					if (slot != null) {
						addParameters(slot);
					}
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
