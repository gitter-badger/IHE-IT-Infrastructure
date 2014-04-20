package edu.tcu.gaduo.ihe;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import org.openhealthtools.openatna.syslog.core.test.tls.ssl.AuthSSLSocketFactory;
import org.openhealthtools.openatna.syslog.core.test.tls.ssl.KeystoreDetails;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ServerSocketTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public ServerSocketTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ServerSocketTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(2861);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		while (true) {
			Socket socket = null;
			byte[] data = null;
			try {
				socket = server.accept();
				DataInputStream is;
				is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				data = new byte[4096];
				is.read(data);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (server != null) {
					try {
						server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			long timestamp = System.currentTimeMillis();
			System.out.println(timestamp);
			File file = new File(timestamp + ".xml");
			DataOutputStream dos = null;
			try {
				dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				dos.write(data);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
//	public void testSSLSocketServer(){
//		URL u = Thread.currentThread().getContextClassLoader().getResource("keys/serverKeyStore");
//		KeystoreDetails key = new KeystoreDetails(u.toString(), "serverStorePass", "myServerCert", "password");
//		URL uu = Thread.currentThread().getContextClassLoader().getResource("keys/clientKeyStore");
//		KeystoreDetails trust = new KeystoreDetails(uu.toString(), "clientStorePass", "myClientCert");
//		try {
//			AuthSSLSocketFactory f = new AuthSSLSocketFactory(key, trust);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
