package com.gaduo.ihe.it_infrastructure.xds_transaction.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.Namespace;
import com.gaduo.ihe.utility.RSQCommon;

public class StoredQuery {

	/* AdhocQuery */
	private OMElement AdhocQuery;
	private HashSet<OMElement> Parameters;

	private String homeCommunityId;
	private RSQCommon common = null;
	
	protected TreeMap<String, String> ParameterSet = new TreeMap<String, String>(); 
	

	public StoredQuery(String UUID) {
		this.common = new RSQCommon();
		AdhocQuery = common.createOMElement(EbXML.AdhocQuery, Namespace.RIM3);
		AdhocQuery.addAttribute("id", UUID, null);
		Parameters = new HashSet<OMElement>();
		this.setParameterSet();
	}

	protected HashSet<OMElement> getParameters() {
		return Parameters;
	}
	
	protected void addParameters(OMElement element) {
		Parameters.add(element);
	}

	public OMElement getAdhocQuery() {
		Iterator<OMElement> iterator = Parameters.iterator();
		while (iterator.hasNext()) {
			OMElement slot = iterator.next();
			AdhocQuery.addChild(slot);
		}
		return AdhocQuery;
	}

	protected void setAdhocQuery(OMElement adhocQuery) {
		AdhocQuery = adhocQuery;
	}

	protected String getHomeCommunityId() {
		return homeCommunityId;
	}

	protected String checkForSingleQuote() {
		return null;
	}

	protected OMElement addSlot(OMElement reqest) {
		String name = reqest.getAttributeValue(new QName("name"));
		OMElement Slot = common.createOMElement(EbXML.Slot, Namespace.RIM3);
		Slot.addAttribute("name", name, null);
		OMElement ValueList = common.createOMElement(EbXML.ValueList,
				Namespace.RIM3);
		@SuppressWarnings("unchecked")
		Iterator<OMElement> children = reqest.getChildElements();
		while (children.hasNext()) {
			OMElement child = (OMElement)children.next();
			String temp = child.getText();
			OMElement Value = common.createOMElement(EbXML.Value,
					Namespace.RIM3);
			Value.setText(transform(temp, reqest));
			ValueList.addChild(Value);
		}
		Slot.addChild(ValueList);
		return Slot;
	}

	protected boolean isContainParameter(String p){
		return ParameterSet.keySet().contains(p);
	}
	
	protected void setParameterSet(){
		
	}
	
	private String transform(String temp, OMElement reqest) {
		if (temp.equalsIgnoreCase("APPROVED")) {
			return "(\'" + Namespace.APPROVED.getNamespace() + "\')";
		}
		if (temp.equalsIgnoreCase("DEPRECATED")) {
			return "(\'" + Namespace.DEPRECATED.getNamespace() + "\')";
		}
		if (temp.equalsIgnoreCase("SUBMITTED")) {
			return "(\'" + Namespace.SUBMITTED.getNamespace() + "\')";
		} else {
			String[] array = ParameterSet.get(reqest.getAttributeValue(new QName("name"))).split(",");
			if(array[1].equals("M"))
				return "(" + temp + ")";
			return temp;
		}
	}
	

}
