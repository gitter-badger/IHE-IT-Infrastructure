package com.gaduo.ihe.utility._interface;

import java.util.Iterator;

import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.Namespace;

public interface IAxiomUtil {
	public Iterator<OMElement> getChildrenWithName(OMElement request,
			Namespace namespace, String tag);

	public OMElement getFirstChildWithName(OMElement request,
			Namespace namespace, String tag);

	public OMElement createOMElement(EbXML ebxml, Namespace namespace);
	OMElement createOMElement(String tag, String value);
	public OMElement createOMElement(String tag, String namespace, String prefix);
	public String getValueOfType(String type, OMElement request) ;
	
	
}
