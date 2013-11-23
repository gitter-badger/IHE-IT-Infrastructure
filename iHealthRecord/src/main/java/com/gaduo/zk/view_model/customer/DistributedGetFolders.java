package com.gaduo.zk.view_model.customer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;

import com.gaduo.ihe.it_infrastructure.xds_transaction.service.RegistryStoredQuery;
import com.gaduo.webservice.LeafClass.MetadataParser;
import com.gaduo.zk.model.CompanyInfomation;
import com.gaduo.zk.model.QueryGenerator;
import com.gaduo.zk.model.KeyValue.KeyValue;
import com.gaduo.zk.model.KeyValue.KeyValuesImpl;
import com.gaduo.zk.view_model.xds_b.iti_18.GetFolders;

public class DistributedGetFolders implements Runnable {
	private String id;
	private String title ;
	private String description;
	private String lastUpdateTime;
	public void run() {
		query();
	}
	
	public void query(){
		QueryGenerator query = new QueryGenerator();
		CompanyInfomation company = new CompanyInfomation();
		company.setRegistryEndpoint("http://203.64.84.112:8010/axis2/services/xdsregistryb?wsdl");
		query.setCompanyRegistry(company);
		query.setQueryType(new KeyValue("GetFolders", "urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4"));
		query.setReturnType("LeafClass");
		GetFolders getFolders = new GetFolders();
		getFolders.setId(id);
		query.setParameter(getFolders);
		query.build();
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		rsq.QueryGenerator(query.getQuery());

		MessageContext context = rsq.getContext();
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope()
				: null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		if (response != null) {
			MetadataParser mp = new MetadataParser(response);
			Map<String, KeyValuesImpl> map = mp.getMapArray();
			if (map != null) {
				KeyValuesImpl keyValueList = map.get(id);
				if (keyValueList != null) {
					List<KeyValue> all = keyValueList.findAll();
					Iterator<KeyValue> iterator = all.iterator();
					while (iterator.hasNext()) {
						KeyValue kv = iterator.next();
						String key = kv.getKey();
						// logger.info(key);
						if (key.equalsIgnoreCase("Name")) {
							title = kv.getValue();
						}
						if (key.equalsIgnoreCase("Description")) {
							description = kv.getValue();
						}
						if (key.equalsIgnoreCase("lastUpdateTime")) {
							lastUpdateTime = kv.getValue();
						}
					}
				}
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
