package edu.tcu.gaduo.ihe.iti.xds_transaction.pojo;

import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;

public class FindFolders extends StoredQuery {
	
	public FindFolders(String queryUUID, List<ParameterType> parameters) {
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
		this.ParameterSet.put(StoredQueryConstants.FOL_PATIENT_ID, "R,-");/*PatientUID */
		this.ParameterSet.put(StoredQueryConstants.FOL_LAST_UPDATE_TIME_FROM, "O,-");/*XDSFolderLastUpdateTimeFrom*/
		this.ParameterSet.put(StoredQueryConstants.FOL_LAST_UPDATE_TIME_TO, "O,-");/*XDSFolderLastUpdateTimeTo*/
		this.ParameterSet.put(StoredQueryConstants.FOL_CODE_LIST, "O,M");/*XDSFolderCodeList*/
		this.ParameterSet.put(StoredQueryConstants.FOL_CODE_LIST_SCHEME, "O,M");
		this.ParameterSet.put(StoredQueryConstants.FOL_STATUS, "R,M");/*XDSFolderStatus */
	}
}
