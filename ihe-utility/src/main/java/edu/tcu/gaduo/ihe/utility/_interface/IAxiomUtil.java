package edu.tcu.gaduo.ihe.utility._interface;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;

public interface IAxiomUtil {
	public Iterator<OMElement> getChildrenWithName(OMElement request, Namespace namespace, String tag);

	public OMElement getFirstChildWithName(OMElement request, Namespace namespace, String tag);

	public OMElement createOMElement(EbXML ebxml, Namespace namespace);
	public OMElement createOMElement(String tag, OMNamespace namespace);
	public OMElement createOMElement(String tag, String value);
	public OMElement createOMElement(String tag, String namespace, String prefix);
	public OMNamespace createNamespace(String namespace, String prefix);
	public String getValueOfType(String type, OMElement request) ;
	
	public OMElement resourcesToOMElement(String resources);
	public OMElement resourcesToOMElement(InputStream is);
	public byte[] resourcesToByteArray(String resources);
	
}
