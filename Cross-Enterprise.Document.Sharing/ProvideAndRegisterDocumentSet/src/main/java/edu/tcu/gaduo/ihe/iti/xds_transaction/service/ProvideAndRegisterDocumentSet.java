package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.util.HashSet;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility.PnRCommon;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.webservice.ServiceConsumer;
import edu.tcu.gaduo.webservice.Soap;
import edu.tcu.gaduo.webservice.SoapWithAttachment;
import edu.tcu.gaduo.webservice._interface.ISoap;

public class ProvideAndRegisterDocumentSet extends Transaction {
	public static ISoap soap;
	private String filename;
	private String operations;
	public static boolean swa = !true;;
	public static Logger logger = Logger.getLogger(ProvideAndRegisterDocumentSet.class);
/*----------------*/
long timestamp;
/*----------------*/
	public ProvideAndRegisterDocumentSet() {
/*----------------*/
this.timestamp = System.currentTimeMillis();
/*----------------*/		
		initial();
	}

	private void initial() {
		c = new PnRCommon();
		PnRCommon.ObjectRef = new HashSet<String>();
		PnRCommon.count = 0;
	}

	public OMElement MetadataGenerator(OMElement source) {
		if (source == null)
			return null;
		logger.info("Beging Transaction");
		logger.debug(source);
		source.build();
		IAxiomUtil axiom = new AxiomUtil();
		initial();
		new PnRCommon(source);
		operations = axiom.getValueOfType("Operations", source);
		// ------ Loading Resource
		filename = new PnRCommon().createTime();
		// --
		c.saveLog(filename, ((PnRCommon) c).SOURCE + "_" + operations, source);
		MessageContext currentContext = MessageContext.getCurrentMessageContext();
		if (currentContext != null) {
			SOAPEnvelope envelope = currentContext.getEnvelope();
			c.saveLog(filename, "_ITI-41_envelope" + "_" + operations, envelope);
		}
		// -------submit ITI - 41 -------------------
		if (!PnRCommon.repositoryUrl.equals("")) {
			if(swa){
				/*Soap With Attachments but cannot connect with MS OpenXDS*/
				soap = new SoapWithAttachment(PnRCommon.repositoryUrl,
						"urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b");
				((SoapWithAttachment)soap).setSwa(true);
			}else{
				soap = new ServiceConsumer(PnRCommon.repositoryUrl,
						"urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b");
				((ServiceConsumer)soap).setMTOM_XOP(true);
			}
			/* Provide And Register Document Set -b */
			MetadataGenerator m = new MetadataGenerator();
			request = m.execution(source);
			logger.debug(request);
/*----------------*/
logger.info("\n***(1)Source:*** " + m.getSubmissionSet().getId() + " *** " + (System.currentTimeMillis() - timestamp));	
/*----------------*/			
			if (request != null) {
				timestamp = System.currentTimeMillis();
/*----------------*/
logger.info("\n###(I)ITI-41RequestBegin:### " + m.getSubmissionSet().getId() + " ### " + System.currentTimeMillis());
/*----------------*/
				response = send(request);
				if (response != null) {		
					logger.debug(response);
/*----------------*/
logger.info("\n###(VIII)ITI-41ResponseEnd:### " + m.getSubmissionSet().getId() + " ### " + System.currentTimeMillis());
logger.info("\n***(2)ITI-41:*** " + m.getSubmissionSet().getId() + " *** " + (System.currentTimeMillis() - timestamp));	
/*----------------*/	
					return response;
				}
			}
		}
				
		gc();
		logger.error("Response is null");
		return null;
		// -------submit ITI - 41 -------------------
	}

	@Override
	public OMElement send(OMElement request) {
		c.saveLog(filename, ((PnRCommon) c).ITI_41_REQUEST + "_" + operations, request);
		
		setContext(soap.send(request));		
		
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope() : null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		c.saveLog(filename, ((PnRCommon) c).ITI_41_RESPONSE + "_" + operations, response);
		gc();
		return response;
	}
}
