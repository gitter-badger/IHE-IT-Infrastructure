/**
 * 
 */
package edu.tcu.gaduo.zk.view_model.xds_b;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axiom.om.OMElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.iod.module.sr.HierachicalSOPInstanceReference;
import org.dcm4che2.iod.module.sr.SOPInstanceReferenceAndMAC;
import org.dcm4che2.iod.module.sr.SeriesAndInstanceReference;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.TreeNode;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.IResponse;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.ResponseAttachmentEntry;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.ResponseCode;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.Response_ITI_18_Fast;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.Response_ITI_43;
import edu.tcu.gaduo.ihe.utility.webservice.nonblock.RetrieveResult;
import edu.tcu.gaduo.view.util.FileExporter;
import edu.tcu.gaduo.zk.model.CompanyInfomation;
import edu.tcu.gaduo.zk.model.QueryGenerator;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValue;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValues;
import edu.tcu.gaduo.zk.model.KeyValue.KeyValuesImpl;
import edu.tcu.gaduo.zk.model.attachment.AttachmentEntryCollection;
import edu.tcu.gaduo.zk.model.attachment.ResponseAttachmentEntryTreeNode;
import edu.tcu.gaduo.zk.view_model.CompanyInfoVM;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.FindDocuments;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.FindFolders;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.FindSubmissionSets;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetAll;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetAssociations;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetDocuments;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetDocumentsAndAssociations;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetFolderAndContents;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetFolders;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetFoldersForDocument;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetRelatedDocuments;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetSubmissionSets;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.GetSubmissionsetAndContents;
import edu.tcu.gaduo.zk.view_model.xds_b.iti_18.IParameter;

/**
 * @author Gaduo
 */
public class DocumentConsumerPatientViewVM {
	public static Logger logger = Logger.getLogger(DocumentConsumerPatientViewVM.class);
	public static int total = 0;
	public static int currentNO;

	private QueryGenerator query;
	private String endpoint;
	private KeyValues queryTypeList;
	private String view;
	private IResponse ITI_18;
	private List<CompanyInfomation> companyInfomations;
	
	private List<ResponseAttachmentEntry> documentList;
    private ResponseAttachmentEntryTreeNode<ResponseAttachmentEntry> root;
    private DefaultTreeModel<ResponseAttachmentEntry> folderList;

	private double timestamp;
	
	@Init
	public void init() {
		getUUID();
		CompanyInfoVM c = new CompanyInfoVM();
        c.init();
        this.setCompanyInfomations(c.getCompanyInfomations());
        
		getData();
		
		System.gc();

		// ---Debug
		// ITI_18 = new Response_ITI_18();
		// ((Response_ITI_18)ITI_18).setList(new TreeSet<String>());
		// for(int i = 0; i < 15; i++){
		// ((Response_ITI_18)ITI_18).addItemToList(i +"_abcdefg");
		// }
		// ---Debug
		if (Executions.getCurrent().getParameter("patientId") != null && Executions.getCurrent().getParameter("patientId").length() > 0)
		    submit();
	}
	
