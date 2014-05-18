package edu.tcu.gaduo.ihe.iti.atna_transaction.syslog;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.axiom.om.OMElement;
import org.apache.commons.codec.binary.Base64;

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
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.ParticipantObjectNameType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc3881.ParticipantObjectQueryType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;

public class SysLogerITI_18_110112 implements ISysLoger {
	private String endPoint;
	private String patientId;
	private EventOutcomeIndicator eventOutcomeIndicator;
	private String localIPAddress;
	private String replyTo;
	private String processId;
	private String homeCommunityId;
	private String request;
	private String queryUUID;

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public void setEventOutcomeIndicator(EventOutcomeIndicator eventOutcomeIndicator) {
		this.eventOutcomeIndicator = eventOutcomeIndicator;
	}

	/**
	 * @param iPAddress the iPAddress to set
	 */
	public void setLocalIPAddress(String iPAddress) {
		localIPAddress = iPAddress;
	}

	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	

	/**
	 * @param queryUUID the queryUUID to set
	 */
	public void setQueryUUID(String queryUUID) {
		this.queryUUID = queryUUID;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(OMElement request) {
		
		byte[] b = Base64.encodeBase64(request.toString().getBytes());
		try {
			this.request = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	

	public OMElement build() {
		AuditMessageType auditMsg = new AuditMessageType(ETransaction.ITI_18_110112);
		/** --- Event --- */
		/** EventID = EV(110112, DCM, “Query”), Opt = M */
		/** EventTypeCode = EV(“ITI-18”, “IHE Transactions”, “Registry Stored Query”), Opt = M */
		/** “E” (Execute), Opt = M */
		/** Opt = M */
		EventIdentificationType event = new EventIdentificationType(eventOutcomeIndicator);
		auditMsg.setEventIdentification(event);
		/** --- Event --- */

		/** --- Source --- */
		ActiveParticipantType source = new ActiveParticipantType(true);
		/**The content of the <wsa:ReplyTo/> element. Opt = M*/
		source.setUserID(replyTo);
		/** The process ID as used within the local operating system in the local system logs. Opt = M */
		processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		source.setAlternativeUserID(processId);
		/** The machine name or IP address, as specified in RFC 3881. Opt = M */
		source.setNetworkAccessPoint(localIPAddress);
		/** EV(110153, DCM, “Source”) Opt = M */
		source.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110153", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(source);
		/** --- Source --- */
		
		/** --- Destination --- */
		URL url = null;		
		try {
			url = new URL(endPoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String host = url.getHost();
		ActiveParticipantType destination = new ActiveParticipantType(false);
		/** SOAP endpoint URI. Opt = M */
		destination.setUserID(endPoint);
		/** The machine name or IP address, as specified in RFC 3881. Opt = M */
		destination.setNetworkAccessPoint(host);
		/** EV(110152, DCM, “Destination”), Opt = M */
		destination.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110152", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(destination);
		/** --- Destination --- */
		
		/** --- Audit Source --- */
		AuditSourceIdentificationType auditSource = new AuditSourceIdentificationType(AuditSourceTypeCode.One);
		auditSource.setAuditSourceID(" *** ");
		auditMsg.addAuditSourceIdentification(auditSource);
		/** --- Audit Source --- */
		
		/** --- Patient --- */
		if(patientId != null){
			/** ParticipantObjectTypeCode = “1” (Person), Opt = M */
			/** ParticipantObjectTypeCodeRole = “1” (Patient), Opt = M */
			ParticipantObjectIdentificationType patient = new ParticipantObjectIdentificationType(ParticipantObjectTypeCode.Person, ParticipantObjectTypeCodeRole.Patient, null, null);
			/** The patient ID in HL7 CX format. Opt = M */
			patient.setParticipantObjectID(patientId);
			/** EV(2, RFC-3881, “Patient Number”), Opt = M */
			patient.setParticipantObjectIDTypeCode(ParticipantObjectIDTypeCode.PatientNumber);
			auditMsg.addParticipantObjectIdentification(patient);
		}
		/** --- Patient --- */
		
		/** --- QueryParameters --- */
		/** ParticipantObjectTypeCode = “2” (system object), Opt = M */
		/** ParticipantObjectTypeCodeRole = “24” (query), Opt = M */
		ParticipantObjectIdentificationType registryStoredQuery = new ParticipantObjectIdentificationType(ParticipantObjectTypeCode.SystemObject, ParticipantObjectTypeCodeRole.Query, null, null);
		/** EV(“ITI-18”, “IHE Transactions”, “Registry Stored Query”) */
		registryStoredQuery.setParticipantObjectIDTypeCode(new CodedValueType(RFC3881.ParticipantObjectIDTypeCode, "ITI-18", CodeType.EventType));
		/** Stored Query ID (UUID) */
		registryStoredQuery.setParticipantObjectID(queryUUID);
		if(homeCommunityId != null){
			/** If known the value of <ihe:HomeCommunityId/> */
			registryStoredQuery.addParticipantObjectName(new ParticipantObjectNameType(homeCommunityId));
		}
		/** the AdhocQueryRequest, base64 encoded. */
		registryStoredQuery.addParticipantObjectQuery(new ParticipantObjectQueryType(request));
		
		/**
		 *  In one element, set “QueryEncoding”as the value of the attribute type,
		 *  Set the attribute value to the character encoding, such as “UTF-8”, used
		 *  to encode the ParticipantObjectQuery before base64 encoding.
		 */
		registryStoredQuery.addParticipantObjectDetail(new ParticipantObjectDetailType("QueryEncoding", "UTF-8"));
		
		if(homeCommunityId != null){
			/**
			 * In another element, set “urn:ihe:iti:xca:2010:homeCommunityId” as the
			 * value of the attribute type and the value of the homeCommunityID as
			 * the value of the attribute value, if known.
			 */
			registryStoredQuery.addParticipantObjectDetail(new ParticipantObjectDetailType("urn:ihe:iti:xca:2010:homeCommunityId", homeCommunityId));
		}
		auditMsg.addParticipantObjectIdentification(registryStoredQuery);
		/** --- QueryParameters --- */
		
		OMElement element = auditMsg.buildRFC3881();
		return element;
		
	}

}
