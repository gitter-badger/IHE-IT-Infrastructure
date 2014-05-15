package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.atna.EventOutcomeIndicator;
import edu.tcu.gaduo.ihe.iti.atna_transaction.service.RecordAuditEvent;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog.SysLogerITI_41_110106;
import edu.tcu.gaduo.ihe.iti.atna_transaction.syslog._interface.ISysLoger;
import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.DocumentType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.FolderType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.MetadataType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.PatientInfoType;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility.Common;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility.ws.ServiceConsumer;
import edu.tcu.gaduo.ihe.utility.ws.SoapWithAttachment;
import edu.tcu.gaduo.ihe.utility.ws._interface.ISoap;

public class ProvideAndRegisterDocumentSet extends Transaction {
	public static ISoap soap;
	private String filename;
	public static boolean swa = !true;;
	public static Logger logger = Logger.getLogger(ProvideAndRegisterDocumentSet.class);
	
	public final String SOURCE = "_41_source";
	public final String ITI_41_REQUEST = "_ITI-41_request";
	public final String ITI_41_RESPONSE = "_ITI-41_response";
	

	private final String ACTION = "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b";
	private String repositoryUrl = null;
	private MetadataType md;
	private EventOutcomeIndicator eventOutcomeIndicator;
	
/*----------------*/
long timestamp;
/*----------------*/

	public ProvideAndRegisterDocumentSet() {
/*----------------*/
this.timestamp = System.currentTimeMillis();
/*----------------*/		
	}

	private void initial() {
		logger.info("Beging Transaction");
		c = new Common();
		filename = createTime();
	}

