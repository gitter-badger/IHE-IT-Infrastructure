package com.gaduo.ihe.utility;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.Node;

public class RSQCommon extends Common {
	public static String registryUrl;

	public final String SOURCE = "_18_source";
	public final String ITI_18_REQUEST = "_ITI-18_request";
	public final String ITI_18_RESPONSE = "_ITI-18_response";

	public RSQCommon() {

	}
	
	public RSQCommon(OMElement request) {
		setRoot_dir();
		setRegistryUrl(request);
	}

	private void setRegistryUrl(OMElement request) {
		String value = "";
		value = this.getValueOfType("RegistryUrl", request);
		RSQCommon.registryUrl = value;
	}

	private void setRoot_dir() {
		Node node = RSQCommon.config.QueryNode("Config/StoragePath/@value");
		RSQCommon.root_dir = (node != null) ? node.getTextContent() : "";
	}
}
