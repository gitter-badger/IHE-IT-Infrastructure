package com.gaduo.webservice.LeafClass._interface;

import org.apache.axiom.om.OMElement;

import com.gaduo.zk.model.KeyValue.KeyValuesImpl;


public interface IEBXMLParser {
	void execute(OMElement element, KeyValuesImpl map) ;
}
