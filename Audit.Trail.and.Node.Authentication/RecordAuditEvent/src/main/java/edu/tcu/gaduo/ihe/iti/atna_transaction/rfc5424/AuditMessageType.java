package edu.tcu.gaduo.ihe.iti.atna_transaction.rfc5424;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.atna.ETransaction;
import edu.tcu.gaduo.ihe.constants.atna.EventActionCode;
import edu.tcu.gaduo.ihe.constants.atna.RFC3881;

/**
 * @author Gaduo
 *
 */
public class AuditMessageType extends General{

	public static Logger logger = Logger.getLogger(AuditMessageType.class);
	
	protected EventIdentificationType eventIdentification; // [1 ... 1]
	protected List<ActiveParticipantType> activeParticipants; // [1 ... *]
	protected List<AuditSourceIdentificationType> auditSourceIdentifications; // [1 ... *]
	protected List<ParticipantObjectIdentificationType> participantObjectIdentifications; // [0 ... *]
	
	private ETransaction eTransaction;
	
	public AuditMessageType(ETransaction eTransaction){
		activeParticipants = new ArrayList<ActiveParticipantType>();
		auditSourceIdentifications = new ArrayList<AuditSourceIdentificationType>();
		participantObjectIdentifications = new ArrayList<ParticipantObjectIdentificationType>();
		
		this.eTransaction = eTransaction;
	}
	
	public void setEventIdentification(EventIdentificationType eventIdentification){
		this.eventIdentification = eventIdentification;
		CodedValueType eventID = eTransaction.getEventID();
		CodedValueType eventTypeCode = eTransaction.getEventTypeCode();
		EventActionCode eventActionCode = eTransaction.getEventActionCode();
		this.eventIdentification.eventActionCode = eventActionCode.getValue();
		this.eventIdentification.eventID = eventID;
		this.eventIdentification.eventTypeCode.add(eventTypeCode);
	}
	
	public void addActiveParticipant(ActiveParticipantType a){
		activeParticipants.add(a);
	}
	
	public void addAuditSourceIdentification(AuditSourceIdentificationType a){
		auditSourceIdentifications.add(a);
	}
	
	public void addParticipantObjectIdentification(ParticipantObjectIdentificationType p){
		participantObjectIdentifications.add(p);
	}
	
	@Override
	public OMElement buildRFC3881() {
		OMNamespace namespace = null;
		OMElement root = axiom.createOMElement(RFC3881.AuditMessage.getTag(), namespace);
		
		if(eventIdentification != null){
			OMElement element = eventIdentification.buildRFC3881();
			if(element != null)
				root.addChild(element);
		}		
		
		if(activeParticipants != null){
			Iterator<ActiveParticipantType> iterator = activeParticipants.iterator();
			while(iterator.hasNext()){
				ActiveParticipantType next = iterator.next();
				OMElement element = next.buildRFC3881();
				logger.info(element);
				if(element != null)
					root.addChild(element);
			}
		}
		
		if(auditSourceIdentifications != null){
			Iterator<AuditSourceIdentificationType> iterator = auditSourceIdentifications.iterator();
			while(iterator.hasNext()){
				AuditSourceIdentificationType next = iterator.next();
				OMElement element = next.buildRFC3881();
				if(element != null)
					root.addChild(element);
			}
		}
		
		if(participantObjectIdentifications != null){
			Iterator<ParticipantObjectIdentificationType> iterator = participantObjectIdentifications.iterator();
			while(iterator.hasNext()){
				ParticipantObjectIdentificationType next = iterator.next();
				OMElement element = next.buildRFC3881();
				if(element != null)
					root.addChild(element);
			}
		}
		return root;
	}

}
