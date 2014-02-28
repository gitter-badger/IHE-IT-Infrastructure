package edu.tcu.gaduo.zk.view_model.customer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Filedownload;


import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.IResponse;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.Response_ITI_18;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.Response_ITI_43;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.RetrieveResult;
import edu.tcu.gaduo.webservice.LeafClass.MetadataParser;
import edu.tcu.gaduo.webservice.LeafClass.MetadataParser.Entry;
import edu.tcu.gaduo.zk.model.CompanyInfomation;
import edu.tcu.gaduo.zk.model.QueryGenerator;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValue;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValuesImpl;
import edu.tcu.gaduo.zk.model.list_item.Item;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.FindFolders;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetFolderAndContents;

public class IndexVM {
	public static Logger logger = Logger.getLogger(IndexVM.class);

	List<Item> list = new ArrayList<Item>();
	Deque<Item> parent = new LinkedList<Item>();
	private String registryEndpoint, repositoryEndpoint ;
	private String patientId;
	
	public IndexVM(){
    	Properties prop = new Properties();
    	ClassLoader loader = IndexVM.class.getClassLoader();
    	InputStream is = loader.getResourceAsStream("iHealthRecord.properties");
		try {
			prop.load(is);
			registryEndpoint = prop.getProperty("document.registry.endpoint");
			repositoryEndpoint = prop.getProperty("document.repository.endpoint");
			patientId = prop.getProperty("patient.id");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Init
	public void init() {
		list.clear();
		parent.clear();
		Set<String> set = listFolders();
		Item root = new Item("-", "Home", "", "", "/", null);
		while (!set.isEmpty()) {
			String next = ((TreeSet<String>)set).pollFirst();
			logger.info(next);
			DistributedGetFolders gf = new DistributedGetFolders();
			gf.setId(next);
			gf.query();
			list.add(new Item(next, gf.getLastUpdateTime(), gf.getTitle(), gf.getDescription(), Item.FOLDER, root));
			
		}

		list.add(new Item("", "2008-09-25", "慈濟醫院花蓮院區", "新生健康檢查", Item.FOLDER, root));
		list.add(new Item("", "2008-12-07", "慈濟醫院花蓮院區", "皮膚科", Item.FOLDER, root));
		list.add(new Item("", "2009-04-18", "慈濟醫院花蓮院區", "腸胃科", Item.FOLDER, root));
		list.add(new Item("", "2011-10-11", "慈濟醫院花蓮院區", "腸胃科", Item.FOLDER, root));
		list.add(new Item("", "2013-05-06", "慈濟醫院花蓮院區", "皮膚科", Item.FOLDER, root));
		list.add(new Item("", "2013-09-21", "慈濟醫院花蓮院區", "新生健康檢查", Item.FOLDER, root));
	}

	public List<Item> getList() {
		return list;
	}
	
	public Deque<Item> getParent(){
		return parent;
	}
	
	@Command
	@NotifyChange({ "list", "parent"})
	public void goToTheNextLayer(@BindingParam("each") Item each){
		
		if(each.getType().equals(Item.DOCUMENT)){
			Set<String> documentIdList = new TreeSet<String>();
			documentIdList.add(each.getUniqueId());
//			String repEndpoint = repositoryMapping();
			String repEndpoint = repositoryEndpoint;
			new Certificate().setCertificate();
			RetrieveDocumentSet rds = new RetrieveDocumentSet();
			rds.RetrieveGenerator(documentIdList, repEndpoint, each.getRepositoryUniqueId(), "");
			IResponse ITI_43 = new Response_ITI_43();
			ITI_43.parser(rds.getContext());
			List<RetrieveResult> RetrieveResultList = ((Response_ITI_43) ITI_43)
					.getRetrieveResultList();
			if (RetrieveResultList != null) {
				Iterator<RetrieveResult> iterator = RetrieveResultList.iterator();
				while (iterator.hasNext()) {
					RetrieveResult rr = iterator.next();
					byte[] array = org.apache.commons.codec.binary.Base64
							.decodeBase64(rr.getDocument().getBytes());
					try {
						Filedownload.save(array, rr.getMimeType(), URLEncoder.encode(each.getName(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			System.gc();
			return;
		}
		if(each.getId().equals("-")){
			init();
			return;
		}
		
		
		parent.clear();
		Item p = each;
		while((p = p.getParent()) != null){
			parent.push(p);
		}
		find(each);
		
	}
	
	private void find(Item each){
		list.clear();
		Iterator<Item> iteratorP = parent.descendingIterator();
		while(iteratorP.hasNext()){
			Item item = iteratorP.next();
			list.add(item);
		}
		QueryGenerator query = new QueryGenerator();
		CompanyInfomation company = new CompanyInfomation();
		company.setRegistryEndpoint(registryEndpoint);
		query.setCompanyRegistry(company);
		query.setQueryType(new KeyValue("GetFolderAndContents", "urn:uuid:b909a503-523d-4517-8acf-8e5834dfc4c7"));
		query.setReturnType("LeafClass");
		GetFolderAndContents getFoldersAndContents = new GetFolderAndContents();
		getFoldersAndContents.setId(each.getId());
		query.setParameter(getFoldersAndContents);
		query.build();
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		rsq.QueryGenerator(query.getQuery());

		MessageContext context = rsq.getContext();
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		if (response != null) {
			MetadataParser mp = new MetadataParser(response);
			Map<String, KeyValuesImpl> map = mp.getMapArray();
			Set<Entry> set = mp.getSet();
			if (map != null) {
				Iterator<Entry> iterator = set.iterator();
				while(iterator.hasNext()){
					Entry next = iterator.next();
					logger.info(next.getId());
					logger.info(next.getType());
					KeyValuesImpl keyValueList = map.get(next.getId());
					if (keyValueList != null) {
						List<KeyValue> all = keyValueList.findAll();
						Iterator<KeyValue> iterator02 = all.iterator();
						if(next.getType().equals("DOC_ENTRY_OBJECT")){
							logger.info("檔案");
							String title = "", description = "", creationTime = "", repositoryUniqueId = "", uniqueId = "";
							while (iterator02.hasNext()) {
								KeyValue kv = iterator02.next();
								String key = kv.getKey();
								if (key.equalsIgnoreCase("Name")) {
									title = kv.getValue();
								}
								if (key.equalsIgnoreCase("Description")) {
									description = kv.getValue();
									
								}
								if (key.equalsIgnoreCase("creationTime")) {
									creationTime = kv.getValue();
								}
								if (key.equalsIgnoreCase("repositoryUniqueId")) {
									repositoryUniqueId = kv.getValue();
								}
								if (key.equalsIgnoreCase("repositoryUniqueId")) {
									repositoryUniqueId = kv.getValue();
								}
								if (key.equalsIgnoreCase("DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME")) {
									uniqueId = kv.getValue();
								}
							}
							Item item = new Item(next.getId(), creationTime, title, description, Item.DOCUMENT, each);
							item.setRepositoryUniqueId(repositoryUniqueId);
							item.setUniqueId(uniqueId);
							list.add(item);
						}
						if(next.getType().equals("FOLDER_OBJECT")){
							while (iterator02.hasNext()) {
								KeyValue kv = iterator02.next();
								String key = kv.getKey();
								if (key.equalsIgnoreCase("Name")) {
									String title = kv.getValue();
								}
								if (key.equalsIgnoreCase("Description")) {
									String description = kv.getValue();
								}
								if (key.equalsIgnoreCase("lastUpdateTime")) {
									String lastUpdateTime = kv.getValue();
								}
							}
						}
					}
				}
			}
		}
	}
	

	private Set<String> listFolders() {
		QueryGenerator query = new QueryGenerator();
		CompanyInfomation company = new CompanyInfomation();
		company.setRegistryEndpoint(registryEndpoint);
		query.setCompanyRegistry(company);
		query.setQueryType(new KeyValue("FindFolders", "urn:uuid:958f3006-baad-4929-a4de-ff1114824431"));
		query.setReturnType("ObjectRef");
		FindFolders findFolders = new FindFolders();
		findFolders.setPatientId(patientId);
		findFolders.setStatus("urn:oasis:names:tc:ebxml-regrep:StatusType:Approved");
		// SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
		// Date dateFrom = date_format.parse("20131010");
		// Date dateTo = date_format.parse("20131025");
		// findFolders.setLastUpdateTimeFrom(dateFrom);
		// findFolders.setLastUpdateTimeTo(dateTo);
		query.setParameter(findFolders);
		query.build();
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		rsq.QueryGenerator(query.getQuery());
		IResponse ITI_18 = new Response_ITI_18();
		ITI_18.clean();
		ITI_18.parser(rsq.getContext());
		Set<String> set = ((Response_ITI_18) ITI_18).getList();
		return set;
	}

}
