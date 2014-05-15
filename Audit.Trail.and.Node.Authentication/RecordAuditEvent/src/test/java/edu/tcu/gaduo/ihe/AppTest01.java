package edu.tcu.gaduo.ihe;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.axiom.om.OMElement;
import org.junit.Test;

import edu.tcu.gaduo.ihe.constants.atna.EventOutcomeIndicator;
import edu.tcu.gaduo.ihe.iti.atna_transaction.service.RecordAuditEvent;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_18_110112;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_41_110106;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_43_110106;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

/**
 * @author Gaduo
 *
 */
public class AppTest01  {

	/**
	 * @throws UnknownHostException 
	 * 
	 */
	@Test
	public void testAppITI_41_110106() throws UnknownHostException {
		String endPoint = "http://ihexds.nist.gov:9080/tf6/services/xdsrepositoryb";
		String patientId = "TestPatient1^^^&1.3.6.1.4.1.21367.13.20.1000&ISO";
		String XDSSubmissionSetUniqueId = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20140417204613.1";
		
		ISysLoger loger = new SysLogerITI_41_110106();
		((SysLogerITI_41_110106) loger).setEndpoint(endPoint);
		((SysLogerITI_41_110106) loger).setPatientId(patientId);
		((SysLogerITI_41_110106) loger).setXDSSubmissionSetUniqueId(XDSSubmissionSetUniqueId);
		((SysLogerITI_41_110106) loger).setEventOutcomeIndicator(EventOutcomeIndicator.Success);
		/** humanRequestor*/
		((SysLogerITI_41_110106) loger).setUserID("1");
		/** --- Source --- */
		((SysLogerITI_41_110106) loger).setReplyTo("http://www.w3.org/2005/08/addressing/anonymous");
		InetAddress addr = InetAddress.getLocalHost();
		((SysLogerITI_41_110106) loger).setLocalIPAddress(addr.getHostAddress());
		
		OMElement element = loger.build();
		System.out.println(element);
		
		RecordAuditEvent rae = new RecordAuditEvent();
		rae.AuditGenerator(element);
	}
	
	public void testAppITI_18_110112() throws UnknownHostException {
		String endPoint = "http://ihexds.nist.gov:9080/tf6/services/xdsrepositoryb";
		String patientId = "TestPatient1^^^&1.3.6.1.4.1.21367.13.20.1000&ISO";
		
		ISysLoger loger = new SysLogerITI_18_110112();
		((SysLogerITI_18_110112) loger).setEndPoint(endPoint);
		((SysLogerITI_18_110112) loger).setPatientId(patientId);
		((SysLogerITI_18_110112) loger).setEventOutcomeIndicator(EventOutcomeIndicator.Success);
		/** --- Source --- */
		((SysLogerITI_18_110112) loger).setReplyTo("http://www.w3.org/2005/08/addressing/anonymous");
		InetAddress addr = InetAddress.getLocalHost();
		((SysLogerITI_18_110112) loger).setLocalIPAddress(addr.getHostAddress());

		/** QueryParameters */
		((SysLogerITI_18_110112) loger).setQueryUUID("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d");
		IAxiomUtil axiom = AxiomUtil.getInstance();
		OMElement request = axiom.resourcesToOMElement("ITI-18_request_FIND_DOCUMENTS.xml");
		((SysLogerITI_18_110112) loger).setRequest(request );
		
		OMElement element = loger.build();
		System.out.println(element);
		
		RecordAuditEvent rae = new RecordAuditEvent();
		rae.AuditGenerator(element);
	}
	
	public void testAppITI_43_110106() throws UnknownHostException {
		String endPoint = "http://ihexds.nist.gov:9080/tf6/services/xdsrepositoryb";
		String patientId = "TestPatient1^^^&1.3.6.1.4.1.21367.13.20.1000&ISO";
		String documentUniqueId = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20140417204613.1";
		
		ISysLoger loger = new SysLogerITI_43_110106();
		((SysLogerITI_43_110106) loger).setEndpoint(endPoint);
		((SysLogerITI_43_110106) loger).setPatientId(patientId);
		((SysLogerITI_43_110106) loger).addDocument(documentUniqueId, "1.2.3", "");
		((SysLogerITI_43_110106) loger).addDocument(documentUniqueId, "1.2.3", "");
		((SysLogerITI_43_110106) loger).setEventOutcomeIndicator(EventOutcomeIndicator.Success);
		/** humanRequestor*/
		((SysLogerITI_43_110106) loger).setUserID("1");
		/** --- Source --- */
		((SysLogerITI_43_110106) loger).setReplyTo("http://www.w3.org/2005/08/addressing/anonymous");
		InetAddress addr = InetAddress.getLocalHost();
		((SysLogerITI_43_110106) loger).setLocalIPAddress(addr.getHostAddress());
		
		OMElement element = loger.build();
		System.out.println(element);
		
//		RecordAuditEvent rae = new RecordAuditEvent();
//		rae.AuditGenerator(element);
	}

}