	@Command
	@NotifyChange({ "ITI_18", "view", "total", "timestamp", "folderList" })
	public void submit() {
	    initDocumentTree();
		timestamp = System.currentTimeMillis();
		
		DocumentConsumerPatientViewVM.total = 0;
		if (query.getCompanyRegistry() == null) {
			logger.warn("Choice Registry .");
			return;
		}
		boolean isBuild = query.build();
		logger.info("RSQ is build : " + isBuild);
		if (isBuild) {
			// setView("");
			new Certificate().setCertificate();
			ITI_18 = new Response_ITI_18_Fast();
			ITI_18.clean();
			RegistryStoredQuery rsq = new RegistryStoredQuery();
			rsq.QueryGenerator(query.getQuery());
			ITI_18.parser(rsq.getContext());
			documentList = ((Response_ITI_18_Fast) ITI_18).getAttachmentEntityList();
			if (documentList != null && !documentList.isEmpty()) {
			    
			    //Map<String, Map<String, List<ResponseAttachmentEntry>>> groupResult = groupingDocument(documentList);
			    Map<ResponseCode, Map<String, List<ResponseAttachmentEntry>>> groupResult = groupingDocumentByCodeByTime(documentList);
			    			    
			    for (ResponseCode classCode : groupResult.keySet()) {
                    addFolderItem(new ResponseAttachmentEntry(classCode.getDisplay(), classCode.getDisplay()));
                    
                    TreeNode<ResponseAttachmentEntry> classCodeNode = root.getChildAt(root.getChildCount() - 1);
                    
                    List<String> orderedTimeList = new ArrayList<String>();
                    for (String time : groupResult.get(classCode).keySet())
                        orderedTimeList.add(time);
                    Collections.sort(orderedTimeList);
                    Collections.reverse(orderedTimeList);
                    
                    for (String time : orderedTimeList) {
                        StringBuffer sb = new StringBuffer(time);
                        sb.insert(6, "/");
                        sb.insert(4, "/");
                        sb.append(" (" + groupResult.get(classCode).get(time).size() + ")");
                        classCodeNode.add(new ResponseAttachmentEntryTreeNode<ResponseAttachmentEntry>(new ResponseAttachmentEntry(sb.toString(), time), new AttachmentEntryCollection<ResponseAttachmentEntry>()));
                    }
                    
                    for (TreeNode<ResponseAttachmentEntry> timeNode : classCodeNode.getChildren()) {
                        for (ResponseAttachmentEntry entry : groupResult.get(classCode).get(timeNode.getData().getDescription()))
                        timeNode.add(new ResponseAttachmentEntryTreeNode<ResponseAttachmentEntry>(entry, null));
                    }
                }
                
		        openFolderList();
			}
			DocumentConsumerPatientViewVM.total = ((Response_ITI_18_Fast) ITI_18).getList() .size();
			logger.info(DocumentConsumerPatientViewVM.total + "");
		}
		IParameter p;
		if (query != null) {
			p = query.getParameter();
			if (p != null) {
				List<OMElement> plist = p.getParameters();
				if (plist != null) {
					plist.clear();
				}
			}
		}
		System.gc();
		timestamp = System.currentTimeMillis() - timestamp;
		timestamp /= 1000;
		
	}

	@Command
	@NotifyChange({ "view", "query" })
	public void SelectQueryType() { // 選擇 Query 型態
		String key = query.getQueryType().getKey();
		logger.info(key);
		if (key.equals("FindDocuments")) {
			query.setParameter(new FindDocuments());
		}
		if (key.equals("FindFolders")) {
			query.setParameter(new FindFolders());
		}
		if (key.equals("FindSubmissionSets")) {
			query.setParameter(new FindSubmissionSets());
		}
		if (key.equals("GetAll")) {
			query.setParameter(new GetAll());
		}
		if (key.equals("GetDocuments")) {
			query.setParameter(new GetDocuments());
		}
		if (key.equals("GetDocumentsAndAssociations")) {
			query.setParameter(new GetDocumentsAndAssociations());
		}
		if (key.equals("GetFolders")) {
			query.setParameter(new GetFolders());
		}
		if (key.equals("GetFolderAndContents")) {
			query.setParameter(new GetFolderAndContents());
		}
		if (key.equals("GetFoldersForDocument")) {
			query.setParameter(new GetFoldersForDocument());
		}
		if (key.equals("GetSubmissionSets")) {
			query.setParameter(new GetSubmissionSets());
		}
		if (key.equals("GetSubmissionsetAndContents")) {
			query.setParameter(new GetSubmissionsetAndContents());
		}
		if (key.equals("GetAssociations")) {
			query.setParameter(new GetAssociations());
		}
		if (key.equals("GetRelatedDocuments")) {
			query.setParameter(new GetRelatedDocuments());
		}
		setView("ITI-18/" + key + ".zul");
	}
	
