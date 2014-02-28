/**
 * 
 */
package edu.tcu.gaduo.zk.view_model.xds_b;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Filedownload;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.IResponse;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.Response_ITI_43;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.RetrieveResult;
import edu.tcu.gaduo.webservice.LeafClass.MetadataParser;
import edu.tcu.gaduo.zk.model.CompanyInfomation;
import edu.tcu.gaduo.zk.model.QueryGenerator;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValue;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValuesImpl;
import edu.tcu.gaduo.zk.view_model.CompanyInfoVM;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetDocuments;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetFolders;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetSubmissionsetAndContents;

/**
 * @author Gaduo
 */
public class DocumentConsumer_LeafClassVM {
	public static Logger logger = Logger.getLogger(DocumentConsumer_LeafClassVM.class);

	private String uuid;
	private QueryGenerator query = null;
	private List<KeyValue> list = null;
	private CompanyInfomation companyRegistry;
	private String filename;
	private String repositoryUniqueId;

	@Init
	public void init(@ExecutionArgParam("query") QueryGenerator query,
			@ExecutionArgParam("uuid") String uuid,
			@ExecutionArgParam("registry") CompanyInfomation registry,
			@ExecutionArgParam("response") OMElement response) {
		setQuery(query); // 配置 QueryGenerator 記憶體，取得前一個查詢類型是什麼
							// FindDocument、FindFolder、FindSubmissionSet
		setUuid(uuid);
		setCompanyRegistry(registry);
		if (query != null) {
			submit(response);
		}
		System.gc();
	}

	public void submit(OMElement response) {
		query.setCompanyRegistry(companyRegistry);
		query.setReturnType("LeafClass");

		boolean resubmit = reSubmit();
		boolean isBuild = query.build(); // 新的 Query
		logger.warn("isBuild is " + isBuild);
		logger.warn("resubmit is " + resubmit);
		if (isBuild && resubmit) { // 再做一次 Query
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			rsq.QueryGenerator(query.getQuery());
			// 解析回應的訊息
			MessageContext context = rsq.getContext();
			SOAPEnvelope envelope = (context != null) ? context.getEnvelope()
					: null;
			SOAPBody body = (envelope != null) ? envelope.getBody() : null;
			OMElement response02 = (body != null) ? body.getFirstElement()
					: null;
			if (response02 != null)
				responseParser(response02);
			return;
		}
		if (query.getQueryType().equals(
				new KeyValue("GetDocuments",
						"urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetDocumentsAndAssociations",
						"urn:uuid:bab9529a-4a10-40b3-a01f-f68a615d247a"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetFolders",
						"urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetFolderAndContents",
						"urn:uuid:b909a503-523d-4517-8acf-8e5834dfc4c7"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetFoldersForDocument",
						"urn:uuid:10cae35a-c7f9-4cf5-b61e-fc3278ffb578"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetFoldersForDocument",
						"urn:uuid:10cae35a-c7f9-4cf5-b61e-fc3278ffb578"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetSubmissionSets",
						"urn:uuid:51224314-5390-4169-9b91-b1980040715a"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetSubmissionsetAndContents",
						"urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetAssociations",
						"urn:uuid:a7ae438b-4bc2-4642-93e9-be891f7bb155"))) {
			// first query's response
			responseParser(response);
		}
		if (query.getQueryType().equals(
				new KeyValue("GetRelatedDocuments",
						"urn:uuid:d90e5407-b356-4d91-a89f-873917b4b0e6"))) {
			// first query's response
			responseParser(response);
		}
		System.gc();
	}

	public boolean reSubmit() {
		boolean resubmit = false;
		if (query.getQueryType() != null) {
			if (query.getQueryType().equals(
					new KeyValue("FindDocuments",
							"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"))) {
				query.setQueryType(new KeyValue("Get Documents",
						"urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4"));
				GetDocuments gd = new GetDocuments();
				gd.setType("EntryUUID");
				gd.setId(uuid);
				query.setParameter(gd);
				logger.trace("QueryType is GetDocuments");
				resubmit = true;
			} else if (query.getQueryType().equals(
					new KeyValue("FindFolders",
							"urn:uuid:958f3006-baad-4929-a4de-ff1114824431"))) {
				query.setQueryType(new KeyValue("Get Folders",
						"urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4"));
				GetFolders gf = new GetFolders();
				gf.setType("EntryUUID");
				gf.setId(uuid);
				query.setParameter(gf);
				logger.trace("QueryType is GetFolders");
				resubmit = true;
			} else if (query.getQueryType().equals(
					new KeyValue("FindSubmissionSets",
							"urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9"))) {
				query.setQueryType(new KeyValue(
						"Get Submissionset And Contents",
						"urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83"));
				GetSubmissionsetAndContents gs = new GetSubmissionsetAndContents();
				gs.setId(uuid);
				query.setParameter(gs);
				logger.trace("QueryType is Get Submissionset And Contents");
				resubmit = true;
			}
		} else {
			logger.error("QueryType is NULL");
		}
		return resubmit;
	}

