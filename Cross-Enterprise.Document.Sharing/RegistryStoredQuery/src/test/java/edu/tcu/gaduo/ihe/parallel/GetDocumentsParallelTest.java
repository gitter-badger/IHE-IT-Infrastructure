package edu.tcu.gaduo.ihe.parallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.GetDocumentsTest;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;

public class GetDocumentsParallelTest {
	public static Logger logger = Logger.getLogger(GetDocumentsParallelTest.class);

	@Before
	public void setUp() {
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");

	}
	
	@Test
	public void testObjectPerNumber(){
		for(int i = 100; i <= 100; i += 100)
			testObjectPerNumber(i);
	}

	public void testObjectPerNumber(int count) {
		Class<GetDocumentsTest> clazz = GetDocumentsTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("uuid_list/uuid_list_1500.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str = "";
		Set<String> array = new TreeSet<String>();
		List<Set<String>> blocks = new ArrayList<Set<String>>();
		try {
			while((str = br.readLine()) != null){
				if(array.size() < count - 1){
					array.add(str);
				}else{
					array.add(str);
					Set<String> block = new TreeSet<String>();
					Iterator<String> iterator = array.iterator();
					while(iterator.hasNext()){
						String next = iterator.next();
						block.add(next);
					}
					blocks.add(block);
					array.clear();
				}
			}
			if(!array.isEmpty()){
				Set<String> block = new TreeSet<String>();
				Iterator<String> iterator = array.iterator();
				while(iterator.hasNext()){
					String next = iterator.next();
					block.add(next);
				}
				blocks.add(block);
				array.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("\n" + Thread.currentThread().getName() + " =-=-= ParallelGetDocumentBegin-" + count + " =-=-= " + System.currentTimeMillis() + " =-=-= ");
		CountDownLatch latch = new CountDownLatch(blocks.size());
		Executor executor = Executors.newFixedThreadPool(blocks.size());
		int i = 0; 
		Iterator<Set<String>> iterator = blocks.iterator();
		while(iterator.hasNext()){
			Set<String> next = iterator.next();
			GetDocumentsWorker worker = new GetDocumentsWorker(i, latch, next);
			executor.execute(worker);
			i++;
		}
		while (latch.getCount() != 0){
		}
		logger.info("\n" + Thread.currentThread().getName() + " =-=-= ParallelGetDocumentEnd-" + count + " =-=-= " + System.currentTimeMillis() + " =-=-= ");

	}
}
