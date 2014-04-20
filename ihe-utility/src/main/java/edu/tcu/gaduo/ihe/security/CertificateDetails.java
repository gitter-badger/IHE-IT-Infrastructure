/**
 * 
 */
package edu.tcu.gaduo.ihe.security;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.security._interface.ICertificate;

/**
 * @author Gaduo
 */
public class CertificateDetails implements ICertificate {
	public static Logger logger = Logger.getLogger(CertificateDetails.class);


	private static ICertificate instance = null; 
	private CertificateDetails() {
		
	}
	public synchronized static ICertificate getInstance(){
		if(instance == null) {
			instance = new CertificateDetails();
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
	
	private String keyStore ;
	private String keyPass ;
	private String trustStore ;
	private String trustPass ;
	private InputStream keyStoreLocation;
	private InputStream trustStoreLocation;

	public void setCertificate(String KeyStore, String KeyPass, String TrustStore, String TrustPass) {
		this.keyStore = KeyStore;
		this.keyPass = KeyPass;
		this.trustStore = TrustStore;
		this.trustPass = TrustPass;
		
		try {
			ClassLoader loader = getClass().getClassLoader();
			String certificate = "";
			URL kURL = loader.getResource("certificate/" + KeyStore);
			trustStoreLocation = kURL.openStream();
//			certificate = url.toString().replace("file:/", "");
//			System.setProperty("javax.net.ssl.keyStore", certificate);
//			System.setProperty("javax.net.ssl.keyStorePassword", KeyPass);
//			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
			
			
			URL tURL =  loader.getResource("certificate/" + TrustStore);
			trustStoreLocation = tURL.openStream();
//			certificate = url02.toString().replace("file:/", "");
//			System.setProperty("javax.net.ssl.trustStore", certificate);
//			System.setProperty("javax.net.ssl.trustStorePassword", TrustPass);
//			System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the keyStore
	 */
	public String getKeyStore() {
		return keyStore;
	}
	/**
	 * @return the keyPass
	 */
	public String getKeyPass() {
		return keyPass;
	}
	/**
	 * @return the trustStore
	 */
	public String getTrustStore() {
		return trustStore;
	}
	/**
	 * @return the trustPass
	 */
	public String getTrustPass() {
		return trustPass;
	}
	/**
	 * @return the keyStoreLocation
	 */
	public InputStream getKeyStoreLocation() {
		return keyStoreLocation;
	}
	/**
	 * @return the trustStoreLocation
	 */
	public InputStream getTrustStoreLocation() {
		return trustStoreLocation;
	}
	
}
