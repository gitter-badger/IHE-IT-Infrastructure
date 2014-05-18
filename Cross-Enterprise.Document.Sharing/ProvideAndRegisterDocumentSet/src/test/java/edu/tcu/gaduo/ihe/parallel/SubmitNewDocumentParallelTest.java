package edu.tcu.gaduo.ihe.parallel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class SubmitNewDocumentParallelTest{
	public static Logger logger = Logger.getLogger(SubmitNewDocumentParallelTest.class);

	@Before
	public void setUp() {
	}

	@Test
	public void test01() {
		int count = 15;
		CountDownLatch latch = new CountDownLatch(count);
		Executor executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			Worker worker = new Worker(i, latch);
			executor.execute(worker);
		}
		while (latch.getCount() != 0){
			System.out.println("Done!\t" + latch.getCount());
		}
	}
}
