package com.gaduo.ihe;

import junit.framework.TestCase;

import com.gaduo.ihe.utility.LoadTesDatatUtil;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	LoadTesDatatUtil load;

	public AppTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}
	
	public void testOne(){
		load.loadTestDataToOMElement("retrieve_template.xml");
	}

}
