package edu.tcu.gaduo.ihe.parallel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class RetrieveDocumentSetParallelTest extends TestCase {
	public static Logger logger = Logger.getLogger(RetrieveDocumentSetParallelTest.class);

	public RetrieveDocumentSetParallelTest(String testName) {
		super(testName);
	}

	protected void setUp() {
	}

	public void testApp() {
		int count = 20;
		CountDownLatch latch = new CountDownLatch(count);
		Executor executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			RetrieveDocumentSetWorker worker = new RetrieveDocumentSetWorker(i, latch);
			executor.execute(worker);
		}
		while (latch.getCount() != 0){
		}
	}
}
