package edu.tcu.gaduo.ihe;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

//import org.openhealthtools.openexchange.audit.ActiveParticipant;
//import org.openhealthtools.openexchange.audit.AuditCodeMappings;
//import org.openhealthtools.openexchange.audit.IheAuditTrail;
//import org.openhealthtools.openexchange.audit.ParticipantObject;

public class AppTest01 extends TestCase{
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
	
	public void testApp() {
	}
//	private IheAuditTrail auditLog = null;
//	private void auditLog(AuditCodeMappings.AuditTypeCodes typeCode, boolean isITI41)   {
//		String replyto = "";
//		String remoteIP = "";
//		String localIP = "";
//		String SubmissionSetUniqueId = "";
//		String PatientId = "";
//		String endpoint = "http://203.64.84.217:8020/axis2/services/xdsrepositoryb";
//
//		ParticipantObject set = new ParticipantObject("SubmissionSet", SubmissionSetUniqueId);
//		ParticipantObject patientObj = new ParticipantObject("PatientIdentifier", PatientId);
//		if (isITI41) {
//			ActiveParticipant source = new ActiveParticipant();
//			source.setUserId(replyto);
//			source.setAccessPointId(remoteIP);
//
//			ActiveParticipant dest = new ActiveParticipant();
//			// TODO: Needs to be improved
//			String userid = endpoint;
//			dest.setUserId(userid);
//			dest.setAccessPointId(localIP);
//			auditLog.logDocumentImport(source, dest, patientObj, set, typeCode);
//		} else {
//			ActiveParticipant source = new ActiveParticipant();
//			source.setUserId(replyto);
//			source.setAccessPointId(localIP);
//
//			ActiveParticipant dest = new ActiveParticipant();
//			// dest.setUserId(registry_endpoint());
//			dest.setAccessPointId(localIP);
//			auditLog.logDocumentExport(source, dest, patientObj, set, typeCode);
//		}
//	}

}
