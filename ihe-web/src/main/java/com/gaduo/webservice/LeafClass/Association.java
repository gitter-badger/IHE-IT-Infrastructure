package com.gaduo.webservice.LeafClass;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import com.gaduo.webservice.LeafClass._interface.IEBXMLParser;
import com.gaduo.zk.model.KeyValue.KeyValuesImpl;


public class Association implements IEBXMLParser {
    public static Logger logger = Logger.getLogger(Association.class);

	public void execute(OMElement element, KeyValuesImpl map) {

        String sourceObject = element.getAttributeValue(new QName("sourceObject"));
        String targetObject = element.getAttributeValue(new QName("targetObject"));
        String associationType = element.getAttributeValue(new QName("associationType"));
        logger.info(sourceObject);
        logger.info(targetObject);
        logger.info(associationType);
	}

}