	@Command
    public void view(@BindingParam("each") ResponseAttachmentEntry entry) {
		try {
			byte[] array = getSingleDocumentContent(entry);
			ByteArrayInputStream xmlFile = new ByteArrayInputStream(array);
			
			ServletContext context = Sessions.getCurrent().getWebApp().getServletContext();
			String rootPath = context.getRealPath("/");
			String risXslFilename = rootPath + "ris.xsl";
			
			FileInputStream fis = new FileInputStream(risXslFilename);
			ByteArrayInputStream xslFile = new ByteArrayInputStream(IOUtils.toByteArray(fis));
			fis.close();
			
			String html = FileExporter.createHtmlByXml(xslFile, xmlFile);
			xmlFile.close();
			xslFile.close();
			
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String nativeURL = request.getRequestURL().toString();
			int start = nativeURL.indexOf("://") + 3;
			String subURL = nativeURL.substring(start);
			String hostPort = subURL.substring(0, subURL.indexOf("/"));

			String xmlViewer = "/xmlViewer.html";
			HttpSession seesion = request.getSession();
    		seesion.setAttribute("html",  html);

            StringBuilder scriptURL = new StringBuilder();
    		scriptURL.append("var newWinWidth = screen.width;");
    		scriptURL.append("newWinHeight = newWinHeight - 20;");
    		scriptURL.append("var newWinHeight = screen.height;");
    		scriptURL.append("newWinHeight = newWinHeight - 70;");
    		scriptURL.append("window.open('http://");
    		scriptURL.append(hostPort);
    		scriptURL.append("/ihe-web/");
    		scriptURL.append(xmlViewer);
    		scriptURL.append("',");
    		scriptURL.append("'', '");
    		String params = "location=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,toolbar=no,left=0,top=0";
    		scriptURL.append(params);
    		scriptURL.append(",width=' + ");
    		scriptURL.append("newWinWidth + ");
    		scriptURL.append("',height=' + ");
    		scriptURL.append("newWinHeight");
    		scriptURL.append(");");
    		System.out.println("scriptURL : " + scriptURL);
            Clients.evalJavaScript(scriptURL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        // TODO 1: view xml using XSLT
	    // TODO 2: view pdf
    }

	@Command
    public void viewImage(@BindingParam("each") ResponseAttachmentEntry entry) {
	    // TODO: view wado, reference "ihe-web\src\main\webapp\XDS.b-I\ImagingDocumentConsumer.zul"
		byte[] array = getSingleDocumentContent(entry);

		try {
	        DicomObject dcmObj = null;
	        DicomInputStream din = null;
	        try {
	            din = new DicomInputStream(new ByteArrayInputStream(array));
	            dcmObj = din.readDicomObject();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (din != null) {
	                try {
	                    din.close();
	                } catch (IOException ignore) {}
	            } else {
	                Clients.alert("kos is null");
	                return;
	            }
	        }

	        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
    		String nativeURL = request.getRequestURL().toString();
    		int start = nativeURL.indexOf("://") + 3;
    		String subURL = nativeURL.substring(start);
    		String hostPort = subURL.substring(0, subURL.indexOf("/"));

    		List<String> imageUrlList = new ArrayList<String>();
	        List<Map<String, String>> list = parseDicom(dcmObj);
	        for (Map<String, String> map : list) {
	        	String prefixUrl = "http://192.168.7.1:8000/mio/WADO.html?requestType=WADO&contentType=image/jpeg";
	        	String studyUID = map.get("studyUID");
	        	String seriesUID = map.get("seriesUID");
	        	String instanceUID = map.get("instanceUID");

	        	String wadoUrl = prefixUrl + "&studyUID=" + studyUID + "&seriesUID=" + seriesUID + 
									"&objectUID=" + instanceUID;
	        	imageUrlList.add(wadoUrl);
	        }
	        
	        String dicomViewer = "/dicomViewer.html";
			HttpSession seesion = request.getSession();
    		seesion.setAttribute("imageUrlList",  imageUrlList);

            StringBuilder scriptURL = new StringBuilder();
    		scriptURL.append("var newWinWidth = screen.width;");
    		scriptURL.append("newWinHeight = newWinHeight - 20;");
    		scriptURL.append("var newWinHeight = screen.height;");
    		scriptURL.append("newWinHeight = newWinHeight - 70;");
    		scriptURL.append("window.open('http://");
    		scriptURL.append(hostPort);
    		scriptURL.append("/ihe-web/");
    		scriptURL.append(dicomViewer);
    		scriptURL.append("',");
    		scriptURL.append("'', '");
    		String params = "location=no,menubar=no,resizable=no,scrollbars=yes,status=no,titlebar=no,toolbar=no,left=0,top=0";
    		scriptURL.append(params);
    		scriptURL.append(",width=' + ");
    		scriptURL.append("newWinWidth + ");
    		scriptURL.append("',height=' + ");
    		scriptURL.append("newWinHeight");
    		scriptURL.append(");");
    		System.out.println("scriptURL : " + scriptURL);
            Clients.evalJavaScript(scriptURL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	private List<Map<String, String>> parseDicom(DicomObject dcmObj) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        try {
            DicomElement sq = dcmObj.get(0x0040a375);
            if (sq == null)
                return list;
            HierachicalSOPInstanceReference[] hierachicalSOPInstanceReference = HierachicalSOPInstanceReference
                    .toSOPInstanceReferenceMacros(sq);
            for (HierachicalSOPInstanceReference study : hierachicalSOPInstanceReference) {
                String studyUID = study.getStudyInstanceUID();
                SeriesAndInstanceReference[] seriesAndInstanceReference = study.getReferencedSeries();
                for (SeriesAndInstanceReference series : seriesAndInstanceReference) {
                    String seriesUID = series.getSeriesInstanceUID();
                    SOPInstanceReferenceAndMAC[] instanceReferenceAndMAC = series.getReferencedInstances();
                    for (SOPInstanceReferenceAndMAC instance : instanceReferenceAndMAC) {
                    	Map<String, String> map = new HashMap<String, String>();
                        String instanceUID = instance.getReferencedSOPInstanceUID();
                        map.put("studyUID", studyUID);
                        map.put("seriesUID", seriesUID);
                        map.put("instanceUID", instanceUID);
                        list.add(map);
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }

	@Command
    public void download(@BindingParam("each") ResponseAttachmentEntry entry) {
	    byte[] array = getSingleDocumentContent(entry);
        try {
            Filedownload.save(array, entry.getMimeType(), URLEncoder.encode(entry.getTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.gc();
    }
	
	public boolean isDICOMFile(ResponseAttachmentEntry entry) {
	    if (entry != null && entry.getTitle() != null) {
    	    String[] s = entry.getTitle().split("\\.");
    	    if (s != null && s.length > 0 && s[s.length -1].equalsIgnoreCase("dcm"))
    	        return true;
	    }
	    return false;
	}

	public IResponse getITI_18() {
		return this.ITI_18;
	}

	public void setITI_18(IResponse ITI_18) {
		this.ITI_18 = ITI_18;
	}

	public List<KeyValue> getQueryTypeList() {
		return this.queryTypeList.findAll();
	}

	public void setQueryTypeList(KeyValues queryTypeList) {
		this.queryTypeList = queryTypeList;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public QueryGenerator getQuery() {
		return query;
	}

	public void setQuery(QueryGenerator query) {
		this.query = query;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public List<CompanyInfomation> getCompanyInfomations() {
		return companyInfomations;
	}

	public void setCompanyInfomations(List<CompanyInfomation> companyInfomations) {
		this.companyInfomations = companyInfomations;
	}

	public int getTotal() {
		return total;
	}

	public int getCurrentNO() {
		return currentNO;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	

    // -- Document
    public List<ResponseAttachmentEntry> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<ResponseAttachmentEntry> documentList) {
        this.documentList = documentList;
    }

    public void addDocumentItem(ResponseAttachmentEntry doc) {
        this.documentList.add(doc);
    }

    public void removeDocumentItem(int i) {
        this.documentList.remove(i);
    }

    // -- Document
    // -- Folder
    public List<TreeNode<ResponseAttachmentEntry>> getRoot() {
        return this.root.getChildren();
    }

    public void setRoot(ResponseAttachmentEntryTreeNode<ResponseAttachmentEntry> root) {
        this.root = root;
    }

    public DefaultTreeModel<ResponseAttachmentEntry> getFolderList() {
        return folderList;
    }

    public void setFolderList(DefaultTreeModel<ResponseAttachmentEntry> folderList) {
        this.folderList = folderList;
    }
    
    public void openFolderList() {
        for (TreeNode<ResponseAttachmentEntry> node :getRoot()) {
            folderList.addOpenObject(node);
            /*for (TreeNode<ResponseAttachmentEntry> timeNode : node.getChildren())
                folderList.addOpenObject(timeNode);*/
        }
    }

    public void addFolderItem(ResponseAttachmentEntry folder) {
        this.root.add(new ResponseAttachmentEntryTreeNode<ResponseAttachmentEntry>(folder, new AttachmentEntryCollection<ResponseAttachmentEntry>()));
    }

    public void removeFolderItem(int i) {
        this.root.remove(i);
    }
    
    /*private Map<String, Map<String, List<ResponseAttachmentEntry>>> groupingDocument(List<ResponseAttachmentEntry> documentList){
        List<String> classCode = new ArrayList<String>();
        Map<String, List<ResponseAttachmentEntry>> classGroup = new HashMap<String, List<ResponseAttachmentEntry>>();
        Map<String, Map<String, List<ResponseAttachmentEntry>>> result = new HashMap<String, Map<String,List<ResponseAttachmentEntry>>>();
        
        for (ResponseAttachmentEntry att: documentList) {
            if (!classCode.contains(att.getClassCode()))
                classCode.add(att.getClassCode().getCode());
        }
        for (String code : classCode) {
            classGroup.put(code, new ArrayList<ResponseAttachmentEntry>());
        }
        for (ResponseAttachmentEntry att: documentList) {
            classGroup.get(att.getClassCode().getCode()).add(att);
        }
        
        for (String code : classCode) {
            List<String> timeList = new ArrayList<String>();
            List<String> orderedTimeList = new ArrayList<String>();
            Map<String, List<ResponseAttachmentEntry>> timeGroup = new HashMap<String, List<ResponseAttachmentEntry>>();
            for (ResponseAttachmentEntry att: classGroup.get(code)) {
                if (!timeList.contains(att.getCreationTime().substring(0, 9)))
                    timeList.add(att.getCreationTime().substring(0, 9));
            }
            
            Collections.sort(timeList);
            for (String s : timeList)
                orderedTimeList.add(s);
            
            for (String time : orderedTimeList) {
                timeGroup.put(time, new ArrayList<ResponseAttachmentEntry>());
            }
            for (ResponseAttachmentEntry att: classGroup.get(code)) {
                timeGroup.get(att.getCreationTime().substring(0, 9)).add(att);
            }
            
            result.put(code, timeGroup);
        }
        
        return result;
    }*/
    
    @NotifyChange({"query"})
    private void getData() {
        query = new QueryGenerator();
        query.setQueryType(queryTypeList.get(0));
        query.setReturnType("LeafClass");
        SelectQueryType();
        query.setCompanyRegistry(companyInfomations.get(0));
        
        //setView("ITI-18/" + query.getQueryType().getKey() + ".zul");
        setView("ITI-18/FindDocuments.zul");
        
        initDocumentTree();
    }
    
    private void initDocumentTree() {
        setDocumentList(new ArrayList<ResponseAttachmentEntry>());
        ResponseAttachmentEntry rootEntry = new ResponseAttachmentEntry("root", "root");
        setRoot(new ResponseAttachmentEntryTreeNode<ResponseAttachmentEntry>(rootEntry, new AttachmentEntryCollection<ResponseAttachmentEntry>()));
        setFolderList(new DefaultTreeModel<ResponseAttachmentEntry>(root));
    }

    private void getUUID() {
        this.queryTypeList = new KeyValuesImpl();
        this.queryTypeList.add(new KeyValue("FindDocuments",
                "urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"));
        this.queryTypeList.add(new KeyValue("FindFolders",
                "urn:uuid:958f3006-baad-4929-a4de-ff1114824431"));
        this.queryTypeList.add(new KeyValue("FindSubmissionSets",
                "urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9"));
        this.queryTypeList.add(new KeyValue("GetAll",
                "urn:uuid:10b545ea-725c-446d-9b95-8aeb444eddf3"));
        this.queryTypeList.add(new KeyValue("GetDocuments",
                "urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4"));
        this.queryTypeList.add(new KeyValue("GetDocumentsAndAssociations",
                "urn:uuid:bab9529a-4a10-40b3-a01f-f68a615d247a"));
        this.queryTypeList.add(new KeyValue("GetFolders",
                "urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4"));
        this.queryTypeList.add(new KeyValue("GetFolderAndContents",
                "urn:uuid:b909a503-523d-4517-8acf-8e5834dfc4c7"));
        this.queryTypeList.add(new KeyValue("GetFoldersForDocument",
                "urn:uuid:10cae35a-c7f9-4cf5-b61e-fc3278ffb578"));
        this.queryTypeList.add(new KeyValue("GetSubmissionSets",
                "urn:uuid:51224314-5390-4169-9b91-b1980040715a"));
        this.queryTypeList.add(new KeyValue("GetSubmissionsetAndContents",
                "urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83"));
        this.queryTypeList.add(new KeyValue("GetAssociations",
                "urn:uuid:a7ae438b-4bc2-4642-93e9-be891f7bb155"));
        this.queryTypeList.add(new KeyValue("GetRelatedDocuments",
                "urn:uuid:d90e5407-b356-4d91-a89f-873917b4b0e6"));
    }
    
    private Map<ResponseCode, Map<String, List<ResponseAttachmentEntry>>> groupingDocumentByCodeByTime(List<ResponseAttachmentEntry> documentList){
        List<ResponseCode> classCode = new ArrayList<ResponseCode>();
        Map<ResponseCode, List<ResponseAttachmentEntry>> classGroup = new HashMap<ResponseCode, List<ResponseAttachmentEntry>>();
        Map<ResponseCode, Map<String, List<ResponseAttachmentEntry>>> result = new HashMap<ResponseCode, Map<String,List<ResponseAttachmentEntry>>>();
        
        for (ResponseAttachmentEntry att: documentList) {
            if (!classCode.contains(att.getClassCode()))
                classCode.add(att.getClassCode());
        }
        for (ResponseCode code : classCode) {
            classGroup.put(code, new ArrayList<ResponseAttachmentEntry>());
        }
        for (ResponseAttachmentEntry att: documentList) {
            classGroup.get(att.getClassCode()).add(att);
        }
        
        for (ResponseCode code : classCode) {
            List<String> timeList = new ArrayList<String>();
            
            Map<String, List<ResponseAttachmentEntry>> timeGroup = new HashMap<String, List<ResponseAttachmentEntry>>();
            for (ResponseAttachmentEntry att: classGroup.get(code)) {
                if (!timeList.contains(att.getCreationTime().substring(0, 8)))
                    timeList.add(att.getCreationTime().substring(0, 8));
            }
            
            /*Collections.sort(timeList, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });*/
            /*Collections.sort(timeList);
            for (String s : timeList)
                orderedTimeList.add(s);*/
            
            for (String time : timeList) {
                timeGroup.put(time, new ArrayList<ResponseAttachmentEntry>());
            }
            for (ResponseAttachmentEntry att: classGroup.get(code)) {
                timeGroup.get(att.getCreationTime().substring(0, 8)).add(att);
            }
            
            result.put(code, timeGroup);
        }
        
        return result;
    }
    
    private String repositoryMapping(String repositoryUniqueId) {
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
    
    private byte[] getSingleDocumentContent(ResponseAttachmentEntry entry) {
        Set<String> documentIdList = new TreeSet<String>();
        documentIdList.add(entry.getDocumentEntryUniqueId());
        String repEndpoint = repositoryMapping(entry.getRepositoryUniqueId());
        new Certificate().setCertificate();
        RetrieveDocumentSet rds = new RetrieveDocumentSet();
        rds.RetrieveGenerator(documentIdList, repEndpoint, entry.getRepositoryUniqueId(), "");
        IResponse ITI_43 = new Response_ITI_43();
        ITI_43.parser(rds.getContext());

        List<RetrieveResult> RetrieveResultList = ((Response_ITI_43) ITI_43).getRetrieveResultList();
        if (RetrieveResultList != null && RetrieveResultList.iterator().hasNext()) {
            Iterator<RetrieveResult> iterator = RetrieveResultList.iterator();
            while (iterator.hasNext()) {
                RetrieveResult rr = iterator.next();
                if (rr.getMimeType() != null && rr.getMimeType().trim().length() > 0)
                    entry.setMimeType(rr.getMimeType());
                if (rr.getDocument() != null && rr.getDocument().trim().length() > 0) {
                    entry.setContent(rr.getDocument());
                    try {
                        return Base64.decodeBase64(rr.getDocument().getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}