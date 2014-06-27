package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="Association", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
@XmlAccessorType (XmlAccessType.FIELD)
public class AssociationType {
	@XmlAttribute(required=true)
	protected String id;
	@XmlAttribute
	protected String objectType;
	@XmlAttribute
	protected String sourceObject;
	@XmlAttribute
	protected String targetObject;
	@XmlAttribute
	protected String associationType;

}
