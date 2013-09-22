package com.gaduo.ihe;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gaduo.ihe.it_infrastructure.consistent_time.transaction.service.NTPClient;
import com.gaduo.ihe.it_infrastructure.consistent_time.transaction.service.TimeClient;

/**
 * Hello world!
 * 
 */
public class App {
	public static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		String host = (args.length >= 1) ? args[0] : "";
		String type = (args.length >= 2) ? args[1] : "";
		String udp = (args.length >= 3) ? args[2] : "";
		Date date = null;
		if (type.equalsIgnoreCase("-timeclient")) {
			logger.info("TimeClient");
			TimeClient tc = new TimeClient();
			if (args.length == 2) {
				date = tc.timeTCP(host);
			} else if (args.length == 3 && udp.equals("-udp")) {
				date = tc.timeUDP(host);
			}
		} else if (type.equalsIgnoreCase("-ntpclient")) {
			logger.info("NTPClient");
			logger.info("Host : " + host);
			TimeInfo info = null;
			if (!host.equals("")) {
				try {
					NTPUDPClient client = new NTPUDPClient();
					client.setDefaultTimeout(10000);
					client.open();
					InetAddress hostAddr = InetAddress.getByName(host);
					info = client.getTime(hostAddr);
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				NTPClient nptc = new NTPClient();
				date = nptc.processResponse(info);
			}
		}
		logger.info(date);
		// -------------
		if (date != null) {
			try {
				String name = System.getProperty("os.name");
				logger.info(name);
				String cmd = null;
			    SimpleDateFormat sdf = null;
				if (name.contains("Windows")) { // Window 操作系统
					sdf = new SimpleDateFormat("hh:mm:ss");
					cmd = " cmd /c time " + sdf.format(date);
					logger.info(cmd);
					Runtime.getRuntime().exec(cmd); // 修改时间
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					cmd = " cmd /c date " + sdf.format(date);
					logger.info(cmd);
					Runtime.getRuntime().exec(cmd); // 修改日期
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// -------------

	}
}
