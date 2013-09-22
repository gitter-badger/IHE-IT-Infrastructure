/**
 * 
 */
package com.gaduo.zk.view_model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Filedownload;

import com.gaduo.ihe.it_infrastructure.xds_transaction.service.RegistryStoredQuery;
import com.gaduo.ihe.security.Certificate;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.Common;
import com.gaduo.ihe.utility.webservice.nonblock.IResponse;
import com.gaduo.ihe.utility.webservice.nonblock.NonBlockCallBack;
import com.gaduo.ihe.utility.webservice.nonblock.Response_ITI_43;
import com.gaduo.ihe.utility.webservice.nonblock.RetrieveResult;
import com.gaduo.ihe.utility.xml.XMLPath;
import com.gaduo.webservice.ServiceConsumer;
import com.gaduo.webservice.Soap;
import com.gaduo.webservice._interface.ISoap;
import com.gaduo.webservice.parser.MetadataParser;
import com.gaduo.zk.model.CompanyInfomation;
import com.gaduo.zk.model.QueryGenerator;
import com.gaduo.zk.model.KeyValue.KeyValue;
import com.gaduo.zk.model.KeyValue.KeyValuesImpl;
import com.gaduo.zk.model.iti_18.GetDocuments;
import com.gaduo.zk.model.iti_18.GetFolders;
import com.gaduo.zk.model.iti_18.GetSubmissionsetAndContents;

/**
 * @author Gaduo
 */
public class LeafClass {
	public static Logger logger = Logger.getLogger(LeafClass.class);

	private String uuid;
	private QueryGenerator query = null;
	private List<KeyValue> list = null;
	private XMLPath codes = null, web = null;
	private CompanyInfomation companyRepository;
	private CompanyInfomation companyRegistry;

	@Init
	public void init(@ExecutionArgParam("query") QueryGenerator query,
			@ExecutionArgParam("uuid") String uuid,
			@ExecutionArgParam("repository") CompanyInfomation repository,
			@ExecutionArgParam("registry") CompanyInfomation registry) {
		ClassLoader loader = getClass().getClassLoader();
		codes = new XMLPath(loader.getResourceAsStream("codes.xml"));
		web = new XMLPath(loader.getResourceAsStream("web.xml"));
		setQuery(query); // 配置 QueryGenerator 記憶體，取得前一個查詢類型是什麼
							// FindDocument、FindFolder、FindSubmissionSet
		setUuid(uuid);
		setCompanyRepository(repository);
		setCompanyRegistry(registry);
		// setEndpoint("http://203.64.84.112:8080/axis2/services/RegistryStoredQuery-Gaduo?wsdl");
		logger.trace(uuid);
		if (query != null)
			submit();
		System.gc();
	}

	public void submit() {
		boolean resubmit = false;
		query.setCompanyRegistry(companyRegistry);
		query.setReturnType("LeafClass");

		if (query.getQueryType() != null) {
			if (query.getQueryType().equals(
					new KeyValue("FindDocuments",
							"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"))) {
				query.setQueryType(new KeyValue("Get Documents",
						"urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4"));
				GetDocuments gd = new GetDocuments();
				gd.setType("EntryUUID");
				gd.setId(uuid);
				query.setParameter(gd);
				logger.trace("QueryType is GetDocuments");
				resubmit = true;
			} else if (query.getQueryType().equals(
					new KeyValue("FindFolders",
							"urn:uuid:958f3006-baad-4929-a4de-ff1114824431"))) {
				query.setQueryType(new KeyValue("Get Folders",
						"urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4"));
				GetFolders gf = new GetFolders();
				gf.setType("EntryUUID");
				gf.setId(uuid);
				query.setParameter(gf);
				logger.trace("QueryType is GetFolders");
				resubmit = true;
			} else if (query.getQueryType().equals(
					new KeyValue("FindSubmissionSets",
							"urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9"))) {
				query.setQueryType(new KeyValue(
						"Get Submissionset And Contents",
						"urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83"));
				GetSubmissionsetAndContents gs = new GetSubmissionsetAndContents();
				gs.setId(uuid);
				query.setParameter(gs);
				logger.trace("QueryType is Get Submissionset And Contents");
				resubmit = true;
			}
		} else {
			logger.error("QueryType is NULL");
			return;
		}
		boolean isBuild = query.build(); // 新的 Query
		if (isBuild && resubmit) { // 再做一次 Query
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			rsq.QueryGenerator(query.getQuery());
			// NonBlockCallBack callback = rsq.getCallback();

			// ServiceConsumer ServiceConsumer = new ServiceConsumer(endpoint,
			// query.getQuery(), null,
			// "urn:Gaduo:Registry:Stored:Query", false, true);
			// NonBlockCallBack callback = ServiceConsumer.getCallback();
			// if (callback == null)
			// return;
			try {
				// 解析回應的訊息
				MessageContext context = rsq.getContext();
				SOAPEnvelope envelope = context.getEnvelope();
				SOAPBody body = envelope.getBody();
				OMElement response = body.getFirstElement();
				MetadataParser mp = new MetadataParser(response);
				Map<String, KeyValuesImpl> map = mp.getMapArray();
				if (map != null) {
					KeyValuesImpl keyValueList = map.get(uuid);
					if (keyValueList != null) {
						setList(keyValueList.findAll());
					}
				}
			} catch (java.lang.NullPointerException e) {
				logger.error(e.toString());
				return;
			}
		} else {
			logger.error("isBuild is " + isBuild);
		}

		System.gc();
	}

