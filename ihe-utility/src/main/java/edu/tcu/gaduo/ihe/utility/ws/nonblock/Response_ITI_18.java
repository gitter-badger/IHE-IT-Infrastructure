/**
 * 
 */
package edu.tcu.gaduo.ihe.utility.ws.nonblock;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.utility.ws.nonblock._interface.IResponse;

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
	private Set<String> list;

	/**
	 * @param msgContext
	 */
	public void parser(MessageContext msgContext) {
		setList(new TreeSet<String>());
		if (msgContext != null) {
			parser(msgContext.getEnvelope());
		} else {
			this.setCodeContext("NO MessageContext");
		}
	}

	public void parser(SOAPEnvelope envelope) {
		if (envelope != null) {
			SOAPBody body = envelope.getBody();// first layer
			parser(body);			
		}
	}
	
	public void parser(SOAPBody body){
		response = body.getFirstElement(); // second layer
		parser(response);
	}
	
	public void parser (OMElement response){
		setList(new TreeSet<String>());
//		logger.info(response);
		if (response != null) {
			status = response.getAttributeValue(new QName("status"));
			OMElement list = response.getFirstElement();
			if (list != null) {
				@SuppressWarnings("unchecked")
				Iterator<OMElement> elements = list.getChildElements();
				while (elements.hasNext()) {
					OMElement element = elements.next();// third layer
//					logger.info(element);
					if (element != null) {
						if (status.equals(Namespace.SUCCESS
								.getNamespace())) {
							success(element);
						} else if (status.equals(Namespace.FAILURE
								.getNamespace())) {
							failure(element);
						}
					}
				}
			}
		}
		synchronized (this) {
			this.notify();
		}
	}

	private void success(OMElement element){
		logger.info("SUCCESS");
		QName qname = new QName("objectType");
		String objectType = element.getAttributeValue(qname);
		objectType = objectType != null ? objectType.trim() : null;
		logger.info(objectType);
		if (objectType != null) {// LeafClass
			String id = element.getAttributeValue(new QName("id")).trim();
			this.list.add(id);
//			if (objectType
//					.equals(ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_OBJECT)) {
//				this.list.add(id);
//			} else if (objectType
//					.equals(ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_OBJECT)) {
//				this.list.add(id);
//			} else if (objectType
//					.equals(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_OBJECT)) {
//				this.list.add(id);
//			}
		} else {
			objectRef(element);
		}
	}
	
	private void failure(OMElement element){
		logger.info("FAILURE");
		this.setCodeContext(setValue(element,
				"codeContext"));
		this.setErrorCode(setValue(element,
				"errorCode"));
		this.setLocation(setValue(element,
				"location"));
		this.setSeverity(setValue(element,
				"severity"));
	}

//	private void leafClass(){
//		
//	}
	
	private void objectRef(OMElement element){
		/* ObjectRef */
		logger.info("ObjectRef");
		String id = element.getAttributeValue(
				new QName("id")).trim();
		this.list.add(id);
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

	public Set<String> getList() {
		return list;
	}

	public void setList(Set<String> list) {
		this.list = list;
	}

	public void addItemToList(String str) {
		this.list.add(str);
	}

	public boolean clean() {
    	errorCode = "";
    	codeContext = "";
    	status = "";
    	severity = "";
    	location = "";
    	return true;
	}
}
