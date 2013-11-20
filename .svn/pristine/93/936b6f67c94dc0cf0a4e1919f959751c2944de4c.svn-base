/**
 * 
 */
package com.gaduo.zk.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeNode;

import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.zk.model.attachment.AttachmentEntry;
import com.gaduo.zk.model.attachment.AttachmentEntryTreeNode;
import com.gaduo.zk.model.code.Code;

/**
 * @author Gaduo
 */
public class MetadataGenerator {
	private OMElement source;
	private String pid5, pid3, pid8 = "M", pid11;
	private Date pid7;
	private String sourceID;
	private String operations = "12049"; // zul 認  string, 不認 int  
	private String authorPerson, authorInstitution, authorRole, authorSpecialty;

	private Code contentTypeCode;
	private Code classCode;
	private Code confidentialityCode;
	private Code formatCode;
	private Code healthcareFacilityTypeCode;
	private Code practiceSettingCode;
	private Code typeCode;
	private Code eventCodeList;
	private Code folderCodeList;

	private CompanyInfomation companyRepository;

	private List<AttachmentEntry> documentList;
	private AttachmentEntryTreeNode<AttachmentEntry> root;
	private DefaultTreeModel<AttachmentEntry> folderList;

	private String existingDocument;
	private String existingFolder;

	private IAxiomUtil axiom;

	public MetadataGenerator() {
		setAxiom(new AxiomUtil());
		setDocumentList(new ArrayList<AttachmentEntry>());
		setRoot(new AttachmentEntryTreeNode<AttachmentEntry>(null, null));
		setFolderList(new DefaultTreeModel<AttachmentEntry>(root));
	}

	public void build() {
		source = axiom.createOMElement("MetadataGenerator", null, null);
		source.addChild(axiom.createOMElement("RepositoryUrl",
				companyRepository.getRepositoryEndpoint()));
		source.addChild(axiom.createOMElement("SourceID", sourceID));
		source.addChild(axiom.createOMElement("Operations", operations + ""));
		patient();
		author();
		codes();

		if (!this.documentList.isEmpty()) {
			OMElement doclist = axiom.createOMElement("Documents", null, null);
			this.source.addChild(doclist);
			Iterator<AttachmentEntry> iterator = this.documentList.iterator();
			while (iterator.hasNext()) {
				AttachmentEntry doc = iterator.next();
				doclist.addChild(buildDocument(doc));
			}
		}
		List<TreeNode<AttachmentEntry>> flist = getRoot();
		if ((!operations.equals("11971") || !operations.equals("11973")) && !flist.isEmpty()) {
			setOperations("11969");// 11970
			Iterator<TreeNode<AttachmentEntry>> iterator = flist.iterator();
			while (iterator.hasNext()) {
				TreeNode<AttachmentEntry> next = iterator.next();
				this.source.addChild(buildFolder(next));
				if (next.getChildCount() > 0) {
					setOperations("11970");
				}
			}
			source.addChild(axiom.createOMElement("FolderCodeList",
					folderCodeList.getCode()));
		}
		OMElement existd, existf;
		int opt = Integer.valueOf(operations);
		switch (opt) {
		case 11971:
			existf = axiom.createOMElement("ExistingFolder", null, null);
			existf.setText(existingFolder);
			this.source.addChild(existf);
			break;
		case 11973:
			existd = axiom.createOMElement("ExistingDocumentEntry", null, null);
			existd.setText(existingDocument);
			this.source.addChild(existd);
			existf = axiom.createOMElement("ExistingFolder", null, null);
			existf.setText(existingFolder);
			this.source.addChild(existf);
			break;
		case 11974:
			existd = axiom.createOMElement("ExistingDocumentEntry", null, null);
			existd.setText(existingDocument);
			this.source.addChild(existd);
			break;
		case 11975:
			existd = axiom.createOMElement("ExistingDocumentEntry", null, null);
			existd.setText(existingDocument);
			this.source.addChild(existd);
			break;
		case 11976:
			existd = axiom.createOMElement("ExistingDocumentEntry", null, null);
			existd.setText(existingDocument);
			this.source.addChild(existd);
			break;
		case 11977:
			existd = axiom.createOMElement("ExistingDocumentEntry", null, null);
			existd.setText(existingDocument);
			this.source.addChild(existd);
			break;
		}
	}

