package edu.tcu.gaduo.ihe.logfilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class LogFilter extends TestCase {
	public static Logger logger = Logger.getLogger(LogFilter.class);

	public LogFilter(String testName) {
		super(testName);
	}

	protected void setUp() {
	}

	private boolean isClient = true;

	public void testApp() {
		Class<LogFilter> clazz = LogFilter.class;
		ClassLoader loader = clazz.getClassLoader();
		try {
			if (!isClient) {
				filter(loader.getResourceAsStream("log/openxds.log"));
			} else {
				filter(loader.getResourceAsStream("log/ihe-gaduo.log"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void filter(InputStream is) throws IOException, ParseException {
		File file01, file02, file03 = null;
		BufferedWriter bw01, bw02, bw03 = null;
		if (isClient) {
			file01 = new File("logs/log_Client_1");
			file02 = new File("logs/log_Client_2");
		} else {
			file01 = new File("logs/log_Server_1");
			file02 = new File("logs/log_Server_2");
			file03 = new File("logs/log_Server_3");
		}
		bw01 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file01, true)));
		bw02 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file02, true)));
		if (!isClient) {
			bw03 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file03, true)));
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String temp = "";
		while ((temp = br.readLine()) != null) {

			if (temp.contains("***")) {
				System.out.println(temp);
				bw01.write(temp);
				bw01.newLine();
			}
			if (temp.contains("###")) {
				System.out.println(temp);
				bw02.write(temp);
				bw02.newLine();
			}
			if (bw03 != null && temp.contains("+++SQL")) {
				System.out.println(temp);
				bw03.write(temp);
				bw03.newLine();
			}
		}
		br.close();
		if (bw01 != null)
			bw01.close();
		if (bw02 != null)
			bw02.close();
		if (bw03 != null)
			bw03.close();
	}
}
