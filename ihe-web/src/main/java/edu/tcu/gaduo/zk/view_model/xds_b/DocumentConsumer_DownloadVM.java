/**
 * 
 */
package edu.tcu.gaduo.zk.view_model.xds_b;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Filedownload;


import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RetrieveDocumentSet;
import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.ws.nonblock.Response_ITI_43;
import edu.tcu.gaduo.ihe.utility.ws.nonblock.RetrieveResult;
import edu.tcu.gaduo.ihe.utility.ws.nonblock._interface.IResponse;
import edu.tcu.gaduo.zk.model.CompanyInfomation;
import edu.tcu.gaduo.zk.view_model.CompanyInfoVM;

/**
 * @author Gaduo
 */
public class DocumentConsumer_DownloadVM {
	public static Logger logger = Logger
			.getLogger(DocumentConsumer_DownloadVM.class);

	private IResponse ITI_43;
	private CompanyInfomation companyRepository;
	private String uniqueId;
	private String homeCommunityId;
	private Set<String> list;
	private List<RetrieveResult> retrieveResultList;
	private List<CompanyInfomation> companyInfomations;

	private RetrieveDocumentSet rd;

	@Init
	public void init() {
		this.setList(new TreeSet<String>());
		// http://ihewiki.wustl.edu/wiki/index.php/NA2010-XDS_homeCommunityId_OIDs
		setHomeCommunityId("");

		rd = new RetrieveDocumentSet();
		CompanyInfoVM c = new CompanyInfoVM();
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
		ICertificate cert = Certificate.getInstance();
		cert.setCertificate();
		RetrieveDocumentSet rds = new RetrieveDocumentSet();
		rds.RetrieveGenerator(list, companyRepository.getRepositoryEndpoint(), companyRepository.getRepositoryUniqueId(), getHomeCommunityId());
		ITI_43 = new Response_ITI_43();
		ITI_43.parser(rds.getContext());
		setRetrieveResultList(((Response_ITI_43) ITI_43).getRetrieveResultList());
	}

	@NotifyChange({ "list", "uniqueId" })
	@Command
	public void addDocumentItem() {
		if (uniqueId.length() > 0 && !list.contains(uniqueId)) {
			list.add(uniqueId);
		}
		logger.info("Add : " + uniqueId);
		this.setUniqueId("");
	}

	@NotifyChange({ "list" })
	@Command
	public void removeDocumentItem(@BindingParam("each") String item) {
		logger.info("Remove:" + item);
		if (list.contains(item)) {
			list.remove(item);
		}
	}

	@Command
	public void download(@BindingParam("each") RetrieveResult rr) {
		byte[] array = org.apache.commons.codec.binary.Base64.decodeBase64(rr
				.getDocument().getBytes());
		Filedownload.save(array, rr.getMimeType(), rr.getDocumentUniqueId()
				+ "." + rd.extractExtension(rr.getMimeType()));
	}

	public Set<String> getList() {
		return list;
	}

	public void setList(Set<String> list) {
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
