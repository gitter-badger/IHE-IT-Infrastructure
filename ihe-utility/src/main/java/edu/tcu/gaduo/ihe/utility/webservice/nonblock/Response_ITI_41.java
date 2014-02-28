/**
 * 
 */
package edu.tcu.gaduo.ihe.utility.webservice.nonblock;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;


/**
 * @author Gaduo
 */
public class Response_ITI_41 implements IResponse{
	public static Logger logger = Logger.getLogger(Response_ITI_41.class);
    private OMElement response;
    private String status;
    private String codeContext;
    private String errorCode;
    private String severity;
    private String location;

    /**
     * @param msgContext
     */
    public void parser(MessageContext msgContext) {
        if (msgContext != null) {
            parser(msgContext.getEnvelope());
        }else {
            this.setCodeContext("NO MessageContext");
        }
    }
    
    public void parser(SOAPEnvelope envelope ){
        if (envelope != null) {
            SOAPBody body = envelope.getBody();
            response = body.getFirstElement();
            if (response != null) {
                OMElement errorList = response.getFirstElement();
                status = response.getAttributeValue(new QName("status"));
                if (errorList != null) {
                    @SuppressWarnings("unchecked")
                    Iterator<OMElement> elements = errorList.getChildElements();
                    while (elements.hasNext()) {
                        OMElement element = elements.next();
                        if (element != null) {
                            this.setCodeContext(setValue(element, "codeContext"));
                            this.setErrorCode(setValue(element, "errorCode"));
                            this.setLocation(setValue(element, "location"));
                            this.setSeverity(setValue(element, "severity"));
                        }
                    }
                }else {
                    this.setCodeContext("NO ErrorList");    
                }
            }else {
                this.setCodeContext("NO SOAP Body");
            }
            synchronized (this) {
                this.notify();
            }
        }else {
            this.setCodeContext("NO SOAP Envelope");
        }
    }
    
    private String setValue(OMElement element, String type) {
        try {
           return element.getAttributeValue(new QName(type)).trim();
        }catch(java.lang.NullPointerException e) {
            return  "";
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
        logger.trace(codeContext);
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
        return null;
    }

    
    public boolean clean(){
    	errorCode = "";
    	codeContext = "";
    	status = "";
    	severity = "";
    	location = "";
    	return true;
    }

}
