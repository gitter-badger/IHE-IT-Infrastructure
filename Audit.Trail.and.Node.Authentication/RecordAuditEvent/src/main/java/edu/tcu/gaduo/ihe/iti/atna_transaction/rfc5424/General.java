package edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public abstract class General {

	public static Logger logger = Logger.getLogger(General.class);
	protected IAxiomUtil axiom = null;
	public General(){
		axiom = AxiomUtil.getInstance();
	}
	public abstract OMElement buildRFC3881();
	
}
