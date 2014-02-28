/**
 * 
 */
package edu.tcu.gaduo.zk.view_model.xds_b.iti_18;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;


import edu.tcu.gaduo.ihe.utility.xml.XMLPath;
import edu.tcu.gaduo.zk.model.code.Code;
import edu.tcu.gaduo.zk.model.code.Codes;
import edu.tcu.gaduo.zk.model.code.CodesImpl;

/**
 * @author Gaduo
 * 
 */
public class FindDocuments extends StoredQuery implements IParameter {
	public static Logger logger = Logger.getLogger(FindDocuments.class);

	private String patientId;
	private String status;
	private Date creationTimeFrom;
	private Date creationTimeTo;
	private Date serviceStartTimeFrom;
	private Date serviceStartTimeTo;
	private Date serviceStopTimeFrom;
	private Date serviceStopTimeTo;

	private Code classCode;
	private Code confidentialityCode;
	private Code formatCode;
	private Code healthcareFacilityTypeCode;
	private Code practiceSettingCode;
	private Code typeCode;
	private Code eventCodeList;

	private Codes classCodeList;
	private Codes confidentialityCodeList;
	private Codes formatCodeList;
	private Codes healthcareFacilityTypeCodeList;
	private Codes practiceSettingCodeList;
	private Codes typeCodeList;
	private Codes eventCodeListList;

	public FindDocuments() {
		super();
		this.setPatientId("c6002e5679534ea^^^&1.3.6.1.4.1.21367.2005.3.7&ISO");
		int oidStartAt = getPatientId().indexOf("^");
        String patientOid = getPatientId().substring(oidStartAt);
		if (Executions.getCurrent().getParameter("patientId") != null && Executions.getCurrent().getParameter("patientId").length() > 0)
		    this.setPatientId(Executions.getCurrent().getParameter("patientId") + patientOid);
		this.setStatus("urn:oasis:names:tc:ebxml-regrep:StatusType:Approved");
		Calendar dayFrom = Calendar.getInstance();
		dayFrom.add(Calendar.MONTH, -6);
		dayFrom.set(Calendar.HOUR_OF_DAY, 0);
		dayFrom.set(Calendar.MINUTE, 0);
		dayFrom.set(Calendar.SECOND, 0);
		this.setCreationTimeFrom(dayFrom.getTime());
		this.setCreationTimeTo(new Date());

		XMLPath codes = new XMLPath(getClass().getClassLoader()
				.getResourceAsStream("codes.xml"));
		setClassCodeList(new CodesImpl(codes, "classCode"));
		setConfidentialityCodeList(new CodesImpl(codes, "confidentialityCode"));
		setFormatCodeList(new CodesImpl(codes, "formatCode"));
		setHealthcareFacilityTypeCodeList(new CodesImpl(codes,
				"healthcareFacilityTypeCode"));
		setPracticeSettingCodeList(new CodesImpl(codes, "practiceSettingCode"));
		setTypeCodeList(new CodesImpl(codes, "typeCode"));
		setEventCodeListList(new CodesImpl(codes, "eventCodeList"));
	}

