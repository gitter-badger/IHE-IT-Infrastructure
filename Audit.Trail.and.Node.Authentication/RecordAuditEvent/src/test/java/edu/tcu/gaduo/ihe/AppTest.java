package edu.tcu.gaduo.ihe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
	 */
	public void testApp() {
		try{
			Class<? extends AppTest> clazz = this.getClass();
			ClassLoader loader = clazz.getClassLoader();
			InputStream is = loader.getResourceAsStream("application-start.xml");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String temp = "", str = "";
			while( (temp = br.readLine()) != null){
				str += temp;
			}
			br.close();
			
			DatagramSocket s = new DatagramSocket();
			InetAddress localMachine = InetAddress.getLocalHost();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-ddHH:mm:ss");
			java.util.Date date = new java.util.Date();
			String value = new Timestamp(date.getTime()).toString();
			value = value.replaceAll("\\D+", "").substring(0, 14);
	        String version = "1";
	        String timestamp = value; //"2014-02-10T00:53:09.624-06:00";
	        String host = localMachine.getHostAddress();
	        String app = "SyslogSender";
	        String proc = "183";
	        String messageId = "IHE+RFC-3881";
	        
	        str = "<" + (85) + ">" + version + " " + timestamp + " " + host + " " + app + " " + proc + " " + messageId + " - " + str;
			System.out.println(str);
			byte[] bytes = str.getBytes("UTF-8");;
			DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress("203.64.84.247", 2861));
			s.send(packet);
			s.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