	public void responseParser(OMElement response) {
//		try {
			// logger.info("responseParser : " + response);
			MetadataParser mp = new MetadataParser(response);
			Map<String, KeyValuesImpl> map = mp.getMapArray();
			if (map != null) {
				KeyValuesImpl keyValueList = map.get(uuid);
				if (keyValueList != null) {
					List<KeyValue> all = keyValueList.findAll();
					setList(all);
					Iterator<KeyValue> iterator = all.iterator();
					while (iterator.hasNext()) {
						KeyValue kv = iterator.next();
						String key = kv.getKey();
						// logger.info(key);
						if (key.equalsIgnoreCase("Name")) {
							filename = kv.getValue();
						}
						if (key.equalsIgnoreCase("repositoryUniqueId")) {
							setRepositoryUniqueId(kv.getValue());
						}
					}
				}
			}
//		} catch (NullPointerException e) {
//			logger.error(e.toString());
//			return;
//		}
	}

	// -------------------
	@Command
	public void download() {
		String uniqueId = "", repositoryUniqueId = "", filename = "";
		logger.info(list);
		if (list == null)
			return ;
		Iterator<KeyValue> iterator01 = list.iterator();
		while (iterator01.hasNext()) {
			KeyValue next = iterator01.next();
			if (next.getKey().equals("DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME")) {
				uniqueId = next.getValue();
			}
			if (next.getKey().equals("repositoryUniqueId")) {
				repositoryUniqueId = next.getValue();
			}
			if (next.getKey().equals("Name")) {
				filename = next.getValue();
			}
		}
		Set<String> documentIdList = new TreeSet<String>();
		documentIdList.add(uniqueId);
		String repEndpoint = repositoryMapping();
		new Certificate().setCertificate();
		RetrieveDocumentSet rds = new RetrieveDocumentSet();
		rds.RetrieveGenerator(documentIdList, repEndpoint, repositoryUniqueId, "");
		IResponse ITI_43 = new Response_ITI_43();
		ITI_43.parser(rds.getContext());

		List<RetrieveResult> RetrieveResultList = ((Response_ITI_43) ITI_43)
				.getRetrieveResultList();
		if (RetrieveResultList != null) {
			Iterator<RetrieveResult> iterator = RetrieveResultList.iterator();
			while (iterator.hasNext()) {
				RetrieveResult rr = iterator.next();
				try {
					byte[] array = Base64.decodeBase64(rr.getDocument().getBytes("UTF-8"));
					Filedownload.save(array, rr.getMimeType(), URLEncoder.encode(filename, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		System.gc();
	}
	/*
	@Command
    public void CDAViewer() {
        String uniqueId = "", repositoryUniqueId = "", filename = "";
        logger.info(list);
        if (list == null)
            return ;
        Iterator<KeyValue> iterator01 = list.iterator();
        while (iterator01.hasNext()) {
            KeyValue next = iterator01.next();
            if (next.getKey().equals("DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME")) {
                uniqueId = next.getValue();
            }
            if (next.getKey().equals("repositoryUniqueId")) {
                repositoryUniqueId = next.getValue();
            }
            if (next.getKey().equals("Name")) {
                filename = next.getValue();
            }
        }
        Set<String> documentIdList = new TreeSet<String>();
        documentIdList.add(uniqueId);
        String repEndpoint = repositoryMapping();
        new Certificate().setCertificate();
        RetrieveDocumentSet rds = new RetrieveDocumentSet();
        rds.RetrieveGenerator(documentIdList, repEndpoint, repositoryUniqueId, "");
        IResponse ITI_43 = new Response_ITI_43();
        ITI_43.parser(rds.getContext());

        List<RetrieveResult> RetrieveResultList = ((Response_ITI_43) ITI_43)
                .getRetrieveResultList();
        if (RetrieveResultList != null) {
            Iterator<RetrieveResult> iterator = RetrieveResultList.iterator();
            while (iterator.hasNext()) {
                RetrieveResult rr = iterator.next();
                try {
                    byte[] array = Base64.decodeBase64(rr.getDocument().getBytes("UTF-8"));
                    Document doc = XmlUtils.readDocumentFromByte(array);
                    
                    //SheetType selectedSheetType = SheetUtils.getSheetType("CDP_VIEW");
                    SheetType selectedSheetType = new SheetType();
                    selectedSheetType.setIsSystem("Y");
                    selectedSheetType.setParserServiceName("CDAR2ParserService");
                    SheetRepository sheetRepository = new SheetRepository();
                    sheetRepository.setLibOid("LIB_CDA_EXT_001");
                    sheetRepository.setSheetClassOid("SHEET_CLASS_CDA_EXT_001");
                    sheetRepository.setSheetReposDesc("外部匯入儲存庫");
                    sheetRepository.setSheetReposOid("SHEET_REPOS_OID_002");
                    selectedSheetType.setSheetRepos(sheetRepository);
                    selectedSheetType.setSheetReposOid("SHEET_REPOS_OID_002");
                    selectedSheetType.setSheetTypeDesc("交換封裝格式閱覽");
                    selectedSheetType.setSheetTypeId("CDP_VIEW");
                    selectedSheetType.setSheetTypeOid("SHEET_TYPE_OID_902");
                    
                    //ImportExternalException importFileInfo = new ImportExternalException(filename);
                    //importFileInfo.setImportFileName(filename);
                    
                    CDPViweHandler(doc, selectedSheetType);
                    Clients.evalJavaScript("showSheet();");
                    //Filedownload.save(array, rr.getMimeType(), URLEncoder.encode(filename, "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.gc();
    }*/

	private String repositoryMapping() {
		CompanyInfoVM c = new CompanyInfoVM();
		c.init();
		// /要將整個 CompanyInfo List 傳進來，在依照 uniqueId 查詢。
		List<CompanyInfomation> companys = c.getCompanyInfomations();
		Iterator<CompanyInfomation> iterator = companys.iterator();
		while (iterator.hasNext()) {
			CompanyInfomation company = iterator.next();
			String repUniqueId = company.getRepositoryUniqueId();
			if (repUniqueId.equals(repositoryUniqueId)) {
				return company.getRepositoryEndpoint();
			}
		}
		logger.info("Repository UniqueId was not maping any Repository Endpoint");
		return "";
	}
	/*
	//private void CDPViweHandler(Document doc, SheetType selectedSheetType, ImportExternalException importFileInfo) {
	private void CDPViweHandler(Document doc, SheetType selectedSheetType) {
        try {
            Entity operator = (Entity) SessionUtils.getOperator();
            CustomResult<List<SheetMetaData>> importResult = getCDAController().readCDAPayload(null, doc, selectedSheetType, operator);
            if (importResult.isSuccess()) {
                
                List<SheetMetaData> result = importResult.getResult();
                SheetMetaData metaData  = result.get(0);
                HttpSession httpSession = (HttpSession) Sessions.getCurrent().getNativeSession();
                httpSession.setAttribute("importSheetXml", metaData);
                httpSession.setAttribute("importDoc", doc);
                
                //importFileInfo.setImportFileName(importFileInfo.getImportFileName());
                //importFileInfo.setPatientId(metaData.getIdNo());
                //importFileInfo.setPatientName(metaData.getPatientName());
                //importFileInfo.setSheetTypeName(metaData.getSheetName());
                //importFileInfo.setImportInfo("File Success");
            } //else
                //importFileInfo.setImportInfo("File Fail");
        } catch (Exception e) {
            //importFileInfo.setImportInfo(e.getLocalizedMessage());
        } finally {
            //importExceptions.add(importFileInfo);
        }
    }

    private static CDAController getCDAController() throws Exception {
        return EmrControllerFactory.eINSTANCE.getCDAController();
    }*/

	// -------------------

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public QueryGenerator getQuery() {
		return this.query;
	}

	public void setQuery(QueryGenerator q) {
		this.query = q.clone();
	}

	public List<KeyValue> getList() {
		return list;
	}

	public void setList(List<KeyValue> list) {
		this.list = list;
	}

	public CompanyInfomation getCompanyRegistry() {
		return this.companyRegistry;
	}

	public void setCompanyRegistry(CompanyInfomation companyRegistry) {
		this.companyRegistry = companyRegistry;
	}

	public String getRepositoryUniqueId() {
		return repositoryUniqueId;
	}

	public void setRepositoryUniqueId(String repositoryUniqueId) {
		this.repositoryUniqueId = repositoryUniqueId;
	}

}
