package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction.core.Transaction;
import edu.tcu.gaduo.ihe.iti.xds_transaction.gaduo_define.MetadataType;
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
			MetadataGenerator_2_0 m = new MetadataGenerator_2_0();
			request = m.execution(source);
			response = send(m.getMetaData());
			return response;
		}
				
		gc();
		logger.error("Response is null");
		return null;
		// -------submit ITI - 41 -------------------
	}
	

	public OMElement MetadataGenerator(MetadataType md){
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
}
