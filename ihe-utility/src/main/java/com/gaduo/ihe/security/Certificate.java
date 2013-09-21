/**
 * 
 */
package com.gaduo.ihe.security;

import org.apache.log4j.Logger;

/**
 * @author Gaduo
 */
public class Certificate {
    public static Logger logger = Logger.getLogger(Certificate.class);
    public void setCertificate() {
        ClassLoader loader = getClass().getClassLoader();
        String certificate = "";
        certificate = loader.getResource("/com/gaduo/ihe/utility/resource/certificate/openxds_2013/OpenXDS_2013_Keystore.p12").toString()
                .replace("file:/", "");
        logger.info(certificate);
        System.setProperty("javax.net.ssl.keyStore", certificate);
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        // System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        certificate = loader.getResource("/com/gaduo/ihe/utility/resource/certificate/openxds_2013/OpenXDS_2013_Truststore.jks").toString()
                .replace("file:/", "");
        System.setProperty("javax.net.ssl.trustStore", certificate);
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
    }
}
