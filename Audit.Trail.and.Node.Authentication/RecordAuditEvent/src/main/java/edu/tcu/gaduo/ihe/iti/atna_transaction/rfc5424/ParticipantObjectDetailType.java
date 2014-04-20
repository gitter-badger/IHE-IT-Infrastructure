package edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;

import edu.tcu.gaduo.ihe.constants.atna.RFC3881;

public class ParticipantObjectDetailType extends General {
	protected String type;
	protected String value;
	
	public ParticipantObjectDetailType(String type, String value){
		this.type = type;
		this.value = value;
	}
	
	@Override
	public OMElement buildRFC3881() {
    	OMNamespace namespace = null;
		OMElement root = axiom.createOMElement(RFC3881.ParticipantObjectDetail.getTag(), namespace);
		if(type != null){
			root.addAttribute("type", type, null);
		}
		if(value != null){
			root.addAttribute("value", value, null);
		}
		return root;
	}


}