	// -------------------
	@Command
	public void download() {
		new Certificate().setCertificate();
		OMElement element = buildRetrieveRequest();
		if (element == null)
			return;
		Common c = new Common();
		c.saveLog(c.createTime(), "Request_ITI-43", element);
		// ISoap soap = new Soap(companyRepository.getRepositoryEndpoint(),
		// "http://www.w3.org/2005/08/addressing",
		// "urn:ihe:iti:2007:RetrieveDocumentSet", true);
		// MessageContext context = soap.send(element);
		ServiceConsumer ServiceConsumer = new ServiceConsumer(
				companyRepository.getRepositoryEndpoint(), element, null,
				"urn:ihe:iti:2007:RetrieveDocumentSet", true, true);
		NonBlockCallBack context = ServiceConsumer.getCallback();
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope()
				: null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		if (response == null) {
			logger.error("ITI-43 No Response.");
			return;
		}
		c.saveLog(c.createTime(), "Response_ITI-43", response);
		IResponse ITI_43 = new Response_ITI_43();
		ITI_43.parser(envelope);

		List<RetrieveResult> RetrieveResultList = ((Response_ITI_43) ITI_43)
				.getRetrieveResultList();
		if (RetrieveResultList != null) {
			Iterator<RetrieveResult> iterator = RetrieveResultList.iterator();
			while (iterator.hasNext()) {
				RetrieveResult rr = iterator.next();
				byte[] array = org.apache.commons.codec.binary.Base64
						.decodeBase64(rr.getDocument().getBytes());
				Filedownload.save(
						array,
						rr.getMimeType(),
						rr.getDocumentUniqueId() + "."
								+ extractExtension(rr.getMimeType()));

			}
		}
		System.gc();
	}

	private String extractExtension(String mimeType) {
		String extension = null;
		Node node = null;
		if (codes != null) {
			node = codes
					.QueryNode("Codes/CodeType[@name='mimeType']/Code[@code='"
							+ mimeType + "']");
			if (node != null) {
				if (node != null) {
					NamedNodeMap attrs = node.getAttributes();
					if (attrs != null) {
						extension = attrs.getNamedItem("ext").getNodeValue();
						return extension;
					}
				}
			}
		}
		if (web != null) {
			node = web.QueryNode("web-app/mime-mapping/mime-type[text()='"
					+ mimeType + "']");
			if (node != null) {
				node = node.getParentNode();
				NodeList nodes = node.getChildNodes();
				for (int i = 0; i < nodes.getLength(); i++) {
					node = nodes.item(i);
					if (node.getNodeName().equalsIgnoreCase("extension")) {
						return node.getTextContent();
					}
				}
			}
		}
		return null;
	}

	private OMElement buildRetrieveRequest() {
		AxiomUtil axiom = new AxiomUtil();
		String uniqueId = "", repositoryUniqueId = "";
		if (list == null)
			return null;
		Iterator<KeyValue> iterator = list.iterator();
		while (iterator.hasNext()) {
			KeyValue next = iterator.next();
			if (next.getKey().equals("DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME")) {
				uniqueId = next.getValue();
			}
			if (next.getKey().equals("repositoryUniqueId")) {
				repositoryUniqueId = next.getValue();
			}
		}
		OMElement RetrieveDocumentSetRequest = axiom.createOMElement(
				"RetrieveDocumentSetRequest", "urn:ihe:iti:xds-b:2007", "");
		OMElement DocumentRequest = axiom.createOMElement("DocumentRequest",
				"urn:ihe:iti:xds-b:2007", "");
		OMElement RepositoryUniqueId = axiom.createOMElement(
				"RepositoryUniqueId", "urn:ihe:iti:xds-b:2007", "");
		RepositoryUniqueId.setText(repositoryUniqueId);
		DocumentRequest.addChild(RepositoryUniqueId);
		OMElement DocumentUniqueId = axiom.createOMElement("DocumentUniqueId",
				"urn:ihe:iti:xds-b:2007", "");
		DocumentUniqueId.setText(uniqueId);
		DocumentRequest.addChild(DocumentUniqueId);
		RetrieveDocumentSetRequest.addChild(DocumentRequest);

		return RetrieveDocumentSetRequest;
	}

	// -------------------

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public QueryGenerator getQuery() {
		return this.query;
	}

	public void setQuery(QueryGenerator q) {
		this.query = q.clone();
	}

	public List<KeyValue> getList() {
		return list;
	}

	public void setList(List<KeyValue> list) {
		this.list = list;
	}

	public CompanyInfomation getCompanyRepository() {
		return this.companyRepository;
	}

	public void setCompanyRepository(CompanyInfomation companyRepository) {
		this.companyRepository = companyRepository;
	}

	public CompanyInfomation getCompanyRegistry() {
		return this.companyRegistry;
	}

	public void setCompanyRegistry(CompanyInfomation companyRegistry) {
		this.companyRegistry = companyRegistry;
	}

}
