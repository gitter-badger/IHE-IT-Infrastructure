/**
 * 
 */
package com.gaduo.zk.view_model;

import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;


import com.gaduo.ihe.it_infrastructure.xds_transaction.service.RegistryStoredQuery;
import com.gaduo.ihe.security.Certificate;
import com.gaduo.ihe.utility.webservice.nonblock.IResponse;
import com.gaduo.ihe.utility.webservice.nonblock.NonBlockCallBack;
import com.gaduo.ihe.utility.webservice.nonblock.Response_ITI_18;
import com.gaduo.zk.model.CompanyInfomation;
import com.gaduo.zk.model.QueryGenerator;
import com.gaduo.zk.model.KeyValue.KeyValue;
import com.gaduo.zk.model.KeyValue.KeyValues;
import com.gaduo.zk.model.KeyValue.KeyValuesImpl;
import com.gaduo.zk.model.iti_18.FindDocuments;
import com.gaduo.zk.model.iti_18.FindFolders;
import com.gaduo.zk.model.iti_18.FindSubmissionSets;
import com.gaduo.zk.model.iti_18.GetAll;
import com.gaduo.zk.model.iti_18.GetAssociations;
import com.gaduo.zk.model.iti_18.GetDocuments;
import com.gaduo.zk.model.iti_18.GetDocumentsAndAssociations;
import com.gaduo.zk.model.iti_18.GetFolderAndContents;
import com.gaduo.zk.model.iti_18.GetFolders;
import com.gaduo.zk.model.iti_18.GetFoldersForDocument;
import com.gaduo.zk.model.iti_18.GetRelatedDocuments;
import com.gaduo.zk.model.iti_18.GetSubmissionSets;
import com.gaduo.zk.model.iti_18.GetSubmissionsetAndContents;

/**
 * @author Gaduo
 */
public class ITI_18 {
    public static Logger logger = Logger.getLogger(ITI_18.class);
    private QueryGenerator query;
    private String endpoint;
    private List<String> returnTypeList;
    private KeyValues queryTypeList;
    private String view;
    private IResponse ITI_18;
    private List<CompanyInfomation> companyInfomations;

    private CompanyInfomation companyRepository;
    @Init
    public void init() {
        getUUID();
        getData();
        CompanyInfo c = new CompanyInfo();
        c.init();
        this.setCompanyInfomations(c.getCompanyInfomations());
        System.gc();   
    }

    private void getData() {
        query = new QueryGenerator();
        query.setQueryType(queryTypeList.get(0));
        query.setReturnType("ObjectRef");
        query.setParameter(new FindDocuments());
        setView("ITI-18/" + query.getQueryType().getKey() + ".zul");
    }

    private void getUUID() {
        this.queryTypeList = new KeyValuesImpl();
        this.queryTypeList.add(new KeyValue("FindDocuments", "urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"));
        this.queryTypeList.add(new KeyValue("FindFolders", "urn:uuid:958f3006-baad-4929-a4de-ff1114824431"));
        this.queryTypeList.add(new KeyValue("FindSubmissionSets", "urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9"));
        this.queryTypeList.add(new KeyValue("GetAll", "urn:uuid:10b545ea-725c-446d-9b95-8aeb444eddf3"));
        this.queryTypeList.add(new KeyValue("GetDocuments", "urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4"));
        this.queryTypeList.add(new KeyValue("GetDocumentsAndAssociations", "urn:uuid:bab9529a-4a10-40b3-a01f-f68a615d247a"));
        this.queryTypeList.add(new KeyValue("GetFolders", "urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4"));
        this.queryTypeList.add(new KeyValue("GetFolderAndContents", "urn:uuid:b909a503-523d-4517-8acf-8e5834dfc4c7"));
        this.queryTypeList.add(new KeyValue("GetFoldersForDocument", "urn:uuid:10cae35a-c7f9-4cf5-b61e-fc3278ffb578"));
        this.queryTypeList.add(new KeyValue("GetSubmissionSets", "urn:uuid:51224314-5390-4169-9b91-b1980040715a"));
        this.queryTypeList.add(new KeyValue("GetSubmissionsetAndContents", "urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83"));
        this.queryTypeList.add(new KeyValue("GetAssociations", "urn:uuid:a7ae438b-4bc2-4642-93e9-be891f7bb155"));
        this.queryTypeList.add(new KeyValue("GetRelatedDocuments", "urn:uuid:d90e5407-b356-4d91-a89f-873917b4b0e6"));
    }

    @Command
    @NotifyChange({"ITI_18", "view"})
    public void submit() {
        if(query.getCompanyRegistry() == null) {
            logger.warn("Choice Registry .");
        }
        if(this.getCompanyRepository() == null) {
            logger.warn("Choise Repository .");
            return;
        }        
        boolean isBuild = query.build();
        if (isBuild) {
            //setView("");
            new Certificate().setCertificate();
            RegistryStoredQuery rsq = new RegistryStoredQuery();
            OMElement response = rsq.QueryGenerator(query.getQuery());
//            NonBlockCallBack callback = rsq.getCallback();
            ITI_18 = new Response_ITI_18();
            ITI_18.parser(rsq.getContext());
        }
        query.getParameter().getParameters().clear();
        System.gc();
    }

    @Command
    public void checked(@BindingParam("checked") boolean checked) {

    }

    @Command
    @NotifyChange({"view", "query"})
    public void SelectQueryType() { // �豢��亥岷憿�
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

    public List<String> getReturnTypeList() {
        return returnTypeList;
    }

    public void setReturnTypeList(List<String> returnTypeList) {
        this.returnTypeList = returnTypeList;
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

    public CompanyInfomation getCompanyRepository() {
        return companyRepository;
    }

    public void setCompanyRepository(CompanyInfomation companyRepository) {
        this.companyRepository = companyRepository;
    }

}
