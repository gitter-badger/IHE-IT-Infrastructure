package edu.tcu.gaduo.ihe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class LogFilter extends TestCase {
	public static Logger logger = Logger.getLogger(LogFilter.class);

	public LogFilter(String testName) {
		super(testName);
	}
	protected void setUp() {
	}

	public void testApp() {
		Class<LogFilter> clazz = LogFilter.class;
		ClassLoader loader = clazz.getClassLoader();
		try {
			filter(loader.getResourceAsStream("log/openxds.log.5"));
			filter(loader.getResourceAsStream("log/openxds.log.4"));
			filter(loader.getResourceAsStream("log/openxds.log.3"));
			filter(loader.getResourceAsStream("log/openxds.log.2"));
			filter(loader.getResourceAsStream("log/openxds.log.1"));
			filter(loader.getResourceAsStream("log/openxds.log"));
//			filter(loader.getResourceAsStream("log/ihe-gaduo.log"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
}
	
	public void filter(InputStream is) throws IOException, ParseException{
		File file01  = new File("logs/log012");
		File file02  = new File("logs/log022");
		BufferedWriter bw01 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file01, true)));
		BufferedWriter bw02 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file02, true)));
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String temp = "";
		String tmp = null ;
		while ((temp = br.readLine()) != null) {
			
			if (temp.contains("***")) {
				System.out.println(temp);
				bw01.write(temp);
				bw01.newLine();
			}
			if (temp.contains("###") || temp.contains("***(5)Registry(42)")) {
				if( temp.contains("***(5)Registry(42)")){
					String t[] = tmp.split("INFO");
					String[] tt = temp.split(" ");
					String d = t[0].replace("[", "");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date =  sdf.parse(d);
					temp = "###(V)ITI-42ResponseBegine:### " + tt[1] + " ### " + date.getTime();
					System.out.println(temp);
				}else{
					System.out.println(temp);
				}
				bw02.write(temp);
				bw02.newLine();
			}
			tmp  = new String(temp);
		}
		br.close();
		bw01.close();
		bw02.close();
	}
}
