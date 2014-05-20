package edu.tcu.gaduo.ihe.utility.ws._interface;

import java.io.InputStream;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.context.MessageContext;

public interface ISoap {
	public MessageContext send(String data);
	public MessageContext send(OMElement data);
	public MessageContext send(InputStream is);
	public boolean isMTOM_XOP();
	public boolean isSWA();
}