	public List<OMElement> getParameters() {
		if (this.getPatientId() != null) {
			super.list.add(super.addParameter("$XDSDocumentEntryPatientId",
					this.getPatientId()));
		} else {
			return null;
		}
		if (this.getStatus() != null) {
			super.list.add(super.addParameter("$XDSDocumentEntryStatus",
					this.getStatus()));
		} else {
			return null;
		}
		if (creationTimeFrom != null && creationTimeTo != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			super.list.add(super.addParameter(
					"$XDSDocumentEntryCreationTimeFrom",
					dateFormat.format(this.getCreationTimeFrom())));
			super.list.add(super.addParameter(
					"$XDSDocumentEntryCreationTimeTo",
					dateFormat.format(this.getCreationTimeTo())));
		}
		if (serviceStartTimeFrom != null && serviceStartTimeTo != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			super.list.add(super.addParameter(
					"$XDSDocumentEntryServiceStartTimeFrom",
					dateFormat.format(this.getServiceStartTimeFrom())));
			super.list.add(super.addParameter(
					"$XDSDocumentEntryServiceStartTimeTo",
					dateFormat.format(this.getServiceStartTimeTo())));
		}
		if (serviceStopTimeFrom != null && serviceStopTimeTo != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			super.list.add(super.addParameter(
					"$XDSDocumentEntryServiceStopTimeFrom",
					dateFormat.format(this.getServiceStopTimeFrom())));
			super.list.add(super.addParameter(
					"$XDSDocumentEntryServiceStopTimeTo",
					dateFormat.format(this.getServiceStopTimeTo())));
		}
		if (this.getClassCode() != null) {
			Code code = this.getClassCode();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter("$XDSDocumentEntryClassCode",
					value));
		}
		if (this.getTypeCode() != null) {
			Code code = this.getTypeCode();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter("$XDSDocumentEntryTypeCode",
					value));
		}//
		if (this.getHealthcareFacilityTypeCode() != null) {
			Code code = this.getHealthcareFacilityTypeCode();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter(
					"$XDSDocumentEntryHealthcareFacilityTypeCode", value));
		}
		if (this.getPracticeSettingCode() != null) {
			Code code = this.getPracticeSettingCode();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter(
					"$XDSDocumentEntryPracticeSettingCode", value));
		}
		if (this.getEventCodeList() != null) {
			Code code = this.getEventCodeList();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter("$XDSDocumentEntryEventCodeList",
					value));
		}
		if (this.getConfidentialityCode() != null) {
			Code code = this.getConfidentialityCode();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter(
					"$XDSDocumentEntryConfidentialityCode", value));
		}
		if (this.getFormatCode() != null) {
			Code code = this.getFormatCode();
			String value = code.getCode() + "^^" + code.getCodingScheme();
			super.list.add(super.addParameter("$XDSDocumentEntryFormatCode",
					value));
		}
		return super.list;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationTimeFrom() {
		return this.creationTimeFrom;
	}

	public void setCreationTimeFrom(Date creationTimeFrom) {
		this.creationTimeFrom = creationTimeFrom;
	}

	public Date getCreationTimeTo() {
		return this.creationTimeTo;
	}

	public void setCreationTimeTo(Date creationTimeTo) {
		this.creationTimeTo = creationTimeTo;
	}

	public Date getServiceStartTimeFrom() {
		return this.serviceStartTimeFrom;
	}

	public void setServiceStartTimeFrom(Date serviceStartTimeFrom) {
		this.serviceStartTimeFrom = serviceStartTimeFrom;
	}

	public Date getServiceStartTimeTo() {
		return this.serviceStartTimeTo;
	}

	public void setServiceStartTimeTo(Date serviceStartTimeTo) {
		this.serviceStartTimeTo = serviceStartTimeTo;
	}

	public Date getServiceStopTimeFrom() {
		return this.serviceStopTimeFrom;
	}

	public void setServiceStopTimeFrom(Date serviceStopTimeFrom) {
		this.serviceStopTimeFrom = serviceStopTimeFrom;
	}

	public Date getServiceStopTimeTo() {
		return this.serviceStopTimeTo;
	}

	public void setServiceStopTimeTo(Date serviceStopTimeTo) {
		this.serviceStopTimeTo = serviceStopTimeTo;
	}

	// ----

	public List<Code> getClassCodeList() {
		return this.classCodeList.findAll();
	}

	public void setClassCodeList(CodesImpl classCodeList) {
		this.classCodeList = classCodeList;
	}

	public List<Code> getConfidentialityCodeList() {
		return this.confidentialityCodeList.findAll();
	}

	public void setConfidentialityCodeList(CodesImpl confidentialityCodeList) {
		this.confidentialityCodeList = confidentialityCodeList;
	}

	public List<Code> getFormatCodeList() {
		return this.formatCodeList.findAll();
	}

	public void setFormatCodeList(CodesImpl formatCodeList) {
		this.formatCodeList = formatCodeList;
	}

	public List<Code> getHealthcareFacilityTypeCodeList() {
		return this.healthcareFacilityTypeCodeList.findAll();
	}

	public void setHealthcareFacilityTypeCodeList(
			CodesImpl healthcareFacilityTypeCodeList) {
		this.healthcareFacilityTypeCodeList = healthcareFacilityTypeCodeList;
	}

	public List<Code> getPracticeSettingCodeList() {
		return this.practiceSettingCodeList.findAll();
	}

	public void setPracticeSettingCodeList(CodesImpl practiceSettingCodeList) {
		this.practiceSettingCodeList = practiceSettingCodeList;
	}

	public List<Code> getEventCodeListList() {
		return eventCodeListList.findAll();
	}

	public void setEventCodeListList(CodesImpl eventCodeListList) {
		this.eventCodeListList = eventCodeListList;
	}

	public List<Code> getTypeCodeList() {
		return typeCodeList.findAll();
	}

	public void setTypeCodeList(CodesImpl typeCodeList) {
		this.typeCodeList = typeCodeList;
	}

	public Code getConfidentialityCode() {
		return confidentialityCode;
	}

	public void setConfidentialityCode(Code confidentialityCode) {
		this.confidentialityCode = confidentialityCode;
	}

	public Code getEventCodeList() {
		return eventCodeList;
	}

	public void setEventCodeList(Code eventCodeList) {
		this.eventCodeList = eventCodeList;
	}

	public Code getFormatCode() {
		return formatCode;
	}

	public void setFormatCode(Code formatCode) {
		this.formatCode = formatCode;
	}

	public Code getPracticeSettingCode() {
		return practiceSettingCode;
	}

	public void setPracticeSettingCode(Code practiceSettingCode) {
		this.practiceSettingCode = practiceSettingCode;
	}

	public Code getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Code typeCode) {
		this.typeCode = typeCode;
	}

	public Code getClassCode() {
		return classCode;
	}

	public void setClassCode(Code classCode) {
		this.classCode = classCode;
	}

	public Code getHealthcareFacilityTypeCode() {
		return healthcareFacilityTypeCode;
	}

	public void setHealthcareFacilityTypeCode(Code healthcareFacilityTypeCode) {
		this.healthcareFacilityTypeCode = healthcareFacilityTypeCode;
	}
}
