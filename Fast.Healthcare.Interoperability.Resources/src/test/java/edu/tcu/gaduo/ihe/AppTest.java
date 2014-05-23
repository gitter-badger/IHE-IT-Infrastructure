package edu.tcu.gaduo.ihe;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.hl7.fhir.instance.formats.parser.XmlParser;
import org.hl7.fhir.instance.model.element.Resource;
import org.hl7.fhir.instance.model.resuorce.Observation;
import org.hl7.fhir.instance.model.type.Coding;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void testApp() {
		Class<AppTest> clazz = AppTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream input = loader.getResourceAsStream("deviceobservationreport-example.xml");
		XmlParser xmlParser = new XmlParser();
		try {
			Resource res = xmlParser.parse(input);
			List<Resource> list = res.getContained();
			Iterator<Resource> iterator = list.iterator();
			while(iterator.hasNext()){
				Resource next = iterator.next();
				if(next instanceof Observation ){
					System.out.println(next.getXmlId());
					List<Coding> coding = ((Observation)next).getName().getCoding();
					Iterator<Coding> cIterator = coding.iterator();
					while(cIterator.hasNext()){
						Coding cNext = cIterator.next();
						System.out.println(cNext.getSystem().getValue());
						System.out.println(cNext.getDisplay().getValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
