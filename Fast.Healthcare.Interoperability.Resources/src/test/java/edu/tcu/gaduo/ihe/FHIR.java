package edu.tcu.gaduo.ihe;

import static org.junit.Assert.*;

import org.hl7.fhir.instance.model.factory.ResourceFactory;
import org.junit.Test;

public class FHIR {

	@Test
	public void test() {
		try {
			ResourceFactory.createResource("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
