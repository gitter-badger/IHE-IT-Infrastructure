package edu.tcu.gaduo.ihe.security._interface;

import java.io.InputStream;

public interface ICertificate {
	void setCertificate(String keyStore, String keypass, String trustStore, String trustpass);
	void setCertificate();
	void setSSLCertificate();
	String getKeyStore();
	String getKeyPass() ;
	String getTrustStore();
	String getTrustPass();
	InputStream getKeyStoreLocation() ;
	InputStream getTrustStoreLocation() ;
}
