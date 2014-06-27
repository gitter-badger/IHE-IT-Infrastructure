package edu.tcu.gaduo.ihe.iti.atna_transaction.syslog;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.ParticipantObjectDetailType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.ParticipantObjectIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;

public class SysLogerITI_43_110106 implements ISysLoger{

	private EventOutcomeIndicator eventOutcomeIndicator;
	private String endpoint;
	private String patientId;
	
	
	private String userID;
	private String localIPAddress;
	private String replyTo;
	
	private List<Document> documents;
	

	
	public SysLogerITI_43_110106(){
		replyTo = "http://www.w3.org/2005/08/addressing/anonymous";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			localIPAddress = addr.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.documents = new ArrayList<Document>();
	}
	
	
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
	
	/**
	 * @param documentUniqueId
	 * @param repositoryId
	 * @param homeCommunityId
	 */
	public void addDocument(String documentUniqueId, String repositoryId, String homeCommunityId){
		documents.add(new Document(documentUniqueId, repositoryId, homeCommunityId));
	}

	public OMElement build(){
		
		AuditMessageType auditMsg = new AuditMessageType(ETransaction.ITI_43_110107);
		/** --- Event --- */
		/** EventID = EV(110107, DCM, “Export”), Opt=M */
		/** EventTypeCode = EV(“ITI-43”, “IHE Transactions”, “Retrieve Document Set”), Opt=M */
		/** “E” (Execute), Opt=M */
		/** Opt=M */
		EventIdentificationType event = new EventIdentificationType(eventOutcomeIndicator);
		auditMsg.setEventIdentification(event);
		/** --- Event --- */

		/** --- Source --- */
		URL url = null;		
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String host = url.getHost();
		
		ActiveParticipantType source = new ActiveParticipantType(false);
		/** SOAP endpoint URI */
		source.setUserID(endpoint);
		/** The machine name or IP address, as specified in RFC 3881. */
		source.setNetworkAccessPoint(host);
		/** EV(110153, DCM, “Destination”), Opt=M */
		source.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110153", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(source);
		/** --- Source --- */
	
		
		/** --- Destination --- */
		ActiveParticipantType destination = new ActiveParticipantType(true);
		/** The content of the <wsa:ReplyTo/> element. */
		destination.setUserID(replyTo);
		/** the process ID as used within the local operating system in the local system logs. */
		String processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		destination.setAlternativeUserID(processId);
		destination.setNetworkAccessPoint(localIPAddress);
		/** EV(110152, DCM, “Source”), Opt=M */
		destination.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110152", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(destination);
		/** --- Destination --- */
				
		/** --- Human Requestor (if known) --- */
		ActiveParticipantType humanRequestor = new ActiveParticipantType(true);
		/** Identity of the human that initiated the transaction */
		humanRequestor.setUserID(userID);
		/** Access Control role(s) the user holds that allows this transaction. */
//		humanRequestor.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110153", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(humanRequestor);
		/** --- Human Requestor --- */
		
		/** --- Audit Source --- */
		AuditSourceIdentificationType auditSource = new AuditSourceIdentificationType(AuditSourceTypeCode.One);
		auditSource.setAuditSourceID(" ***** ");
		auditMsg.addAuditSourceIdentification(auditSource);
		/** --- Audit Source --- */
		
		/** --- Patient (if known) --- */
		/** ParticipantObjectTypeCode = “1” (Person), Opt=M */
		/** ParticipantObjectTypeCodeRole = “1” (Patient), Opt=M */
		ParticipantObjectIdentificationType patient = new ParticipantObjectIdentificationType(ParticipantObjectTypeCode.Person, ParticipantObjectTypeCodeRole.Patient, null, null);
		/** EV(2, RFC-3881, “Patient Number”) */
		patient.setParticipantObjectIDTypeCode(ParticipantObjectIDTypeCode.PatientNumber);
		/** The patient ID in HL7 CX format. */
		patient.setParticipantObjectID(patientId);
		auditMsg.addParticipantObjectIdentification(patient);
		/** --- Patient --- */
		
		
		Iterator<Document> iterator = documents.iterator();
		while(iterator.hasNext()){
			Document doc =iterator.next();
			/** --- Document --- */
			/** ParticipantObjectTypeCode = “2” (System), Opt=M */
			/** ParticipantObjectTypeCodeRole = “3” (Report), Opt=M */
			ParticipantObjectIdentificationType document = new ParticipantObjectIdentificationType(ParticipantObjectTypeCode.SystemObject, ParticipantObjectTypeCodeRole.Report, null, null);
			/** EV(“9”, “RFC-3881”, “Report Nunber”), Opt=M */
			document.setParticipantObjectIDTypeCode(ParticipantObjectIDTypeCode.ReportNunber);
			/** The value of <ihe:DocumentUniqueId />, Opt=M */
			document.setParticipantObjectID(doc.documentUniqueId);
			document.addParticipantObjectDetail(new ParticipantObjectDetailType("Repository Unique Id", doc.repositoryId));
			if(doc.homeCommunityId != null && !doc.homeCommunityId.equals(""))
				document.addParticipantObjectDetail(new ParticipantObjectDetailType("ihe:homeCommunityID", doc.homeCommunityId));
			
			auditMsg.addParticipantObjectIdentification(document);
			/** --- Document --- */
		}
		
		OMElement element = auditMsg.buildRFC3881();
		return element;
	}
	
	public class Document{
		protected String documentUniqueId;
		protected String repositoryId;
		protected String homeCommunityId;
		
		public Document(String documentUniqueId, String repositoryId, String homeCommunityId){
			this.documentUniqueId = documentUniqueId;
			this.repositoryId = repositoryId;
			this.homeCommunityId = homeCommunityId;
		}
	}
	
}
