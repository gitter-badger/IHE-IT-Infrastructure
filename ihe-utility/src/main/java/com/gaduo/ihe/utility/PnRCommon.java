package com.gaduo.ihe.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.UUID;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.Node;

public class PnRCommon extends Common  {
	public static Set<String> ObjectRef;
	public static String OID;
	public static String bootTimestamp;
	public static String SourceID;
	public static String repositoryUrl;

	public final String SOURCE = "_41_source";
	public final String ITI_41_REQUEST = "_ITI-41_request";
	public final String ITI_41_RESPONSE = "_ITI-41_response";

	public PnRCommon() {

	}

	public String createUUID() {
		UUID uid = UUID.randomUUID();
		return "urn:uuid:" + uid.toString();
	}

	public PnRCommon(OMElement source) {
		setRoot_dir();
		setOID(source);
		setServerIP();
		setBootTimestamp();
		setSourceID(source);
		setRepositoryUrl(source);
	}

	private void setSourceID(OMElement request) {
		String value = this.getValueOfType("SourceID", request);
		PnRCommon.SourceID = value;
	}

	private void setRepositoryUrl(OMElement request) {
		String value = "";
		value = this.getValueOfType("RepositoryUrl", request);
		PnRCommon.repositoryUrl = value;
	}

	private void setBootTimestamp() {
		PnRCommon.bootTimestamp = this.createTime();
	}

	private void setServerIP() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			Common.IP = localHost.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void setOID(OMElement request) {
		String value = "";
		value = this.getValueOfType("SourceID", request);
		PnRCommon.OID = value;
	}

	private void setRoot_dir() {
		Node node = PnRCommon.config.QueryNode("Config/StoragePath/@value");
		PnRCommon.root_dir = (node != null) ? node.getTextContent() : "";
	}
}