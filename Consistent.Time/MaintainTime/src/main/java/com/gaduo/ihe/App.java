package com.gaduo.ihe;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import com.gaduo.consistent.time.client.NTPClient;
import com.gaduo.consistent.time.client.TimeClient;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		TimeClient tc = new TimeClient();
		if (args.length == 1) {
			try {
				tc.timeTCP(args[0]);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else if (args.length == 2 && args[0].equals("-udp")) {
			try {
				tc.timeUDP(args[1]);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			System.err.println("Usage: TimeClient [-udp] <hostname>");
			System.exit(1);
		}

		//-------------
		try {
			/**
			 * 获取操作系统的名称
			 * */
			String name = System.getProperty("os.name");
			System.out.println(name);
			if (name.contains("Windows")) { // Window 操作系统
				String cmd = " cmd /c time 19:50:00";
				Runtime.getRuntime().exec(cmd); // 修改时间
				cmd = " cmd /c date 2012-01-02";
				Runtime.getRuntime().exec(cmd); // 修改日期
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//-------------
		NTPClient nptc = new NTPClient();

		if (args == null || args.length == 0) {
			System.err.println("Usage: NTPClient <hostname-or-address-list>");
			System.exit(1);
		}

		NTPUDPClient client = new NTPUDPClient();
		// We want to timeout if a response takes longer than 10 seconds
		client.setDefaultTimeout(10000);
		try {
			client.open();
			for (int i = 0; i < args.length; i++) {
				System.out.println();
				try {
					InetAddress hostAddr = InetAddress.getByName(args[i]);
					System.out.println("> " + hostAddr.getHostName() + "/"
							+ hostAddr.getHostAddress());
					TimeInfo info = client.getTime(hostAddr);
					nptc.processResponse(info);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		client.close();
		
	}
}
