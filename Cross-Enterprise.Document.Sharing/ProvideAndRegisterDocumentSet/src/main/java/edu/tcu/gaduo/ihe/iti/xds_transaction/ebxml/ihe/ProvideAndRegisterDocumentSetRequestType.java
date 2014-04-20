package edu.tcu.gaduo.ihe.iti.xds_transaction.ebxml.ihe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import edu.tcu.gaduo.ihe.iti.xds_transaction.ebxml.lcm.SubmitObjectsRequestType;

@XmlRootElement(name = "ProvideAndRegisterDocumentSetRequest")
@XmlAccessorType (XmlAccessType.FIELD)
public class ProvideAndRegisterDocumentSetRequestType {
	protected SubmitObjectsRequestType submitObjectsRequest;
	protected DocumentsType documents;
}
