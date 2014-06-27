package edu.tcu.gaduo.ihe.parallel;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;

public class GetDocumentsWorker implements Runnable {
	public static Logger logger = Logger.getLogger(GetDocumentsWorker.class);
	private CountDownLatch latch;
	private int index;
	private Set<String> array;

	public GetDocumentsWorker(int index, CountDownLatch latch, Set<String> array) {
		this.index = index;
		this.array = array;
		this.latch = latch;
	}

	public void run() {
		OneQuery(1, "0010k (" + (index % 10) + ").xml", array);
		latch.countDown();
	}

	private void OneQuery(int numberOfDocument, String FileName, Set<String> array) {
		QueryType query = new QueryType();
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_DOCUMENTS_UUID));
		query.setReturnType(new ReturnTypeType(StoredQueryConstants.LEAFCLASS));
		
		ParameterType p1 = new ParameterType(StoredQueryConstants.DE_ENTRY_UUID);

		Iterator<String> iterator = array.iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			p1.addValues(new ValueType("'" + next + "'"));
		}
		query.addParameters(p1);
		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);

		AdhocQueryResponseType aqs = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(response.toString().getBytes()));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		if(!aqs.getStatus().equals("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success")){
			OneQuery(1, "0010k (" + (index % 10) + ").xml", array);
		}
		
	}

}