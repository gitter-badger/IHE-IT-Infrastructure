package edu.tcu.gaduo.ihe;

import java.io.InputStream;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.xml.XMLPath;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		ClassLoader loader = App.class.getClassLoader();
		InputStream configuration = loader
				.getResourceAsStream("configuration.xml");
		XMLPath configurations = new XMLPath(configuration);
		String expression = "Configuration/Connection[@name='合華-Security']/Certificate";
		Node node = configurations.QueryNode(expression);
		String KeyStore = "", Keypass = "", TrustStore = "", Trustpass = "";
		NodeList childNodes = node.getChildNodes();
		for (int k = 0; k < childNodes.getLength(); k++) {
			Node cert = childNodes.item(k);
			if (cert.getNodeName().equalsIgnoreCase("KeyStore")) {
				KeyStore = cert.getTextContent();
			}
			if (cert.getNodeName().equalsIgnoreCase("Keypass")) {
				Keypass = cert.getTextContent();
			}
			if (cert.getNodeName().equalsIgnoreCase("TrustStore")) {
				TrustStore = cert.getTextContent();
			}
			if (cert.getNodeName().equalsIgnoreCase("Trustpass")) {
				Trustpass = cert.getTextContent();
			}
		}
		Certificate cert = new Certificate();
		cert.setCertificate(KeyStore, Keypass, TrustStore, Trustpass);
	}
}
