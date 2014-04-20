package edu.tcu.gaduo.ihe.ebxml;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.MetadataType;

public class TemplateParserTest extends TestCase {
	public static Logger logger = Logger.getLogger(TemplateParserTest.class);
	public TemplateParserTest(String testName) {
		super(testName);
	}

	protected void setUp() {

	}
	
	public void testMetadata() throws JAXBException{
		Class<TemplateParserTest> clazz = TemplateParserTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("metadataGenerator.xml");
		
		JAXBContext jaxbContext = JAXBContext.newInstance(MetadataType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		MetadataType md = (MetadataType) jaxbUnmarshaller.unmarshal(is);
		logger.info(md.buildEbXML());
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.marshal(md, System.out);
	}
}
