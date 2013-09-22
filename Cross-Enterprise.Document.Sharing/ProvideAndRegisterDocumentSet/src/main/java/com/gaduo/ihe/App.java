package com.gaduo.ihe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.log4j.BasicConfigurator;

import com.gaduo.ihe.it_infrastructure.xds_transaction.service.ProvideAndRegisterDocumentSet;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        BasicConfigurator.configure();  
		File file = new File(
				"C:\\Binary\\Metadata\\20130922133720_41_source_12049.xml");

		FileInputStream fis = new FileInputStream(file);
		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader reader = xif.createXMLStreamReader(fis);
		StAXOMBuilder builder = new StAXOMBuilder(reader);
		OMElement source = builder.getDocumentElement();
		ProvideAndRegisterDocumentSet PnR = new ProvideAndRegisterDocumentSet();
		PnR.MetadataGenerator(source);
	}
}
