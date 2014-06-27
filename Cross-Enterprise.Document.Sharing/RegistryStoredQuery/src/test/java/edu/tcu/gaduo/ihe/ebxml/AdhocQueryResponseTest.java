package edu.tcu.gaduo.ihe.ebxml;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExternalIdentifierType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExtrinsicObjectType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.SlotType;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class AdhocQueryResponseTest {

	@Test
	public void test() {
		IAxiomUtil axiom = AxiomUtil.getInstance();
		byte[] array = axiom.resourcesToByteArray("20140606220100_ITI-18_response_GET_FOLDER_AND_CONTENTS_http-8080-2.xml");
		AdhocQueryResponseType aqs = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(array));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		List<ExtrinsicObjectType> eList = aqs.getRegistryObjectList().getExtrinsicObjects();
		if(eList != null){
			Iterator<ExtrinsicObjectType> eIterator = eList.iterator();
			while(eIterator.hasNext()){
				ExtrinsicObjectType eNext = eIterator.next();
				System.out.println(eNext.getId());
				System.out.println(eNext.getName().getLocalizedString().getValue());
				// Slot
				List<SlotType> sList = eNext.getSlots();
				Iterator<SlotType> sIterator = sList.iterator();
				while(sIterator.hasNext()){
					SlotType sNext = sIterator.next();
					List<edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType> vList = sNext.getValueList().getValues();
					Iterator<edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType> vIterator = vList.iterator();
					while(vIterator.hasNext()){
						edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType vNext = vIterator.next();
						break;
					}
				}
				// ExternalIdentifier
				List<ExternalIdentifierType> eifList = eNext.getExternalIdentifiers();
				Iterator<ExternalIdentifierType> eifIterator = eifList.iterator();
				while(eifIterator.hasNext()){
					ExternalIdentifierType eifNext = eifIterator.next();
					if(eifNext.getIdentificationScheme().equals("urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab")){
						String uniqueId = eifNext.getValue();
						System.out.println(uniqueId);
					}
				}
			}
		}
	}

}
