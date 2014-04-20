package edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.atna.NetworkAccessPointTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.RFC3881;

public class ActiveParticipantType extends General {

	public static Logger logger = Logger.getLogger(ActiveParticipantType.class);

	protected String userID;
	protected String alternativeUserID;
	protected String userName; // ITI-41, ITI-63
	protected boolean userIsRequestor;
	protected List<CodedValueType> roleIDCodes; // [0 ... *]
	protected int networkAccessPointTypeCode = -1;
	protected String networkAccessPointID;
	
	
	public ActiveParticipantType(boolean userIsRequestor, NetworkAccessPointTypeCode networkAccessPointTypeCode){
		if(networkAccessPointTypeCode != null)
			this.networkAccessPointTypeCode = networkAccessPointTypeCode.getValue();
		this.userIsRequestor = userIsRequestor;
		roleIDCodes = new ArrayList<CodedValueType>();
	}
	
	/* --------------------------------------------------- */

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @param alternativeUserID the alternativeUserID to set
	 */
	public void setAlternativeUserID(String alternativeUserID) {
		this.alternativeUserID = alternativeUserID;
	}

	/**
	 * @param networkAccessPointID the networkAccessPointID to set
	 */
	public void setNetworkAccessPointID(String networkAccessPointID) {
		this.networkAccessPointID = networkAccessPointID;
	}
	
	
	
	public void addRoleIDCode(CodedValueType roleIDCode){
		roleIDCodes.add(roleIDCode);
	}
	
	/* --------------------------------------------------- */
	

	@Override
	public OMElement buildRFC3881() {
		OMNamespace namespace = null;
		OMElement root = axiom.createOMElement(RFC3881.ActiveParticipant.getTag(), namespace);
		root.addAttribute("UserIsRequestor", userIsRequestor + "", null);
		
		
		if(userID != null)
			root.addAttribute("UserID", userID, null);
		if(alternativeUserID != null)
			root.addAttribute("AlternativeUserID", alternativeUserID, null);
		if(userName != null)
			root.addAttribute("UserName", userName, null);
		if(networkAccessPointID != null)
			root.addAttribute("NetworkAccessPointID", networkAccessPointID, null);
		if(networkAccessPointTypeCode != -1)
			root.addAttribute("NetworkAccessPointTypeCode", networkAccessPointTypeCode + "", null);
		
		logger.info(root);
		
		if(roleIDCodes != null){
			Iterator<CodedValueType> iterator = roleIDCodes.iterator();
			while(iterator.hasNext()){
				CodedValueType next = iterator.next();
				OMElement element = next.buildRFC3881();
				if(element != null)
					root.addChild(element);
			}
		}
		logger.info(root);
		return root;
	}

}
