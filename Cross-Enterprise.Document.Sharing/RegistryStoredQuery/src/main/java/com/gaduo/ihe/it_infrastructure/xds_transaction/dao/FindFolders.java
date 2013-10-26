package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.StoredQueryConstants;

public class FindFolders extends StoredQuery {
	
	public FindFolders(String queryUUID, OMElement request) {
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
		this.ParameterSet.put(StoredQueryConstants.FOL_PATIENT_ID, "R,-");/*PatientUID */
		this.ParameterSet.put(StoredQueryConstants.FOL_LAST_UPDATE_TIME_FROM, "O,-");/*XDSFolderLastUpdateTimeFrom*/
		this.ParameterSet.put(StoredQueryConstants.FOL_LAST_UPDATE_TIME_TO, "O,-");/*XDSFolderLastUpdateTimeTo*/
		this.ParameterSet.put(StoredQueryConstants.FOL_CODE_LIST, "O,M");/*XDSFolderCodeList*/
		this.ParameterSet.put(StoredQueryConstants.FOL_CODE_LIST_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.FOL_STATUS, "R,M");/*XDSFolderStatus */
	}
}
