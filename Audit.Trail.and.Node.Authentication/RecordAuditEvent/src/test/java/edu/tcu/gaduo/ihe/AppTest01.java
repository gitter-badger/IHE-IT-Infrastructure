package edu.tcu.gaduo.ihe;


import org.apache.axiom.om.OMElement;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.tcu.gaduo.ihe.constants.atna.AuditSourceTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.CodeType;
import edu.tcu.gaduo.ihe.constants.atna.ETransaction;
import edu.tcu.gaduo.ihe.constants.atna.EventOutcomeIndicator;
import edu.tcu.gaduo.ihe.constants.atna.NetworkAccessPointTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.ParticipantObjectIDTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.ParticipantObjectTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.ParticipantObjectTypeCodeRole;
import edu.tcu.gaduo.ihe.constants.atna.RFC3881;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.ActiveParticipantType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.AuditMessageType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.AuditSourceIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.CodedValueType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.EventIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.ParticipantObjectIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.service.RecordAuditEvent;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_18_110112;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_41_110106;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;

/**
 * @author Gaduo
 *
 */
public class AppTest01 extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest01(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * 
	 */
	public void testAppITI_41_110106() {
		String endPoint = "http://ihexds.nist.gov:9080/tf6/services/xdsrepositoryb";
		String patientId = "TestPatient1^^^&1.3.6.1.4.1.21367.13.20.1000&ISO";
		String XDSSubmissionSetUniqueId = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20140417204613.1";
		
		ISysLoger loger = new SysLogerITI_41_110106();
		((SysLogerITI_41_110106) loger).setEndPoint(endPoint);
		((SysLogerITI_41_110106) loger).setPatientId(patientId);
		((SysLogerITI_41_110106) loger).setXDSSubmissionSetUniqueId(XDSSubmissionSetUniqueId);
		((SysLogerITI_41_110106) loger).setEventOutcomeIndicator(EventOutcomeIndicator.Success);
		((SysLogerITI_41_110106) loger).setUserID("1");
		OMElement element = loger.build();
		System.out.println(element);
		
		RecordAuditEvent rae = new RecordAuditEvent();
		rae.AuditGenerator(element);
	}
	
	public void testAppITI_18_110112() {
		String endPoint = "http://ihexds.nist.gov:9080/tf6/services/xdsrepositoryb";
		String patientId = "TestPatient1^^^&1.3.6.1.4.1.21367.13.20.1000&ISO";
		String XDSSubmissionSetUniqueId = "1.3.6.1.4.1.21367.2010.1.2.203.64.84.247.20140417204613.1";
		
		ISysLoger loger = new SysLogerITI_18_110112();
		((SysLogerITI_18_110112) loger).setEndPoint(endPoint);
		((SysLogerITI_18_110112) loger).setPatientId(patientId);
		((SysLogerITI_18_110112) loger).setEventOutcomeIndicator(EventOutcomeIndicator.Success);
		((SysLogerITI_18_110112) loger).setUserID("1");
		OMElement element = loger.build();
		System.out.println(element);
		
		RecordAuditEvent rae = new RecordAuditEvent();
		rae.AuditGenerator(element);
	}

}
