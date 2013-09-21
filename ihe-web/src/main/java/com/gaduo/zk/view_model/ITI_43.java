/**
 * 
 */
package com.gaduo.zk.view_model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.gaduo.ihe.it_infrastructure.xds_transaction.service.RetrieveDocumentSet;
import com.gaduo.ihe.security.Certificate;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.Common;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility._interface.ICommon;
import com.gaduo.ihe.utility.webservice.nonblock.IResponse;
import com.gaduo.ihe.utility.webservice.nonblock.NonBlockCallBack;
import com.gaduo.ihe.utility.webservice.nonblock.Response_ITI_43;
import com.gaduo.ihe.utility.webservice.nonblock.RetrieveResult;
import com.gaduo.webservice.ServiceConsumer;
import com.gaduo.webservice.Soap;
import com.gaduo.webservice._interface.ISoap;
import com.gaduo.zk.model.CompanyInfomation;

/**
 * @author Gaduo
 */
public class ITI_43 {
	public static Logger logger = Logger.getLogger(ITI_43.class);

	private IResponse ITI_43;
	private CompanyInfomation companyRepository;
	private String uniqueId;
	private String homeCommunityId;
	private List<String> list;
	private List<RetrieveResult> retrieveResultList;
	private IAxiomUtil axiom;
	private List<CompanyInfomation> companyInfomations;
	RetrieveDocumentSet rd;
	@Init
	public void init() {
		this.setList(new ArrayList<String>());
		// http://ihewiki.wustl.edu/wiki/index.php/NA2010-XDS_homeCommunityId_OIDs
		setHomeCommunityId("");

		rd = new RetrieveDocumentSet();
		CompanyInfo c = new CompanyInfo();
		c.init();
		this.setCompanyInfomations(c.getCompanyInfomations());
		System.gc();
	}

	@NotifyChange({ "ITI_43", "retrieveResultList" })
	@Command
	public void submit() {
		if (companyRepository == null) {
			logger.warn("Choice Repository.");
			return;
		}
		new Certificate().setCertificate();
//		rd.RetrieveGenerator(null);
		OMElement element = buildRetrieveRequest();
		if (element == null)
			return;
		ICommon c = new Common();
		c.saveLog(c.createTime(), "Request_ITI-43", element);
//		ISoap soap = new Soap(companyRepository.getRepositoryEndpoint(),
//				"http://www.w3.org/2005/08/addressing",
//				"urn:ihe:iti:2007:RetrieveDocumentSet", true);
//		MessageContext context = soap.send(element);
//		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
//		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
//		OMElement response = (body != null) ? body.getFirstElement() : null;
		
		ServiceConsumer ServiceConsumer = new ServiceConsumer(
				companyRepository.getRepositoryEndpoint(), element, null,
				"urn:ihe:iti:2007:RetrieveDocumentSet", true, true);
		NonBlockCallBack context = ServiceConsumer.getCallback();
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		if (response == null) {
			logger.warn("ITI-43 No Response.");
			return;
		}
		logger.info(response);
		c.saveLog(c.createTime(), "Response_ITI-43", response );
		ITI_43 = new Response_ITI_43();
		ITI_43.parser(envelope);
		setRetrieveResultList(((Response_ITI_43) ITI_43)
				.getRetrieveResultList());
	}

	@NotifyChange({ "list" })
	@Command
	public void addDocumentItem() {
		if (this.getUniqueId().length() > 0 && !list.contains(uniqueId)) {
			list.add(uniqueId);
			this.setUniqueId("");
		}
	}

	@NotifyChange({ "list" })
	@Command
	public void removeDocumentItem(@BindingParam("each") int i) {
		list.remove(i);
	}

	@Command
	public void download(@BindingParam("each") RetrieveResult rr) {
		byte[] array = org.apache.commons.codec.binary.Base64.decodeBase64(rr
				.getDocument().getBytes());
		Filedownload.save(array, rr.getMimeType(), rr.getDocumentUniqueId()
				+ "." + rd.extractExtension(rr.getMimeType()));
	}

	private OMElement buildRetrieveRequest() {
		axiom = new AxiomUtil();
		OMElement RetrieveDocumentSetRequest = axiom.createOMElement(
				"RetrieveDocumentSetRequest", "urn:ihe:iti:xds-b:2007", "");
		Iterator<String> iterator = this.list.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			OMElement DocumentRequest = axiom.createOMElement(
					"DocumentRequest", "urn:ihe:iti:xds-b:2007", "");

			OMElement RepositoryUniqueId = axiom.createOMElement(
					"RepositoryUniqueId", "urn:ihe:iti:xds-b:2007", "");
			RepositoryUniqueId.setText(companyRepository
					.getRepositoryUniqueId());
			DocumentRequest.addChild(RepositoryUniqueId);

			if (!getHomeCommunityId().equals("")) {
				OMElement HomeCommunityId = axiom.createOMElement(
						"HomeCommunityId", "urn:ihe:iti:xds-b:2007", "");
				HomeCommunityId.setText(getHomeCommunityId());
				DocumentRequest.addChild(HomeCommunityId);
			}
			OMElement DocumentUniqueId = axiom.createOMElement(
					"DocumentUniqueId", "urn:ihe:iti:xds-b:2007", "");
			DocumentUniqueId.setText(next);
			DocumentRequest.addChild(DocumentUniqueId);
			RetrieveDocumentSetRequest.addChild(DocumentRequest);
		}
		logger.info(RetrieveDocumentSetRequest);
		return RetrieveDocumentSetRequest;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public IResponse getITI_43() {
		return this.ITI_43;
	}

	public void setITI_43(IResponse iTI_43) {
		this.ITI_43 = iTI_43;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public List<RetrieveResult> getRetrieveResultList() {
		return retrieveResultList;
	}

	public void setRetrieveResultList(List<RetrieveResult> retrieveResultList) {
		if (retrieveResultList != null)
			logger.trace(retrieveResultList.size());
		this.retrieveResultList = retrieveResultList;
	}

	public CompanyInfomation getCompanyRepository() {
		return companyRepository;
	}

	public void setCompanyRepository(CompanyInfomation companyRepository) {
		this.companyRepository = companyRepository;
	}

	public List<CompanyInfomation> getCompanyInfomations() {
		return companyInfomations;
	}

	public void setCompanyInfomations(List<CompanyInfomation> companyInfomations) {
		this.companyInfomations = companyInfomations;
	}

	public String getHomeCommunityId() {
		return homeCommunityId != null ? homeCommunityId : "";
	}

	public void setHomeCommunityId(String homeCommunityId) {
		this.homeCommunityId = homeCommunityId;
	}

}
