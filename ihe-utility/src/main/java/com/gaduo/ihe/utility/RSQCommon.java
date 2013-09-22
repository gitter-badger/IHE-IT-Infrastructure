package com.gaduo.ihe.utility;

import org.apache.axiom.om.OMElement;

public class RSQCommon extends Common {
	public static String registryUrl;

	public final String SOURCE = "_18_source";
	public final String ITI_18_REQUEST = "_ITI-18_request";
	public final String ITI_18_RESPONSE = "_ITI-18_response";

	public RSQCommon() {
		super();
	}

	public RSQCommon(OMElement request) {
		super();
		setRegistryUrl(request);
	}

	private void setRegistryUrl(OMElement request) {
		String value = "";
		value = getAxiom().getValueOfType("RegistryUrl", request);
		RSQCommon.registryUrl = value;
	}
}
