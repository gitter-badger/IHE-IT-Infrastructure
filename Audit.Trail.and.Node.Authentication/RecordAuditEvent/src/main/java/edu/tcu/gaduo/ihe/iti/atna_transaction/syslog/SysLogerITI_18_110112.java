package edu.tcu.gaduo.ihe.iti.atna_transaction.syslog;

import org.apache.axiom.om.OMElement;

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
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.ParticipantObjectDetailType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.ParticipantObjectIdentificationType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.ParticipantObjectNameType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424.ParticipantObjectQueryType;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;

public class SysLogerITI_18_110112 implements ISysLoger {
	private String endPoint;
	private String patientId;
	private EventOutcomeIndicator eventOutcomeIndicator;
	private String IPAddress;
	private String userID;
	

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public void setEventOutcomeIndicator(EventOutcomeIndicator eventOutcomeIndicator) {
		this.eventOutcomeIndicator = eventOutcomeIndicator;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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
		ActiveParticipantType source = new ActiveParticipantType(true, NetworkAccessPointTypeCode.IPAddress);
		/**The content of the <wsa:ReplyTo/> element. Opt = M*/
		source.setUserID(" **** ");
		/** The process ID as used within the local operating system in the local system logs. Opt = M */
		source.setAlternativeUserID(" **** ");
		/** The machine name or IP address, as specified in RFC 3881. Opt = M */
		source.setNetworkAccessPointID(IPAddress);
		/** EV(110153, DCM, “Source”) Opt = M */
		source.addRoleIDCode(new CodedValueType(RFC3881.RoleIDCode, "110153", CodeType.ParticipantRoleType));
		auditMsg.addActiveParticipant(source);
		/** --- Source --- */
		
		/** --- Destination --- */
		ActiveParticipantType destination = new ActiveParticipantType(false, NetworkAccessPointTypeCode.IPAddress);
		/** SOAP endpoint URI. Opt = M */
		destination.setUserID(endPoint);
		/** The machine name or IP address, as specified in RFC 3881. Opt = M */
		source.setNetworkAccessPointID(" **** ");
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
		registryStoredQuery.setParticipantObjectID(" ****** ");
		/** If known the value of <ihe:HomeCommunityId/> */
		registryStoredQuery.addParticipantObjectName(new ParticipantObjectNameType(" ****** "));
		/** the AdhocQueryRequest, base64 encoded. */
		registryStoredQuery.addParticipantObjectQuery(new ParticipantObjectQueryType(" ****** "));
		
		/**
		 *  In one element, set “QueryEncoding”as the value of the attribute type,
		 *  Set the attribute value to the character encoding, such as “UTF-8”, used
		 *  to encode the ParticipantObjectQuery before base64 encoding.
		 */
		registryStoredQuery.addParticipantObjectDetail(new ParticipantObjectDetailType("QueryEncoding", "UTF-8"));
		/**
		 * In another element, set “urn:ihe:iti:xca:2010:homeCommunityId” as the
		 * value of the attribute type and the value of the homeCommunityID as
		 * the value of the attribute value, if known.
		 */
		registryStoredQuery.addParticipantObjectDetail(new ParticipantObjectDetailType("urn:ihe:iti:xca:2010:homeCommunityId", " ****** "));
		auditMsg.addParticipantObjectIdentification(registryStoredQuery);
		/** --- QueryParameters --- */
		
		OMElement element = auditMsg.buildRFC3881();
		return element;
		
	}

}
