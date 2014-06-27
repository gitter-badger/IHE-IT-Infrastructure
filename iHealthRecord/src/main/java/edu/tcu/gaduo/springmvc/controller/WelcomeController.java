package edu.tcu.gaduo.springmvc.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExternalIdentifierType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExtrinsicObjectType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryPackageType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.SlotType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.ihe.DocumentResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_43.ebxml.ihe.RetrieveDocumentSetResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.pojo.DocumentRequest;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.springmvc.view_model.Document;
import edu.tcu.gaduo.springmvc.view_model.Folder;



@Controller
public class WelcomeController {

	public static Logger logger = Logger.getLogger(WelcomeController.class);

	Set<Document> documents;
	String registryEndpoint;
	String repositoryEndpoint;
	
	public WelcomeController(){
		documents = new TreeSet<Document>();
		Properties pro = new Properties();
		Class<WelcomeController> clazz = WelcomeController.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("iHealthRecord.properties");
		repositoryEndpoint = "http://203.64.84.214:8020/axis2/services/xdsrepositoryb?wsdl";
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){

//		ICertificate cert = CertificateDetails.getInstance();
//		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		
		List<Folder> folerList = new ArrayList<Folder>();
		
		QueryType query = new QueryType();
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.FIND_FOLDERS_UUID));
		query.setReturnType(new ReturnTypeType("LeafClass"));
		ParameterType p1 = new ParameterType(StoredQueryConstants.FOL_PATIENT_ID);
		p1.addValues(new ValueType("'20131214^^^&1.3.6.1.4.1.21367.2005.3.7&ISO'"));
		query.addParameters(p1);
		ParameterType p2 = new ParameterType(StoredQueryConstants.FOL_STATUS);
		p2.addValues(new ValueType("'urn:oasis:names:tc:ebxml-regrep:StatusType:Approved'"));
		query.addParameters(p2);
		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);
		InputStream is = new ByteArrayInputStream(response.toString().getBytes());
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			List<RegistryPackageType> rList = aqs.getRegistryObjectList().getRegistryPackages();
			if(rList != null){
				Iterator<RegistryPackageType> iterator = rList.iterator();
				while(iterator.hasNext()){
					Folder folder = new Folder();
					RegistryPackageType next = iterator.next();
					folder.setEntryUUID(next.getId());
					folder.setName(next.getName().getLocalizedString().getValue());
					List<SlotType> sList = next.getSlots();
					Iterator<SlotType> sIterator = sList.iterator();
					while(sIterator.hasNext()){
						SlotType sNext = sIterator.next();
						List<edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType> vList = sNext.getValueList().getValues();
						Iterator<edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType> vIterator = vList.iterator();
						while(vIterator.hasNext()){
							edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType vNext = vIterator.next();
							folder.setLastUpdateTime(vNext.getText());
							break;
						}
					}
					folerList.add(folder);
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("folerList", folerList);

		return "welcome/index";
	}
	
	
    @RequestMapping(value = "/welcome/GetFolderAndContents/{entryuuid}/", method = RequestMethod.GET)
    @ResponseBody
    public Set<Document> getFolderAndContents(@PathVariable("entryuuid") String entryuuid) {
		try {
			documents.clear();
			QueryType query = new QueryType();
			query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_FOLDER_AND_CONTENTS_UUID));
			query.setReturnType(new ReturnTypeType("LeafClass"));
			ParameterType p1 = new ParameterType(StoredQueryConstants.FOL_ENTRY_UUID);
			p1.addValues(new ValueType("'" + entryuuid + "'")); //urn:uuid:1ded6009-68e4-4a4f-9cd5-48f6be1eaa7f
			query.addParameters(p1);
			
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			OMElement response = rsq.QueryGenerator(query);
			InputStream is = new ByteArrayInputStream(response.toString().trim().getBytes("utf-8"));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqs = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);

			
			List<ExtrinsicObjectType> eList = aqs.getRegistryObjectList().getExtrinsicObjects();

			if(eList != null){
				Iterator<ExtrinsicObjectType> eIterator = eList.iterator();
				while(eIterator.hasNext()){
					Document doc = new Document();
					ExtrinsicObjectType eNext = eIterator.next();
					doc.setEntryUUID(eNext.getId());
					doc.setName(eNext.getName().getLocalizedString().getValue());
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
							doc.setUniqueId(uniqueId);
						}
					}
					documents.add(doc);
				}
				return documents;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
        return null;
    }
    
    @RequestMapping(value = "/welcome/GetDocuemt/{entryuuid}/", method = RequestMethod.GET)
    @ResponseBody
    public Document getDocuemt(@PathVariable("entryuuid") String entryuuid){
    	Iterator<Document> iterator = documents.iterator();
    	while(iterator.hasNext()){
    		Document next = iterator.next();
    		if(next.getEntryUUID().equals(entryuuid)){
    			return next;
    		}
    	}
		return null;
    }

    
    @RequestMapping(value = "/welcome/RetrieveDocument/{uniqueId}/", method = RequestMethod.GET)
    @ResponseBody
    public String retrieveDocument(@PathVariable("uniqueId") String uniqueId){
		

		RetrieveDocumentSetResponseType rdst = null;
		try {
			RetrieveDocumentSet rds = new RetrieveDocumentSet();
			rds.setRepositoryUrl(repositoryEndpoint);
			Set<DocumentRequest> documentIdList = new TreeSet<DocumentRequest>();
			documentIdList.add(new DocumentRequest("1.3.6.1.4.1.21367.2010.1.2.1125.103", uniqueId, ""));
			OMElement response = rds.RetrieveGenerator(documentIdList);

			ByteArrayInputStream is = new ByteArrayInputStream(response.toString().getBytes("utf-8"));
			JAXBContext jaxbContext = JAXBContext.newInstance(RetrieveDocumentSetResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rdst = (RetrieveDocumentSetResponseType) jaxbUnmarshaller.unmarshal(is);
			
			List<DocumentResponseType> documents = rdst.getDocumentResponses();
			Iterator<DocumentResponseType> dIterator = documents.iterator();
			while(dIterator.hasNext()){
				DocumentResponseType dNext = dIterator.next();
				String document = dNext.getDocument().getValue();
				String mimeType = dNext.getMimeType().getValue();
				return "{\"document\":\"" + document + "\", \"mimeType\":\"" + mimeType + "\"}";
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		return null;
    }
}
