package com.gaduo.ihe.it_infrastructure.xds_transaction.service;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gaduo.ihe.utility.xml.XMLPath;


public class RetrieveDocumentSet {

	private XMLPath codes = null, web = null;

	public RetrieveDocumentSet() {
		super();
		ClassLoader loader = getClass().getClassLoader();
		codes = new XMLPath(loader.getResourceAsStream("codes.xml"));
		web = new XMLPath(loader.getResourceAsStream("web.xml"));
	}

	public OMElement RetrieveGenerator(OMElement source) {
		if (source == null)
			return null;
		RetrieveGenerator r = new RetrieveGenerator();
		OMElement request = r.execution(source);
		return source;
	}

	public String extractExtension(String mimeType) {
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
}
