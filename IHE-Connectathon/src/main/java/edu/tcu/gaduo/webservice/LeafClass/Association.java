package edu.tcu.gaduo.webservice.LeafClass;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;


import edu.tcu.gaduo.webservice.LeafClass._interface.IEBXMLParser;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValue;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValuesImpl;


public class Association implements IEBXMLParser {
    public static Logger logger = Logger.getLogger(Association.class);

	public void execute(OMElement element, KeyValuesImpl map) {
        String sourceObject = element.getAttributeValue(new QName("sourceObject"));
        String targetObject = element.getAttributeValue(new QName("targetObject"));
        String associationType = element.getAttributeValue(new QName("associationType"));
		map.add(new KeyValue("sourceObject", sourceObject));
		map.add(new KeyValue("targetObject", targetObject));
		map.add(new KeyValue("associationType", associationType));
	}

}
