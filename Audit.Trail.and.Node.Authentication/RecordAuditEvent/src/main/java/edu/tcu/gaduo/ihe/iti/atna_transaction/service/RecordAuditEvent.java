package edu.tcu.gaduo.ihe.iti.atna_transaction.service;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;

public class RecordAuditEvent extends Transaction{

	
	public OMElement AuditGenerator(OMElement source){
		return source;
	}
	
	@Override
	public OMElement send(OMElement request) {
		return null;
	}

}
