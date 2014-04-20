package edu.tcu.gaduo.ihe.iti.xds_transaction.ebxml.rim;

import javax.xml.bind.annotation.XmlAttribute;

public class ClassificationType {

	protected String classificationScheme;
	@XmlAttribute(required=true)
	protected String classifiedObject;
	protected String classificationNode;
	
}
