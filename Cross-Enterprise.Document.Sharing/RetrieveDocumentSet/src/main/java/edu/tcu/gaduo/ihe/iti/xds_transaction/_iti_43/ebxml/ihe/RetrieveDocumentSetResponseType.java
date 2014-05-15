package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.ihe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.rs.RegistryResponseType;

@XmlRootElement(name = "RetrieveDocumentSetResponse", namespace="urn:ihe:iti:xds-b:2007")
@XmlAccessorType (XmlAccessType.FIELD)
public class RetrieveDocumentSetResponseType {

	@XmlElement(name="RegistryResponse", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0")
	protected RegistryResponseType registryResponseType;
	
	@XmlElement(name="DocumentResponse", namespace="urn:ihe:iti:xds-b:2007")
	protected List<DocumentResponseType> documentResponses ;


	/**
	 * @return the registryResponseType
	 */
	public RegistryResponseType getRegistryResponseType() {
		return registryResponseType;
	}

	/**
	 * @param registryResponseType the registryResponseType to set
	 */
	public void setRegistryResponseType(RegistryResponseType registryResponseType) {
		this.registryResponseType = registryResponseType;
	}

	/**
	 * @return the documentResponses
	 */
	public List<DocumentResponseType> getDocumentResponses() {
		return documentResponses;
	}

	/**
	 * @param documentResponses the documentResponses to set
	 */
	public void setDocumentResponses(List<DocumentResponseType> documentResponses) {
		this.documentResponses = documentResponses;
	}
	
	
}
