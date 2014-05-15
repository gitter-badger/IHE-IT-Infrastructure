package edu.tcu.gaduo.ihe.utility.ws._interface;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.context.MessageContext;

public interface ISoap {
	public MessageContext send(String data);
	public MessageContext send(OMElement data);
	public boolean isMTOM_XOP();
	public boolean isSWA();
}
