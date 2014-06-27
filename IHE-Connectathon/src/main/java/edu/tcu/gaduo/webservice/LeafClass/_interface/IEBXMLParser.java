package edu.tcu.gaduo.webservice.LeafClass._interface;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.zk.model.KeyValue.KeyValuesImpl;


public interface IEBXMLParser {
	void execute(OMElement element, KeyValuesImpl map) ;
}
