package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.DocumentRelationshipsConstants;
import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.AssociationType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.DocumentType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.FolderType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.PatientInfoType;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class MetadataGenerator_2_0 {
	public static Logger logger = Logger.getLogger(MetadataGenerator_2_0.class);
	private MetadataType md;
	
	public MetadataGenerator_2_0() {
	}

	public OMElement execution(OMElement source) {
		MetadataType md = null;
		String s = source.toString();
		InputStream is = new ByteArrayInputStream(s.getBytes());
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MetadataType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			md = (MetadataType) jaxbUnmarshaller.unmarshal(is);
			PatientInfoType pInfo = md.getPatientInfo();
			String sourcePatientId = md.getSourcePatientId();
			String sourceID = md.getSourceID();
			md.setSourceID(sourceID);
			Iterator<DocumentType> dIterator = md.getDocuments().getList().iterator();
			while(dIterator.hasNext()){
				DocumentType d = dIterator.next();
				d.setSourcePatientId(sourcePatientId);
				d.setPatientInfo(pInfo);
			}
			
			Iterator<FolderType> fIterator = md.getFolders().getList().iterator();
			while(fIterator.hasNext()){
				FolderType f = fIterator.next();
				f.setSourcePatientId(sourcePatientId);
				Iterator<DocumentType> _dIterator = f.getDocuments().getList().iterator();
				while(_dIterator.hasNext()){
					DocumentType d = _dIterator.next();
					d.setSourcePatientId(sourcePatientId);
					d.setPatientInfo(pInfo);
				}
			}

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(md, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		if(md == null)
			return null;
		return execution(md);
	}	
	
	public OMElement execution(MetadataType md){
		this.md = md;
		IAxiomUtil axiom = AxiomUtil.getInstance();
		/* ProvideAndRegisterDocumentSetRequest */
		OMElement ProvideAndRegisterDocumentSetRequest = axiom.createOMElement(EbXML.ProvideAndRegisterDocumentSetRequest, Namespace.IHE);
		/* SubmitObjectsRequest */
		OMElement SubmitObjectsRequest = axiom.createOMElement(EbXML.SubmitObjectsRequest, Namespace.LCM3);
		ProvideAndRegisterDocumentSetRequest.addChild(SubmitObjectsRequest);
		/* SubmitObjectsRequest */
		OMElement RegistryObjectList = axiom.createOMElement(EbXML.RegistryObjectList, Namespace.RIM3);
		SubmitObjectsRequest.addChild(RegistryObjectList);
		
		
		String ssId = md.getId();
		
		// ------ DocumentEntry ------
		List<DocumentType> documents = md.getDocuments().getList();
		Iterator<DocumentType> iterator01 = documents.iterator();
		while(iterator01.hasNext()){
			DocumentType d = iterator01.next();
			String deId = d.getId();
			OMElement deElement = d.buildEbXML();
			
			// ------ HasMember (4) SubmissionSet HasMemeber DocumentEntry ------
			AssociationType hm04 = new AssociationType();
			hm04.setSourceObject(ssId);
			hm04.setTargetObject(deId);
			hm04.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
			hm04.setNote("Original");
			OMElement hm04Element = hm04.buildEbXML();
			
			RegistryObjectList.addChild(deElement);
			RegistryObjectList.addChild(hm04Element);
			
			// Relationship
			OMElement rElement = d.buildEbXMLRelationship();
			if(rElement != null)
				RegistryObjectList.addChild(rElement);
			
			// ------ Document ------
			OMElement dElement = d.buildEbXMLDocument();
			if(dElement != null)
				ProvideAndRegisterDocumentSetRequest.addChild(dElement);
		}
		
		// Scenario 1. ------ Folder ------
		List<FolderType> folders = md.getFolders().getList();
		Iterator<FolderType> iterator02 = folders.iterator();
		while(iterator02.hasNext()){
			FolderType f = iterator02.next();
			String fId = f.getId();
			String existingFolderId = f.getExistingFolderId();
			if(existingFolderId == null){
				OMElement element = f.buildEbXML();
				RegistryObjectList.addChild(element);
				OMElement fCElement = f.buildEbXMLClassification();
				RegistryObjectList.addChild(fCElement);
			}
			// ------ HasMember (1) SubmissionSet HasMemeber Folder ------ 
			AssociationType hm01 = new AssociationType();
			hm01.setSourceObject(ssId);
			hm01.setTargetObject(fId);
			hm01.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
			OMElement hm01Element = hm01.buildEbXML();
			
			if(hm01Element != null)
				RegistryObjectList.addChild(hm01Element);
			
			// ------ DocumentEntry ------
			List<DocumentType> documents02 = f.getDocuments().getList();
			Iterator<DocumentType> iterator03 = documents02.iterator();
			while(iterator03.hasNext()){
				DocumentType d = iterator03.next();
				String deId = d.getId();
				String existingDocumentId = d.getExistingDocumentId();
				OMElement deElement = d.buildEbXML();
				
				AssociationType hm02 = new AssociationType();
				if(existingFolderId == null){ // New Folder
					if(existingDocumentId == null){ // New DocumentEntry
						// ------ HasMember (2) Folder HasMemeber DocumentEntry ------ 
						hm02.setSourceObject(fId);
						hm02.setTargetObject(deId);
						hm02.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
					}else{ // Old DocumentEntry
						// Scenario 3. ------ Folder submitted and existing DocumentEntry added to it ------ 
						hm02.setSourceObject(fId);
						hm02.setTargetObject(existingDocumentId);
						hm02.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
					}
				}else{  // Old Folder
					if(existingDocumentId == null){  // New DocumentEntry
						// Scenario 4. ------ DocumentEntry submitted and added to existing Folder ------
						hm02.setSourceObject(existingFolderId);
						hm02.setTargetObject(deId);
						hm02.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
					}else{ // Old DocumentEntry
						// Scenario 2. ------ Add existing DocumentEntry to existing Folder ------ 11971
						hm02.setSourceObject(existingFolderId);
						hm02.setTargetObject(existingDocumentId);
						hm02.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
					}
				}
				OMElement hm02Element = hm02.buildEbXML();
				
				// ------ HasMember (3) SubmissionSet HasMemeber HasMember (2) ------ 
				String hm02Id = hm02.getId();
				AssociationType hm03 = new AssociationType();
				hm03.setSourceObject(ssId);
				hm03.setTargetObject(hm02Id);
				hm03.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
				OMElement hm03Element = hm03.buildEbXML();
				if(existingDocumentId == null){
					// ------ HasMember (4) SubmissionSet HasMemeber DocumentEntry ------
					AssociationType hm04 = new AssociationType();
					hm04.setSourceObject(ssId);
					hm04.setTargetObject(deId);
					hm04.setAssociation(DocumentRelationshipsConstants.HAS_MEMBER);
					hm04.setNote("Original");
					OMElement hm04Element = hm04.buildEbXML();
					if(hm04Element != null)
						RegistryObjectList.addChild(hm04Element);
				}
				if(deElement != null)
					RegistryObjectList.addChild(deElement);
				if(hm02Element != null)
					RegistryObjectList.addChild(hm02Element);
				if(hm03Element != null)
					RegistryObjectList.addChild(hm03Element);
				
				// ------ Document ------
				OMElement dElement = d.buildEbXMLDocument();
				if(dElement != null)
					ProvideAndRegisterDocumentSetRequest.addChild(dElement);
			}
		}

		// ------ SubmissionSet ------
		OMElement ssElement = md.buildEbXML();
		RegistryObjectList.addChild(ssElement);
		OMElement ssCElement = md.buildEbXMLClassification();
		RegistryObjectList.addChild(ssCElement);
		
		return ProvideAndRegisterDocumentSetRequest;
	}

	public MetadataType getMetaData() {
		return md;
	}
	
}