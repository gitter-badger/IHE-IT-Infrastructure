package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.ihe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.lcm.SubmitObjectsRequestType;

@XmlRootElement(name = "ProvideAndRegisterDocumentSetRequest", namespace="urn:ihe:iti:xds-b:2007")
@XmlAccessorType (XmlAccessType.FIELD)
public class ProvideAndRegisterDocumentSetRequestType {
	
	@XmlElement(name="SubmitObjectsRequest", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0")
	protected SubmitObjectsRequestType submitObjectsRequest;

	// [0 ... *]
	@XmlElement(name="Document", namespace="urn:ihe:iti:xds-b:2007")
	protected List<DocumentType> documents;
}
