package edu.tcu.gaduo.ihe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.openhealthtools.openatna.syslog.core.test.tls.ssl.AuthSSLX509TrustManager;

import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public static Logger logger = Logger.getLogger(AppTest.class);
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}
	
	public void testAppParallel(){
//		int count = 100;
//		CountDownLatch latch = new CountDownLatch(count);
//		Executor executor = Executors.newFixedThreadPool(count);
//		for (int i = 0; i < count; i++) {
//			Worker worker = new Worker(i, latch);
//			executor.execute(worker);
//		}
//		while (latch.getCount() != 0){
//		}
	}
	
	
	/**
	 * Rigourous Test :-)
	 * http://gazelle-gold.wustl.edu/SyslogSender/Sender.jsf
	 */
	public void testApp() {
		
		try{
			Class<? extends AppTest> clazz = this.getClass();
			ClassLoader loader = clazz.getClassLoader();
			InputStream resource = loader.getResourceAsStream("openxds.xml");
			BufferedReader brResource = new BufferedReader(new InputStreamReader(resource));
			String temp = "", str = "";
			while( (temp = brResource.readLine()) != null){
				str += temp;
			}
			brResource.close();
			
			InetAddress localMachine = InetAddress.getLocalHost();
	        String version = "1";
	        String timestamp = formatDate(new java.util.Date()); //"2014-02-10T00:53:09.624-06:00";
	        String host = localMachine.getHostAddress();
	        String app = "GaduoSyslogSender";
	        String proc = "183";
	        String messageId = "IHE+RFC-3881";
	        
	        str = "<" + (85) + ">" + version + " " + timestamp + " " + host + " " + app + " " + proc + " " + messageId + " - " + str;
			str = str.replace("${timeStamp}", timestamp);
			System.out.println(str);
			byte[] bytes = str.getBytes("UTF-8");
			DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress("203.64.84.247", 2861));
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(packet);
			datagramSocket.close();
		

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public String formatDate(Date date) {
        StringBuilder sb = new StringBuilder();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTime(date);
        sb.append(c.get(Calendar.YEAR));
        sb.append('-');
        int f = c.get(Calendar.MONTH);
        if (f < 9) {
            sb.append('0');
        }
        sb.append(f + 1);
        sb.append('-');
        f = c.get(Calendar.DATE);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append('T');
        f = c.get(Calendar.HOUR_OF_DAY);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append(':');
        f = c.get(Calendar.MINUTE);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append(':');
        f = c.get(Calendar.SECOND);
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append('.');
        f = c.get(Calendar.MILLISECOND);
        if (f < 100) {
            sb.append('0');
        }
        if (f < 10) {
            sb.append('0');
        }
        sb.append(f);
        sb.append('Z');
        return sb.toString();
    }
}
