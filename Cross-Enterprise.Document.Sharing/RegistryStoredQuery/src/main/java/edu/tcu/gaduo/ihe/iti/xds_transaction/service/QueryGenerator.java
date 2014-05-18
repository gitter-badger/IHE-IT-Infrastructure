package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.util.List;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.FindDocuments;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.FindFolders;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.FindSubmissionSets;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetAll;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetAssociations;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetDocuments;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetDocumentsAndAssociations;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetFolderAndContents;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetFolders;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetFoldersForDocument;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetRelatedDocuments;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetSubmissionsSets;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.GetSubmissionsetAndContents;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.StoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class QueryGenerator {
	private OMElement AdhocQueryRequest;
	private IAxiomUtil axiom = null;

	public OMElement execution(QueryType query) {

		this.axiom = AxiomUtil.getInstance();
		/* AdhocQueryRequest */
		AdhocQueryRequest = axiom.createOMElement(EbXML.AdhocQueryRequest, Namespace.QUERY3);
		OMElement RequestSlotList = axiom.createOMElement(EbXML.RequestSlotList, Namespace.RS3);
		AdhocQueryRequest.addChild(RequestSlotList);
		/* AdhocQueryRequest */
		OMElement ResponseOption = axiom.createOMElement(EbXML.ResponseOption, Namespace.QUERY3);
		String returnType = query.getReturnType().getValue();
		ResponseOption.addAttribute("returnType", returnType, null);
		
		if (ResponseOption != null) {
			AdhocQueryRequest.addChild(ResponseOption);
		}
		String queryUUID = query.getQueryUUID().getValue();
		List<ParameterType> parameters = query.getParameters();
		OMElement AdhocQuery = optionCondition(queryUUID, parameters);
		if (AdhocQuery != null) {
			AdhocQueryRequest.addChild(AdhocQuery);
		}
		return AdhocQueryRequest;
	}
	
	private OMElement optionCondition(String QueryUUID, List<ParameterType> parameters) {
		/* switch UUID */
		OMElement AdhocQuery = null;
		StoredQuery query = null;
		if (QueryUUID.equals(RegistryStoredQueryUUIDs.FIND_DOCUMENTS_UUID)) {
			query = new FindDocuments(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.FIND_SUBMISSIONSETS_UUID)) {
			query = new FindSubmissionSets(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.FIND_FOLDERS_UUID)) {
			query = new FindFolders(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_ALL_UUID)) {
			query = new GetAll(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_DOCUMENTS_UUID)) {
			query = new GetDocuments(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_FOLDERS_UUID)) {
			query = new GetFolders(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_ASSOCIATIONS_UUID)) {
			query = new GetAssociations(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_DOCUMENTS_AND_ASSOCIATIONS_UUID)) {
			query = new GetDocumentsAndAssociations(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_SUBMISSIONSETS_UUID)) {
			query = new GetSubmissionsSets(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_SUBMISSIONSETS_AND_CONTENTS_UUID)) {
			query = new GetSubmissionsetAndContents(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_FOLDER_AND_CONTENTS_UUID)) {
			query = new GetFolderAndContents(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_FOLDERS_FOR_DOCUMENT_UUID)) {
			query = new GetFoldersForDocument(QueryUUID, parameters);
			return query.getAdhocQuery();
		} else if (QueryUUID.equals(RegistryStoredQueryUUIDs.GET_RELATED_DOCUMENTS_UUID)) {
			query = new GetRelatedDocuments(QueryUUID, parameters);
			return query.getAdhocQuery();
		}
		return AdhocQuery;
	}
}
