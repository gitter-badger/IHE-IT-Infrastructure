package edu.tcu.gaduo.ihe.logfilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AverageTest {
	public static Logger logger = Logger.getLogger(AverageTest.class);
	private BufferedWriter bw;

	@Before
	public void init(){
		File file = new File("src/test/resources/log/Average");
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void destroy(){
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void count(){
		Class<SpendTime> clazz = SpendTime.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream client02 = loader.getResourceAsStream("log/SpendTime");
		BufferedReader br = new BufferedReader(new InputStreamReader(client02));

		try {
			String str = "";
			int i = 1, j = 1;
			long sum = 0;
			while((str  = br.readLine()) != null){
				String[] temp = str.split("\t");
				long time = Long.valueOf(temp[2]);
				if(j <= 16 && i%29 == 0){
					i = 0;
					bw.write(j*50 + "\t" + sum/29);
					bw.newLine();
					j++;
					sum = 0;
				} else if(j > 16 && i%30 == 0){
					i = 0;
					bw.write(j*50 + "\t" + sum/30);
					bw.newLine();
					j++;
					sum = 0;
				} else {
					sum += time;
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
