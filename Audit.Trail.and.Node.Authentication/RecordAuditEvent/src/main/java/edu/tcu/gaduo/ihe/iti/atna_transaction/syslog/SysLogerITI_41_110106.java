package edu.tcu.gaduo.ihe.iti.atna_transaction.syslog;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.atna.AuditSourceTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.CodeType;
import edu.tcu.gaduo.ihe.constants.atna.ETransaction;
import edu.tcu.gaduo.ihe.constants.atna.EventOutcomeIndicator;
import edu.tcu.gaduo.ihe.constants.atna.ParticipantObjectIDTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.ParticipantObjectTypeCode;
import edu.tcu.gaduo.ihe.constants.atna.ParticipantObjectTypeCodeRole;
import edu.tcu.gaduo.ihe.constants.atna.RFC3881;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.ActiveParticipantType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.AuditMessageType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.AuditSourceIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.CodedValueType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.EventIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.ParticipantObjectIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;

public class SysLogerITI_41_110106 implements ISysLoger{

	private String endpoint;
	private String patientId;
	private String XDSSubmissionSetUniqueId;
	private String localIPAddress;
	private String userID;
	private EventOutcomeIndicator eventOutcomeIndicator;
	private String replyTo;
	private String processId;
	
	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endPoint) {
		this.endpoint = endPoint;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * @param xDSSubmissionSetUniqueId the xDSSubmissionSetUniqueId to set
	 */
	public void setXDSSubmissionSetUniqueId(String xDSSubmissionSetUniqueId) {
		XDSSubmissionSetUniqueId = xDSSubmissionSetUniqueId;
	}

	/**
	 * @param iPAddress the iPAddress to set
	 */
	public void setLocalIPAddress(String iPAddress) {
		localIPAddress = iPAddress;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @param eventOutcomeIndicator the eventOutcomeIndicator to set
	 */
	public void setEventOutcomeIndicator(EventOutcomeIndicator eventOutcomeIndicator) {
		this.eventOutcomeIndicator = eventOutcomeIndicator;
	}
	

	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public OMElement build(){
		
		AuditMessageType auditMsg = new AuditMessageType(ETransaction.ITI_41_110106);
		/** --- Event --- */
		/** EventID = EV(110106, DCM, “Export”), Opt=M */
		/** EventTypeCode = EV(“ITI-41”, “IHE Transactions”, “Provider And Registry Document Set”), Opt=M */
		/** “E” (Execute), Opt=M */
		/** Opt=M */
		EventIdentificationType event = new EventIdentificationType(eventOutcomeIndicator);
		auditMsg.setEventIdentification(event);
		/** --- Event --- */

		/** --- Source --- */
		ActiveParticipantType source = new ActiveParticipantType(true);
		/** The content of the <wsa:ReplyTo/> element */
		source.setUserID(replyTo);
		/** the process ID as used within the local operating system in the local system logs. */
		processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		source.setAlternativeUserID(processId);
		source.setNetworkAccessPoint(localIPAddress);
		/** EV(110153, DCM, “Source”), Opt=M */
		source.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110153", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(source);
		/** --- Source --- */
		
		/** --- Human Requestor --- */
		ActiveParticipantType humanRequestor = new ActiveParticipantType(true);
		/** Identity of the human that initiated the transaction */
		humanRequestor.setUserID(userID);
		/** Access Control role(s) the user holds that allows this transaction. */
//		humanRequestor.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110153", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(humanRequestor);
		/** --- Human Requestor --- */
		
		/** --- Destination --- */
		URL url = null;		
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String host = url.getHost();
		
		ActiveParticipantType destination = new ActiveParticipantType(false);
		/** SOAP endpoint URI. */
		destination.setUserID(endpoint);
		/** The machine name or IP address, as specified in RFC 3881. */
		destination.setNetworkAccessPoint(host);
		/** EV(110152, DCM, “Destination”), Opt=M */
		destination.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110152", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(destination);
		/** --- Destination --- */
		
		/** --- Audit Source --- */
		AuditSourceIdentificationType auditSource = new AuditSourceIdentificationType(AuditSourceTypeCode.One);
		auditSource.setAuditSourceID(" + + + + + + ");
		auditMsg.addAuditSourceIdentification(auditSource);
		/** --- Audit Source --- */
		
		/** --- Patient --- */
		/** ParticipantObjectTypeCode = “1” (Person) */
		/** ParticipantObjectTypeCodeRole = “1” (Patient) */
		ParticipantObjectIdentificationType patient = new ParticipantObjectIdentificationType(ParticipantObjectTypeCode.Person, ParticipantObjectTypeCodeRole.Patient, null, null);
		/** EV(2, RFC-3881, “Patient Number”) */
		patient.setParticipantObjectIDTypeCode(ParticipantObjectIDTypeCode.PatientNumber);
		/** The patient ID in HL7 CX format. */
		patient.setParticipantObjectID(patientId);
		auditMsg.addParticipantObjectIdentification(patient);
		/** --- Patient --- */
		
		/** --- SubmissionSet --- */
		/** ParticipantObjectTypeCode = “2” (System) */
		/** ParticipantObjectTypeCodeRole = “20” (job) */
		ParticipantObjectIdentificationType submissionSet = new ParticipantObjectIdentificationType(ParticipantObjectTypeCode.SystemObject, ParticipantObjectTypeCodeRole.Job, null, null);
		/** EV(“urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd”, “IHE XDS Metadata”, “submission set classificationNode”) */
		submissionSet.setParticipantObjectIDTypeCode(ParticipantObjectIDTypeCode.SubmissionSetClassificationNode);
		/** The submissionSet unique ID */
		submissionSet.setParticipantObjectID(XDSSubmissionSetUniqueId);
		auditMsg.addParticipantObjectIdentification(submissionSet);
		/** --- SubmissionSet --- */
		
		OMElement element = auditMsg.buildRFC3881();
		return element;
	}
	
}
