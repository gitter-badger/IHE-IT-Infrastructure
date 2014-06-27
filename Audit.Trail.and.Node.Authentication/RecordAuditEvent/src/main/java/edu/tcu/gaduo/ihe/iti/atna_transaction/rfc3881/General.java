package edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

@XmlTransient
public abstract class General {
	@XmlTransient
	public static Logger logger = Logger.getLogger(General.class);
	@XmlTransient
	protected IAxiomUtil axiom = null;
	public General(){
		axiom = AxiomUtil.getInstance();
	}
	public abstract OMElement buildRFC3881();
	
}
