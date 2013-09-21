/**
 * 
 */
package com.gaduo.ihe.utility.webservice.nonblock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

/**
 * @author Gaduo
 */
public class Response_ITI_18 implements IResponse {
	public static Logger logger = Logger.getLogger(Response_ITI_18.class);
	private OMElement response;
	private String status;
	private String codeContext;
	private String errorCode;
	private String severity;
	private String location;
	private List<String> list;

	/**
	 * @param msgContext
	 */
	public void parser(MessageContext msgContext) {
		setList(new ArrayList<String>());
        if (msgContext != null) {
            parser(msgContext.getEnvelope());
        }else {
            this.setCodeContext("NO MessageContext");
        }
	}

	public void parser(SOAPEnvelope envelope) {
		setList(new ArrayList<String>());
		if (envelope != null) {
			SOAPBody body = envelope.getBody();
			response = body.getFirstElement();
			if (response != null) {
				OMElement list = response.getFirstElement();
				status = response.getAttributeValue(new QName("status"));
				if (list != null) {
					@SuppressWarnings("unchecked")
					Iterator<OMElement> elements = list.getChildElements();
					while (elements.hasNext()) {
						OMElement element = elements.next();
						// String objectType = element
						// .getAttributeValue(new QName("objectType"));
						// objectType = objectType != null ? objectType.trim() :
						// null;
						if (element != null) {
							if (status
									.equals("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success")
							/*
							 * && (objectType != null) && (objectType
							 * .equals(ProvideAndRegistryDocumentSet_B_UUIDs
							 * .SUBMISSON_SET_OBJECT) // SUBMISSON_SET ||
							 * objectType
							 * .equals(ProvideAndRegistryDocumentSet_B_UUIDs
							 * .DOC_ENTRY_OBJECT) // Document || objectType
							 * .equals
							 * (ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_OBJECT
							 * ) // Folder )
							 */) {
								this.list.add(element.getAttributeValue(
										new QName("id")).trim());
							}
							if (status
									.equals("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure")) {
								this.setCodeContext(setValue(element,
										"codeContext"));
								this.setErrorCode(setValue(element, "errorCode"));
								this.setLocation(setValue(element, "location"));
								this.setSeverity(setValue(element, "severity"));
							}
						}
					}
				}
			}
			synchronized (this) {
				this.notify();
			}
		}

	}

	private String setValue(OMElement element, String type) {
		try {
			return element.getAttributeValue(new QName(type)).trim();
		} catch (java.lang.NullPointerException e) {
			return "";
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCodeContext() {
		return codeContext;
	}

	public void setCodeContext(String codeContext) {
		this.codeContext = codeContext;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OMElement getResponse() {
		return this.response;
	}

	public void setResponse(OMElement response) {
		this.response = response;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String toString() {
		logger.trace(getCodeContext());
		logger.trace(getStatus());
		logger.trace(getErrorCode());
		logger.trace(getLocation());
		logger.trace(getSeverity());
		logger.trace(getResponse());
		return getCodeContext() + "\t" + getStatus() + "\t" + getErrorCode()
				+ "\t" + getLocation() + "\t" + getSeverity() + "\t"
				+ getResponse();
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void addItemToList(String str) {
		this.list.add(str);
	}
}