	private void patient() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		OMElement element;
		element = axiom.createOMElement("SourcePatientId", pid3);
		source.addChild(element);
		element = axiom.createOMElement("SourcePatientInfo", null);
		element.addChild(axiom.createOMElement("PID-3", getPid3()));
		element.addChild(axiom.createOMElement("PID-5", getPid5()));
		element.addChild(axiom.createOMElement("PID-7",
				dateFormat.format(getPid7())));
		element.addChild(axiom.createOMElement("PID-8", getPid8()));
		element.addChild(axiom.createOMElement("PID-11", getPid11()));
		source.addChild(element);
	}

	private void author() {
		OMElement element;
		element = axiom.createOMElement("Author", null, null);
		element.addChild(axiom.createOMElement("AuthorPerson", authorPerson));
		element.addChild(axiom.createOMElement("AuthorInstitution",
				authorInstitution));
		element.addChild(axiom.createOMElement("AuthorRole", authorRole));
		element.addChild(axiom.createOMElement("AuthorSpecialty",
				authorSpecialty));
		source.addChild(element);
	}

	private void codes() {
		source.addChild(axiom.createOMElement("ClassCode", classCode.getCode()));
		source.addChild(axiom.createOMElement("ConfidentialityCode",
				confidentialityCode.getCode()));
		source.addChild(axiom.createOMElement("FormatCode",
				formatCode.getCode()));
		source.addChild(axiom.createOMElement("HealthcareFacilityTypeCode",
				healthcareFacilityTypeCode.getCode()));
		source.addChild(axiom.createOMElement("PracticeSettingCode",
				practiceSettingCode.getCode()));
		source.addChild(axiom.createOMElement("TypeCode", typeCode.getCode()));
		source.addChild(axiom.createOMElement("ContentTypeCode",
				contentTypeCode.getCode()));
		source.addChild(axiom.createOMElement("EventCodeList",
				eventCodeList.getCode()));
	}

	public OMElement buildDocument(AttachmentEntry doc) {
		OMElement document = axiom.createOMElement("Document", null, null);
		OMElement fileName = axiom.createOMElement("Title", null, null);
		fileName.setText(doc.getTitle());
		document.addChild(fileName);
		OMElement description = axiom
				.createOMElement("Description", null, null);
		description.setText(doc.getDescription());
		document.addChild(description);
		OMElement fileContent = axiom.createOMElement("Content", null, null);
		fileContent.setText(doc.getContent());
		document.addChild(fileContent);
		return document;
	}

	private OMNode buildFolder(TreeNode<AttachmentEntry> next) {
		AttachmentEntry ff = next.getData();
		OMElement folder = axiom.createOMElement("Folder", null, null);
		OMElement fileName = axiom.createOMElement("Title", null, null);
		fileName.setText(ff.getTitle());
		folder.addChild(fileName);
		OMElement description = axiom
				.createOMElement("Description", null, null);
		description.setText(ff.getDescription());
		folder.addChild(description);
		for (int i = 0; i < next.getChildCount(); i++) {
			AttachmentEntry doc = next.getChildAt(i).getData();
			folder.addChild(buildDocument(doc));
		}
		return folder;
	}

	public String getSourceID() {
		return this.sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getOperations() {
		return this.operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public Code getContentTypeCode() {
		return this.contentTypeCode;
	}

	public void setContentTypeCode(Code contentTypeCode) {
		this.contentTypeCode = contentTypeCode;
	}

	public Code getClassCode() {
		return this.classCode;
	}

	public void setClassCode(Code classCode) {
		this.classCode = classCode;
	}

	public Code getConfidentialityCode() {
		return this.confidentialityCode;
	}

	public void setConfidentialityCode(Code confidentialityCode) {
		this.confidentialityCode = confidentialityCode;
	}

	public Code getFormatCode() {
		return this.formatCode;
	}

	public void setFormatCode(Code formatCode) {
		this.formatCode = formatCode;
	}

	public Code getHealthcareFacilityTypeCode() {
		return this.healthcareFacilityTypeCode;
	}

	public void setHealthcareFacilityTypeCode(Code healthcareFacilityTypeCode) {
		this.healthcareFacilityTypeCode = healthcareFacilityTypeCode;
	}

	public Code getPracticeSettingCode() {
		return this.practiceSettingCode;
	}

	public void setPracticeSettingCode(Code practiceSettingCode) {
		this.practiceSettingCode = practiceSettingCode;
	}

	public Code getEventCodeList() {
		return this.eventCodeList;
	}

	public void setEventCodeList(Code eventCodeList) {
		this.eventCodeList = eventCodeList;
	}

	public Code getFolderCodeList() {
		return folderCodeList;
	}

	public void setFolderCodeList(Code folderCodeList) {
		this.folderCodeList = folderCodeList;
	}

	public Code getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Code typeCode) {
		this.typeCode = typeCode;
	}

	public String getPid5() {
		return this.pid5;
	}

	public void setPid5(String pid5) {
		this.pid5 = pid5;
	}

	public String getPid3() {
		return this.pid3;
	}

	public void setPid3(String pid3) {
		this.pid3 = pid3;
	}

	public String getPid8() {
		return this.pid8;
	}

	public void setPid8(String pid8) {
		this.pid8 = pid8;
	}

	public Date getPid7() {
		return pid7;
	}

	public void setPid7(Date pid7) {
		this.pid7 = pid7;
	}

	public String getPid11() {
		return this.pid11;
	}

	public void setPid11(String pid11) {
		this.pid11 = pid11;
	}

	public String getAuthorPerson() {
		return this.authorPerson;
	}

	public void setAuthorPerson(String authorPerson) {
		this.authorPerson = authorPerson;
	}

	public String getAuthorInstitution() {
		return this.authorInstitution;
	}

	public void setAuthorInstitution(String authorInstitution) {
		this.authorInstitution = authorInstitution;
	}

	public String getAuthorRole() {
		return this.authorRole;
	}

	public void setAuthorRole(String authorRole) {
		this.authorRole = authorRole;
	}

	public String getAuthorSpecialty() {
		return this.authorSpecialty;
	}

	public void setAuthorSpecialty(String authorSpecialty) {
		this.authorSpecialty = authorSpecialty;
	}

	public OMElement getSource() {
		return this.source;
	}

	public void setSource(OMElement source) {
		this.source = source;
	}

	public IAxiomUtil getAxiom() {
		return this.axiom;
	}

	public void setAxiom(AxiomUtil axiom) {
		this.axiom = axiom;
	}

	// -- Document
	public List<AttachmentEntry> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<AttachmentEntry> documentList) {
		this.documentList = documentList;
	}

	public void addDocumentItem(AttachmentEntry doc) {
		this.documentList.add(doc);
	}

	public void removeDocumentItem(int i) {
		this.documentList.remove(i);
	}

	// -- Document
	// -- Folder
	public List<TreeNode<AttachmentEntry>> getRoot() {
		return this.root.getChildren();
	}

	public void setRoot(AttachmentEntryTreeNode<AttachmentEntry> root) {
		this.root = root;
	}

	public DefaultTreeModel<AttachmentEntry> getFolderList() {
		return folderList;
	}

	public void setFolderList(DefaultTreeModel<AttachmentEntry> folderList) {
		this.folderList = folderList;
	}

	public void addFolderItem(AttachmentEntry folder) {
		this.root
				.add(new AttachmentEntryTreeNode<AttachmentEntry>(folder, null));
	}

	public void removeFolderItem(int i) {
		this.root.remove(i);
	}

	// -- Folder
	// Existing
	public String getExistingDocument() {
		return existingDocument;
	}

	public void setExistingDocument(String existingDocument) {
		this.existingDocument = existingDocument;
	}

	public String getExistingFolder() {
		return existingFolder;
	}

	public void setExistingFolder(String existingFolder) {
		this.existingFolder = existingFolder;
	}

	// Existing
	public CompanyInfomation getCompanyRepository() {
		return companyRepository;
	}

	public void setCompanyRepository(CompanyInfomation companyRepository) {
		this.companyRepository = companyRepository;
	}

}
