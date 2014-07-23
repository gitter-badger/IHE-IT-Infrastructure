package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.io.InputStream;
import java.util.Set;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.DocumentRequest;
import edu.tcu.gaduo.ihe.utility.Common;
import edu.tcu.gaduo.ihe.utility.ws.ServiceConsumer;
import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;
import edu.tcu.gaduo.ihe.utility.xml.XMLPath;

public class RetrieveDocumentSet extends Transaction {
	public static Logger logger = Logger.getLogger(RetrieveDocumentSet.class);
	private String filename;
	private String repositoryUrl;
	private String ACTION = "urn:ihe:iti:2007:RetrieveDocumentSet";

	public RetrieveDocumentSet() {
		super();
		c = new Common();
	}

	public OMElement RetrieveGenerator(Set<DocumentRequest> documentIdList) {
		if (documentIdList == null)
			return null;
		filename = c.createTime();
		// -------submit ITI - 43 -------------------
		if (!this.repositoryUrl.equals("")) {
			RetrieveGenerator r = new RetrieveGenerator();
			request = r.execution(documentIdList);
			logger.info(request);
			if (request != null) {
				response = send(request);
				if (response != null) {
					logger.info(response);
					return response;
				}
			}
		}
		gc();
		logger.error("Response is null");
		return null;
	}

	public OMElement RetrieveGenerator(Set<String> documentIdList, String repositoryUrl, String repositoryId, String homeCommunityId) {
		if (documentIdList == null)
			return null;
		filename = c.createTime();
		this.repositoryUrl = repositoryUrl;
		// -------submit ITI - 43 -------------------
		if (!this.repositoryUrl.equals("")) {
			RetrieveGenerator r = new RetrieveGenerator();
			request = r.execution(documentIdList, repositoryId, homeCommunityId);
			logger.info(request);
			if (request != null) {
				response = send(request);
				if (response != null) {
					logger.info(response);
					return response;
				}
			}
		}
		gc();
		logger.error("Response is null");
		return null;
	}

	@Override
	public OMElement send(OMElement request) {
		c.saveLog(filename, "Request_ITI-43", request);
		ISoap soap = new ServiceConsumer(repositoryUrl, ACTION);
		((ServiceConsumer) soap).setMTOM_XOP(true);
		OMElement response = soap.send(request);
		c.saveLog(filename, "Response_ITI-43", response);
		gc();
		return response;
	}

	public String extractExtension(String mimeType) {
		String extension = null;
		Node node = null;
		ClassLoader loader = getClass().getClassLoader();
		InputStream codesXml = loader.getResourceAsStream("codes.xml");
		InputStream webXml = loader.getResourceAsStream("web.xml");
		XMLPath codes = new XMLPath(codesXml);
		XMLPath web = new XMLPath(webXml);
		
		if (codes != null) {
			node = codes.QueryNode("Codes/CodeType[@name='mimeType']/Code[@code='" + mimeType + "']");
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
			node = web.QueryNode("web-app/mime-mapping/mime-type[text()='" + mimeType + "']");
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

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	@Override
	public void auditLog() {
		
	}

}
