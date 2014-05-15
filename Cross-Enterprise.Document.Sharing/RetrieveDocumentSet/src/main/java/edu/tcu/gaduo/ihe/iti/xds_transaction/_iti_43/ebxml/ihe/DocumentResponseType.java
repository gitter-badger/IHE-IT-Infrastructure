package edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.ihe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DocumentResponse", namespace="urn:ihe:iti:xds-b:2007")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentResponseType {
	@XmlElement(name="RepositoryUniqueId", namespace="urn:ihe:iti:xds-b:2007")
	protected RepositoryUniqueIdType repositoryUniqueId;
	
	@XmlElement(name="DocumentUniqueId", namespace="urn:ihe:iti:xds-b:2007")
	protected DocumentUniqueIdType documentUniqueId;
	
	@XmlElement(name="mimeType", namespace="urn:ihe:iti:xds-b:2007")
	protected MimeTypeType mimeType;
	
	@XmlElement(name="Document", namespace="urn:ihe:iti:xds-b:2007")
	protected DocumentType document;

	/**
	 * @return the repositoryUniqueId
	 */
	public RepositoryUniqueIdType getRepositoryUniqueId() {
		return repositoryUniqueId;
	}

	/**
	 * @param repositoryUniqueId the repositoryUniqueId to set
	 */
	public void setRepositoryUniqueId(RepositoryUniqueIdType repositoryUniqueId) {
		this.repositoryUniqueId = repositoryUniqueId;
	}

	/**
	 * @return the documentUniqueId
	 */
	public DocumentUniqueIdType getDocumentUniqueId() {
		return documentUniqueId;
	}

	/**
	 * @param documentUniqueId the documentUniqueId to set
	 */
	public void setDocumentUniqueId(DocumentUniqueIdType documentUniqueId) {
		this.documentUniqueId = documentUniqueId;
	}

	/**
	 * @return the mimeType
	 */
	public MimeTypeType getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(MimeTypeType mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the document
	 */
	public DocumentType getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(DocumentType document) {
		this.document = document;
	}
	
	

}
