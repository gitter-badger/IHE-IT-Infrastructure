package com.gaduo.ihe.it_infrastructure.xds_transaction.service._interface;

import org.apache.axiom.om.OMElement;

public interface XDSTransactionGenerator {
	public OMElement execution(OMElement request) ;
}
