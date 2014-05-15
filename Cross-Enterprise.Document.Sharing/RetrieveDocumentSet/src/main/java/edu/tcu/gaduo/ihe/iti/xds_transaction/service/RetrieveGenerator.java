package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.DocumentRequest;

public class RetrieveGenerator {
	public static Logger logger = Logger.getLogger(RetrieveGenerator.class);

	public OMElement execution(Set<DocumentRequest> documentIdList) {
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMElement RetrieveDocumentSetRequest = axiom.createOMElement("RetrieveDocumentSetRequest", "urn:ihe:iti:xds-b:2007", "");
		Iterator<DocumentRequest> iterator = documentIdList.iterator();
		while (iterator.hasNext()) {
			DocumentRequest next = iterator.next();
			OMElement DocumentRequest = makeRequest(next);
			RetrieveDocumentSetRequest.addChild(DocumentRequest);
		}
		logger.info(RetrieveDocumentSetRequest);
		return RetrieveDocumentSetRequest;
	}
	
	public OMElement execution(List<DocumentRequest> documentIdList) {
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMElement RetrieveDocumentSetRequest = axiom.createOMElement("RetrieveDocumentSetRequest", "urn:ihe:iti:xds-b:2007", "");
		Iterator<DocumentRequest> iterator = documentIdList.iterator();
		while (iterator.hasNext()) {
			DocumentRequest next = iterator.next();
			OMElement DocumentRequest = makeRequest(next);
			RetrieveDocumentSetRequest.addChild(DocumentRequest);
		}
		logger.info(RetrieveDocumentSetRequest);
		return RetrieveDocumentSetRequest;
	}
	
	
	private OMElement makeRequest(DocumentRequest next){
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMElement DocumentRequest = axiom.createOMElement("DocumentRequest", "urn:ihe:iti:xds-b:2007", "");
		//--RepositoryUniqueId
		OMElement RepositoryUniqueId = axiom.createOMElement("RepositoryUniqueId", "urn:ihe:iti:xds-b:2007", "");
		RepositoryUniqueId.setText(next.getRepositoryUniqueId());
		DocumentRequest.addChild(RepositoryUniqueId);
		//--HomeCommunityId			
		String homeCommunityId = next.getHomeCommunityId();
		if(homeCommunityId != null && !homeCommunityId.equals("")){
			OMElement HomeCommunityId = axiom.createOMElement("HomeCommunityId", "urn:ihe:iti:xds-b:2007", "");
			HomeCommunityId.setText(homeCommunityId);
			DocumentRequest.addChild(HomeCommunityId);
		}
		//--DocumentUniqueId					
		OMElement DocumentUniqueId = axiom.createOMElement("DocumentUniqueId", "urn:ihe:iti:xds-b:2007", "");
		DocumentUniqueId.setText(next.getDocumentUniqueId());
		DocumentRequest.addChild(DocumentUniqueId);
		return DocumentRequest;
		
	}
	
	
	public OMElement execution(Set<String> documentIdList, String repositoryId,
			String homeCommunityId) {
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMElement RetrieveDocumentSetRequest = axiom.createOMElement("RetrieveDocumentSetRequest", "urn:ihe:iti:xds-b:2007", "");
		Iterator<String> iterator = documentIdList.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			OMElement DocumentRequest = axiom.createOMElement("DocumentRequest", "urn:ihe:iti:xds-b:2007", "");

			OMElement RepositoryUniqueId = axiom.createOMElement("RepositoryUniqueId", "urn:ihe:iti:xds-b:2007", "");
			RepositoryUniqueId.setText(repositoryId);
			DocumentRequest.addChild(RepositoryUniqueId);

			if (homeCommunityId != null && !homeCommunityId.equals("")) {
				OMElement HomeCommunityId = axiom.createOMElement("HomeCommunityId", "urn:ihe:iti:xds-b:2007", "");
				HomeCommunityId.setText(homeCommunityId);
				DocumentRequest.addChild(HomeCommunityId);
			}
			OMElement DocumentUniqueId = axiom.createOMElement("DocumentUniqueId", "urn:ihe:iti:xds-b:2007", "");
			DocumentUniqueId.setText(next);
			DocumentRequest.addChild(DocumentUniqueId);
			RetrieveDocumentSetRequest.addChild(DocumentRequest);
		}
		logger.info(RetrieveDocumentSetRequest);
		return RetrieveDocumentSetRequest;
	}
}
