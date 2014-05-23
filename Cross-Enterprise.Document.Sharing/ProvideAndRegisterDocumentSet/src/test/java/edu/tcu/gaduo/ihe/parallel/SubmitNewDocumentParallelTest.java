package edu.tcu.gaduo.ihe.parallel;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class SubmitNewDocumentParallelTest{
	public static Logger logger = Logger.getLogger(SubmitNewDocumentParallelTest.class);
	private String content;

	@Before
	public void setUp() {
		Class<SubmitNewDocumentParallelTest> clazz = SubmitNewDocumentParallelTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("test_data/0050k.xml");
		try {
			byte[] input = IOUtils.toByteArray(is);
			byte[] base64 = Base64.encodeBase64(input);
			content = new String(base64);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test01() {
		int count = 15;
		CountDownLatch latch = new CountDownLatch(count);
		Executor executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			Worker worker = new Worker(i, content, latch);
			executor.execute(worker);
		}
		while (latch.getCount() != 0){
		}
	}
	
	
	
	
}
