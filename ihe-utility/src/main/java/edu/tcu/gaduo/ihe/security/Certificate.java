/**
 * 
 */
package edu.tcu.gaduo.ihe.security;

import java.net.URL;

import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.security._interface.ICertificate;

/**
 * @author Gaduo
 */
public class Certificate implements ICertificate {
	public static Logger logger = Logger.getLogger(Certificate.class);


	private static ICertificate instance = null; 
	private Certificate() {
		
	}
	public synchronized static ICertificate getInstance(){
		if(instance == null) {
			instance = new Certificate();
		}
		return instance;
	}
	
	
	public void setCertificate() {
		ClassLoader loader = getClass().getClassLoader();
		String certificate = "";
		certificate = loader.getResource("certificate/openxds_2013/OpenXDS_2013_Keystore.p12").toString().replace("file:/", "");
		logger.info(certificate);
		System.setProperty("javax.net.ssl.keyStore", certificate);
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		// System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		certificate = loader.getResource("certificate/openxds_2013/OpenXDS_2013_Truststore.jks").toString().replace("file:/", "");
		logger.info(certificate);
		System.setProperty("javax.net.ssl.trustStore", certificate);
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
	}

	public void setSSLCertificate() {
		ClassLoader loader = getClass().getClassLoader();
		String certificate = "";
		certificate = loader.getResource("certificate/openxds_2013/OpenXDS_2013_Truststore.jks").toString().replace("file:/", "");
		System.setProperty("javax.net.ssl.trustStore", certificate);
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
	}

	public void setCertificate(String KeyStore, String KeyPass,
			String TrustStore, String TrustPass) {
		try {
			ClassLoader loader = getClass().getClassLoader();
			String certificate = "";
			URL url = loader.getResource("certificate/" + KeyStore);
			certificate = url.toString().replace("file:/", "");
			System.setProperty("javax.net.ssl.keyStore", certificate);
			System.setProperty("javax.net.ssl.keyStorePassword", KeyPass);
			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
			certificate = loader.getResource("certificate/" + TrustStore)
					.toString().replace("file:/", "");
			System.setProperty("javax.net.ssl.trustStore", certificate);
			System.setProperty("javax.net.ssl.trustStorePassword", TrustPass);
			System.setProperty("java.protocol.handler.pkgs",
					"com.sun.net.ssl.internal.www.protocol");
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}
}
