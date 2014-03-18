package edu.tcu.gaduo.ihe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
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

	/**
	 * Rigourous Test :-)
	 * http://gazelle-gold.wustl.edu/SyslogSender/Sender.jsf
	 */
	public void testApp() {
		try{
			Class<? extends AppTest> clazz = this.getClass();
			ClassLoader loader = clazz.getClassLoader();
			InputStream resource = loader.getResourceAsStream("ITI-45_110112.dcm.xml");
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
	        String app = "SyslogSender";
	        String proc = "183";
	        String messageId = "IHE+RFC-3881";
	        
	        str = "<" + (85) + ">" + version + " " + timestamp + " " + host + " " + app + " " + proc + " " + messageId + " - " + str;
			str = str.replace("${timeStamp}", timestamp);
			System.out.println(str);
			byte[] bytes = str.getBytes("UTF-8");;
//			DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress("203.64.84.214", 2862));
//			DatagramSocket socket = new DatagramSocket();
//			socket.send(packet);
		
			SSLSocketFactory f =  (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) f.createSocket("203.64.84.247", 2862);
			printSocketInfo(socket);
			socket.startHandshake();
			
			Certificate[] serverCerts = socket.getSession().getPeerCertificates();
			System.out.println("Retreived Server's Certificate Chain");
			 
			System.out.println(serverCerts.length + "Certifcates Found\n\n\n");
			for (int i = 0; i < serverCerts.length; i++) {
				Certificate myCert = serverCerts[i];
				System.out.println("====Certificate:" + (i+1) + "====");
				System.out.println("-Public Key-\n" + myCert.getPublicKey());
				System.out.println("-Certificate Type-\n " + myCert.getType());
				System.out.println();
			}
			 
			
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw =new PrintWriter(os);
			pw.print(bytes);
			pw.flush();
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
			pw.close();
			br.close();
			socket.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: " + s.getClass());
		System.out.println("   Remote address = " + s.getInetAddress().toString());
		System.out.println("   Remote port = " + s.getPort());
		System.out.println("   Local socket address = " + s.getLocalSocketAddress().toString());
		System.out.println("   Local address = " + s.getLocalAddress().toString());
		System.out.println("   Local port = " + s.getLocalPort());
		System.out.println("   Need client authentication = " + s.getNeedClientAuth());
		SSLSession ss = s.getSession();
		System.out.println("   Cipher suite = " + ss.getCipherSuite());
		System.out.println("   Protocol = " + ss.getProtocol());
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
