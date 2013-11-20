package com.gaduo.ihe.it_infrastructure.xds_transaction.service;

import java.util.HashSet;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

import com.gaduo.ihe.it_infrastructure.xds_transaction.service._interface.XDSTransaction;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.ihe.utility.PnRCommon;
import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility.webservice.nonblock.NonBlockCallBack;
import com.gaduo.webservice.ServiceConsumer;
import com.gaduo.webservice._interface.ISoap;

public class ProvideAndRegisterDocumentSet extends XDSTransaction {
	public static ISoap soap;
	private String filename;
	private String Operations;

	public static Logger logger = Logger
			.getLogger(ProvideAndRegisterDocumentSet.class);

	public ProvideAndRegisterDocumentSet() {
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
		logger.info(source);
		source.build();
		IAxiomUtil axiom = new AxiomUtil();
		initial();
		new PnRCommon(source);
		Operations = axiom.getValueOfType("Operations", source);
		// ------ Loading Resource
		filename = new PnRCommon().createTime();
		// --
		c.saveLog(filename, ((PnRCommon) c).SOURCE + "_" + Operations, source);
		MessageContext currentContext = MessageContext
				.getCurrentMessageContext();
		if (currentContext != null) {
			SOAPEnvelope envelope = currentContext.getEnvelope();
			c.saveLog(filename, "_ITI-41_envelope" + "_" + Operations, envelope);
		}
		// -------submit ITI - 41 -------------------
		if (!PnRCommon.repositoryUrl.equals("")) {
			MetadataGenerator m = new MetadataGenerator();
			/* Provide And Register Document Set -b */
			request = m.execution(source);
			logger.info(request);
			if (request != null) {
				response = send(request);
				if (response != null) {		
					logger.info(response);
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
		// boolean MTOM_XOP = true;
		// soap = new Soap(PnRCommon.repositoryUrl,
		// "http://www.w3.org/2005/08/addressing",
		// "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b",
		// MTOM_XOP);
		c.saveLog(filename, ((PnRCommon) c).ITI_41_REQUEST + "_" + Operations,
				request);
		// context = soap.send(request);

		ServiceConsumer serviceConsumer = new ServiceConsumer(
				PnRCommon.repositoryUrl, request,
				"http://www.w3.org/2005/08/addressing",
				"urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b", true, true);
		NonBlockCallBack callback = serviceConsumer.getCallback();
		setContext(callback.getContext());
		SOAPEnvelope envelope = (context != null) ? context.getEnvelope()
				: null;
		SOAPBody body = (envelope != null) ? envelope.getBody() : null;
		OMElement response = (body != null) ? body.getFirstElement() : null;
		c.saveLog(filename, ((PnRCommon) c).ITI_41_RESPONSE + "_" + Operations,
				response);
		gc();
		return response;
	}
}
