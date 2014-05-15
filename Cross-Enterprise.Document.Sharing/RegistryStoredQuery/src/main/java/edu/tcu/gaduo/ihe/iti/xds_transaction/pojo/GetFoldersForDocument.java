package edu.tcu.gaduo.ihe.iti.xds_transaction.pojo;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.ValueType;


public class GetFoldersForDocument extends StoredQuery {
	public GetFoldersForDocument(String queryUUID, List<ParameterType> parameters) {
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
					if(name.equalsIgnoreCase(StoredQueryConstants.HOMECOMMUNITYID)){
//						OMElement home = p.getFirstChildWithName(new QName("Value"));
//						String homeCommunityId = home.getText().replaceAll("'", "");
//						super.getAdhocQuery().addAttribute("home", homeCommunityId, null);
						continue;
					}
					if (slot != null) {
						addParameters(slot);
					}
				}
			}
		}
	}
	
	protected void setParameterSet(){
		this.ParameterSet.put(StoredQueryConstants.DE_ENTRY_UUID, "O,-");/*XDSDocumentEntryEntryUUID*/
		this.ParameterSet.put(StoredQueryConstants.DE_UNIQUE_ID, "O,-");/*XDSDocumentEntryUniqueId*/
		this.ParameterSet.put(StoredQueryConstants.HOMECOMMUNITYID, "O,-");/*homeCommunityId*/
	}
}
