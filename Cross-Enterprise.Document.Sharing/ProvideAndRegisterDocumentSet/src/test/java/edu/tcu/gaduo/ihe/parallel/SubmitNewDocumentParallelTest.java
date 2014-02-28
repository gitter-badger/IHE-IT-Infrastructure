package edu.tcu.gaduo.ihe.parallel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.apache.log4j.Logger;


/**
 * Unit test for simple App.
 */
public class SubmitNewDocumentParallelTest extends TestCase {
	public static Logger logger = Logger
			.getLogger(SubmitNewDocumentParallelTest.class);

	public SubmitNewDocumentParallelTest(String testName) {
		super(testName);
	}

	protected void setUp() {
	}

	public void test01() {
		int count = 1;
		CountDownLatch latch = new CountDownLatch(count);
		Executor executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			Worker worker = new Worker(i, latch);
			executor.execute(worker);
		}
		while (latch.getCount() != 0){
		}
	}
}
