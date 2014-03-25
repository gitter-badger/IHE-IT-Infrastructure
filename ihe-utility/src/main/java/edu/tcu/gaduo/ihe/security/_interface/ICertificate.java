package edu.tcu.gaduo.ihe.security._interface;

public interface ICertificate {
	void setCertificate(String keyStore, String keypass, String trustStore, String trustpass);
	void setCertificate();
	void setSSLCertificate();
}