	public OMElement MetadataGenerator(OMElement source) {
		if (source == null)
			return null;
		source.build();
		initial();
		logger.debug(source);
		IAxiomUtil axiom = AxiomUtil.getInstance();
		// ------ Loading Resource
		c.saveLog(filename, SOURCE, source);
		MessageContext currentContext = MessageContext.getCurrentMessageContext();
		if (currentContext != null) {
			SOAPEnvelope envelope = currentContext.getEnvelope();
			c.saveLog(filename, "_ITI-41_envelope", envelope);
		}
		if(repositoryUrl == null)
			repositoryUrl = axiom.getValueOfType("RepositoryUrl", source);
		// -------submit ITI - 41 -------------------
		if (!repositoryUrl.equals("")) {
			if(swa){
				/*Soap With Attachments but cannot connect with MS OpenXDS*/
				soap = new SoapWithAttachment(repositoryUrl, ACTION);
				((SoapWithAttachment)soap).setSwa(true);
			}else{
				soap = new ServiceConsumer(repositoryUrl, ACTION);
				((ServiceConsumer)soap).setMTOM_XOP(true);
			}
			/* Provide And Register Document Set -b */
			
			MetadataType md = null;
			String s = source.toString();
			InputStream is = new ByteArrayInputStream(s.getBytes());
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(MetadataType.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				md = (MetadataType) jaxbUnmarshaller.unmarshal(is);
				PatientInfoType pInfo = md.getPatientInfo();
				String sourcePatientId = md.getSourcePatientId();
				String sourceID = md.getSourceID();
				md.setSourceID(sourceID);
				Iterator<DocumentType> dIterator = md.getDocuments().getList().iterator();
				while(dIterator.hasNext()){
					DocumentType d = dIterator.next();
					d.setSourcePatientId(sourcePatientId);
					d.setPatientInfo(pInfo);
				}
				
				Iterator<FolderType> fIterator = md.getFolders().getList().iterator();
				while(fIterator.hasNext()){
					FolderType f = fIterator.next();
					f.setSourcePatientId(sourcePatientId);
					Iterator<DocumentType> _dIterator = f.getDocuments().getList().iterator();
					while(_dIterator.hasNext()){
						DocumentType d = _dIterator.next();
						d.setSourcePatientId(sourcePatientId);
						d.setPatientInfo(pInfo);
					}
				}

//				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//				jaxbMarshaller.marshal(md, System.out);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			if( md != null)
				return MetadataGenerator(md);
			else
				return null;
		}
				
		gc();
		logger.error("Response is null");
		return null;
		// -------submit ITI - 41 -------------------
	}
	

	public OMElement MetadataGenerator(MetadataType md){
		this.md = md;
		if(repositoryUrl == null)
			repositoryUrl = md.getRepositoryUrl();
		initial();
		OMElement source = md.marshal();
		c.saveLog(filename, SOURCE, source);
		
		if (!repositoryUrl.equals("")) {
			if(swa){
				/*Soap With Attachments but cannot connect with MS OpenXDS*/
				soap = new SoapWithAttachment(repositoryUrl, ACTION);
				((SoapWithAttachment)soap).setSwa(true);
			}else{
				soap = new ServiceConsumer(repositoryUrl, ACTION);
				((ServiceConsumer)soap).setMTOM_XOP(true);
			}
			/* Provide And Register Document Set -b */
			MetadataGenerator_2_0 m = new MetadataGenerator_2_0();
			request = m.execution(md);
			response = send(md);
			return response;
		}
		
		gc();
		logger.error("Response is null");
		return null;
	}
	
	public OMElement send(MetadataType md){
		logger.debug(request);
/*----------------*/
logger.info("\n***(1)Source:*** " + md.getId() + " *** " + (System.currentTimeMillis() - timestamp));	
/*----------------*/			
		if (request != null) {
			timestamp = System.currentTimeMillis();
/*----------------*/
logger.info("\n###(I)ITI-41RequestBegin:### " + md.getId() + " ### " + System.currentTimeMillis());
/*----------------*/
			response = send(request);
			

			auditLog() ;
			
			if (response != null) {		
				logger.debug(response);
/*----------------*/
logger.info("\n###(VIII)ITI-41ResponseEnd:### " + md.getId() + " ### " + System.currentTimeMillis());
logger.info("\n***(2)ITI-41:*** " + md.getId() + " *** " + (System.currentTimeMillis() - timestamp));	
/*----------------*/	
				return response;
			}
		}
		return null;
	}
	

	@Override
	public OMElement send(OMElement request) {
		logger.info(request);
		c.saveLog(filename, ITI_41_REQUEST, request);
		
		setContext(soap.send(request));		
		
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		c.saveLog(filename, ITI_41_RESPONSE, response);
		gc();
		return response;
	}
	
	public void setRepositoryUrl(String repositoryUrl){
		this.repositoryUrl = repositoryUrl;
	}
	
	public boolean assertEquals(OMElement response, String success){
		if(response.toString().equals(success))
			return true;
		return false;
	}

	@Override
	public void auditLog() {
		if (response == null) {
			this.eventOutcomeIndicator = EventOutcomeIndicator.MajorFailure;
		} else if (assertEquals(
				response,
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>")) {
			this.eventOutcomeIndicator = EventOutcomeIndicator.Success;
		} else {
			this.eventOutcomeIndicator = EventOutcomeIndicator.SeriousFaailure;
		}
		
		String patientId = md.getSourcePatientId();
		String XDSSubmissionSetUniqueId = md.getId();
		String endpoint = repositoryUrl;
		EventOutcomeIndicator eventOutcomeIndicator = this.eventOutcomeIndicator;
		
		
		ISysLoger loger = new SysLogerITI_41_110106();
		((SysLogerITI_41_110106) loger).setEndpoint(endpoint);
		((SysLogerITI_41_110106) loger).setPatientId(patientId);
		((SysLogerITI_41_110106) loger).setXDSSubmissionSetUniqueId(XDSSubmissionSetUniqueId);
		((SysLogerITI_41_110106) loger).setEventOutcomeIndicator(eventOutcomeIndicator);
		/** humanRequestor*/
		((SysLogerITI_41_110106) loger).setUserID("1");
		/** --- Source --- */
		((SysLogerITI_41_110106) loger).setReplyTo("http://www.w3.org/2005/08/addressing/anonymous");
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			((SysLogerITI_41_110106) loger).setLocalIPAddress(addr.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		OMElement element = loger.build();
		logger.info(element);
		
		RecordAuditEvent rae = new RecordAuditEvent();
		rae.AuditGenerator(element);
		
	}
}
