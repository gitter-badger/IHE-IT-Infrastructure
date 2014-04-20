package edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424;

import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;

import edu.tcu.gaduo.ihe.constants.atna.AuditSourceTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.RFC3881;

public class AuditSourceIdentificationType extends General{

	protected String auditSourceID; // required
	protected String auditEnterpriseSiteID;
	protected List<CodedValueType> auditSourceTypeCodes; // [0 ... *]
	
	private int code = -1;
	public AuditSourceIdentificationType(AuditSourceTypeCode auditSourceTypeCode) {
		code = auditSourceTypeCode.getValue();
	}
	
	/* --------------------------------------------------------- */

	/**
	 * @param auditSourceID the auditSourceID to set
	 */
	public void setAuditSourceID(String auditSourceID) {
		this.auditSourceID = auditSourceID;
	}
	
	/* --------------------------------------------------------- */	
	
	@Override
	public OMElement buildRFC3881() {
		OMNamespace namespace = null;
		OMElement root = axiom.createOMElement(RFC3881.AuditSourceIdentification.getTag(), namespace);
		if(code != -1)
			root.addAttribute("code", code + "", null);
		if(auditSourceID != null)
			root.addAttribute("AuditSourceID", auditSourceID, null);
		if(auditEnterpriseSiteID != null)
			root.addAttribute("AuditEnterpriseSiteID", auditEnterpriseSiteID, null);
		if(auditSourceTypeCodes != null){
			Iterator<CodedValueType> iterator = auditSourceTypeCodes.iterator();
			while(iterator.hasNext()){
				CodedValueType next = iterator.next();
				OMElement element = next.buildRFC3881();
				if(element != null)
					root.addChild(element);
			}
		}
		return root;
	}

}
