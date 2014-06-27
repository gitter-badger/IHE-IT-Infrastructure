package edu.tcu.gaduo.ihe.logfilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SpendTime {
	public static Logger logger = Logger.getLogger(SpendTime.class);

	@Test
	public void count(){
		Class<SpendTime> clazz = SpendTime.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream client02 = loader.getResourceAsStream("log/log_Client_2");
		BufferedReader br = new BufferedReader(new InputStreamReader(client02));
		
		
		Map<String, String> map = new HashMap<String, String>();
		
		String str = "";
		try {
			while((str = br.readLine()) != null){
				String[] temp = str.split("###");
				if(temp[1].contains("(I)ITI-41RequestBegin")){	
					String key = temp[2].trim();
					String value = temp[3].trim();
					logger.info(key + "\t" + value);
					map.put(key, value);
				}else{
					String key = temp[2].trim();
					String value = temp[3].trim();
					logger.info(key + "\t" + value);
					String value01 = map.get(key);
					long time = Long.valueOf(value) - Long.valueOf(value01);
					map.put(key, time + "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int i = 1;
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = map.get(key);
			logger.info((i++) + " :" + key + "\t" + value);
		}
	}
}
