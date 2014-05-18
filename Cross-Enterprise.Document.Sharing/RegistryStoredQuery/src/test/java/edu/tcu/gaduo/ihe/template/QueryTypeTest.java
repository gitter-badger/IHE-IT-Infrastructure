package edu.tcu.gaduo.ihe.template;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.tcu.gaduo.ihe.ebxml.FindDocumentTest;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;

public class QueryTypeTest {
	public static Logger logger = Logger.getLogger(QueryTypeTest.class);

	@Test
	public void test() {
		try{
			Class<FindDocumentTest> clazz = FindDocumentTest.class;
			ClassLoader loader = clazz.getClassLoader();
			InputStream is = loader.getResourceAsStream("template/_GetFolders.xml");
			
			JAXBContext jaxbContext = JAXBContext.newInstance(QueryType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			QueryType query = (QueryType) jaxbUnmarshaller.unmarshal(is);

			logger.info(query.getRegistryUrl().getValue());
			logger.info(query.getQueryUUID().getValue());
			logger.info(query.getReturnType().getValue());
			List<ParameterType> ps = query.getParameters();
			Iterator<ParameterType> iterator = ps.iterator();
			while(iterator.hasNext()){
				ParameterType next = iterator.next();
				logger.info(next.getName());
				List<ValueType> vs = next.getValues();
				Iterator<ValueType> vIterator = vs.iterator();
				while(vIterator.hasNext()){
					ValueType vNext = vIterator.next();
					logger.info(vNext.getValue());
				}
			}
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.marshal(query, System.out);
		}catch(JAXBException e){
			e.printStackTrace();
		}
	}

}
