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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpendTime {
	public static Logger logger = Logger.getLogger(SpendTime.class);
	private BufferedWriter bw;

	@Before
	public void init(){
		File file = new File("src/test/resources/log/SpendTime");
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
		InputStream client02 = loader.getResourceAsStream("log/log_Client_2");
		BufferedReader br = new BufferedReader(new InputStreamReader(client02));

		
		try {
			String str = "";
			boolean flag = false;
			List<String> array = new ArrayList<String>();
			String temp = "", title = "";
			int pen = 0;
			int i = 0;
			while((str = br.readLine()) != null){
				String p = null;
				if(str.contains("=-=-=")){
					p = title
							.replace("=-=-= 第", "")
							.replaceAll("\\d+次", "")
							.replace("的每", "")
							.replace("筆 Query一次的實驗 =-=-=", "").trim();
					if(!p.equals("")) 
						pen = Integer.valueOf(p);
					temp = str;
					flag = true;
				}else{
					title = temp;
					array.add(str);
					flag = false;
				}
				if(!array.isEmpty() && flag){
					filterAndCount(title, p, array, pen);
					array.clear();
				}
				i++;
			}
			if(!array.isEmpty()){
				String p = title
						.replace("=-=-= 第", "")
						.replaceAll("\\d+次", "")
						.replace("的每", "")
						.replace("筆 Query一次的實驗 =-=-=", "").trim();
				if(!p.equals("")) pen = Integer.valueOf(p);
				filterAndCount(title, p, array, pen);
				array.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void filterAndCount(String title, String perPen, List<String> array, int pen){

		Map<String, String> map = new TreeMap<String, String>();
		List<String> list = new ArrayList<String>();
		long sum = 0L;
		int i = 1;
		Iterator<String> aIterator = array.iterator();
		while(aIterator.hasNext()){
			String str = aIterator.next();
			String[] temp = str.split("###");
			if(str.equals("")) continue;
			if(temp[1].contains("(I)ITI-18RequestBegin")){	
				final String key = String.format("%04d", i );
				final String value = temp[3].trim();
				map.put(key, value);
				list.add(value);
			}else{
				final String key = String.format("%04d", i );
				final String value = temp[3].trim();
				final String value01 = map.get(key);
				if(value01 == null){
					map.remove(value);
					continue;
				}
				long time = Long.valueOf(value) - Long.valueOf(value01);
				map.put(key, time + "");
				list.add(value);
				sum += time;
				i++;
			}
		}

		int k = 0;
		long first = 0L, last = 0L;
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			String value = iterator.next();
			if(k == 0){
				first = Long.valueOf(value);
			}
			if(k == list.size() -1){
				last = Long.valueOf(value);
			}
			k++;
		}
		
		
		try {
			if(pen != 0){
				bw.write(title + "\t" + perPen + "\t" + (last - first) + "\t" + (sum/((i+1)/2))/pen);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


