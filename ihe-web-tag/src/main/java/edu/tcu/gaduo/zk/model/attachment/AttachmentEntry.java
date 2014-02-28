/**
 * 
 */
package edu.tcu.gaduo.zk.model.attachment;

/**
 * @author Gaduo
 * 
 */
public class AttachmentEntry implements Comparable<AttachmentEntry> {
	public static final int DOCUMENT = 0;
	public static final int FOLDER = 1;

	private String title;
	private String description;
	private String content = "";
	private int type;
	
	private String contentTypeCode;
	private String classCode;
	private String confidentialityCode;
	private String formatCode;
	private String healthcareFacilityTypeCode;
	private String practiceSettingCode;
	private String eventCodeList;
	private String typeCode;
	private String mimeType;

	public AttachmentEntry(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return title + "," + description + "," + content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int compareTo(AttachmentEntry o) {
		return o.content.compareTo(this.content);
	}

    public String getContentTypeCode() {
        return contentTypeCode;
    }

    public void setContentTypeCode(String contentTypeCode) {
        this.contentTypeCode = contentTypeCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getConfidentialityCode() {
        return confidentialityCode;
    }

    public void setConfidentialityCode(String confidentialityCode) {
        this.confidentialityCode = confidentialityCode;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    public String getHealthcareFacilityTypeCode() {
        return healthcareFacilityTypeCode;
    }

    public void setHealthcareFacilityTypeCode(String healthcareFacilityTypeCode) {
        this.healthcareFacilityTypeCode = healthcareFacilityTypeCode;
    }

    public String getPracticeSettingCode() {
        return practiceSettingCode;
    }

    public void setPracticeSettingCode(String practiceSettingCode) {
        this.practiceSettingCode = practiceSettingCode;
    }

    public String getEventCodeList() {
        return eventCodeList;
    }

    public void setEventCodeList(String eventCodeList) {
        this.eventCodeList = eventCodeList;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}
