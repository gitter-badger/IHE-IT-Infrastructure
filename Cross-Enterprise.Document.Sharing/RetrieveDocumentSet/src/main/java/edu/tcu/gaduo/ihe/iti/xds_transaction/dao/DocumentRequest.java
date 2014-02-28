package edu.tcu.gaduo.ihe.iti.xds_transaction.dao;

public class DocumentRequest {
	private String RepositoryUniqueId;
	private String DocumentUniqueId;
	private String HomeCommunityId;
	
	public DocumentRequest(String repositoryUniqueId, String documentUniqueId,
			String homeCommunityId) {
		super();
		RepositoryUniqueId = repositoryUniqueId;
		DocumentUniqueId = documentUniqueId;
		HomeCommunityId = homeCommunityId;
	}
	public String getRepositoryUniqueId() {
		return RepositoryUniqueId;
	}
	public String getDocumentUniqueId() {
		return DocumentUniqueId;
	}
	public String getHomeCommunityId() {
		return HomeCommunityId;
	}
	public void setRepositoryUniqueId(String repositoryUniqueId) {
		RepositoryUniqueId = repositoryUniqueId;
	}
	public void setDocumentUniqueId(String documentUniqueId) {
		DocumentUniqueId = documentUniqueId;
	}
	public void setHomeCommunityId(String homeCommunityId) {
		HomeCommunityId = homeCommunityId;
	}
	
	
}
