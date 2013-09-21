package com.gaduo.ihe.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import com.gaduo.ihe.constants.EbXML;
import com.gaduo.ihe.constants.Namespace;
import com.gaduo.ihe.utility._interface.ICommon;
import com.gaduo.ihe.utility.xml.XMLPath;

public class Common implements ICommon {
	public static XMLPath config;
	public static XMLPath codes;
	public static XMLPath web;
	public static int count;
	public static String IP;
	public static String root_dir;

	@SuppressWarnings("unchecked")
	public Iterator<OMElement> getChildrenWithName(OMElement request,
			Namespace namespace, String tag) {
		Iterator<OMElement> element = null;
		QName qname = null;
		if (namespace != null) {
			qname = new QName(namespace.getNamespace(), tag,
					namespace.getPrefix());
			element = request.getChildrenWithName(qname);
			if (element == null) {
				qname = new QName(namespace.getNamespace(), tag);
				element = request.getChildrenWithName(qname);
			}
		}
		if (element == null) {
			qname = new QName(tag);
			element = request.getChildrenWithName(qname);
		}
		return element;
	}

	public OMElement getFirstChildWithName(OMElement request,
			Namespace namespace, String tag) {
		OMElement element = null;
		QName qname = null;
		if (namespace != null) {
			qname = new QName(namespace.getNamespace(), tag,
					namespace.getPrefix());
			element = request.getFirstChildWithName(qname);
			if (element == null) {
				qname = new QName(namespace.getNamespace(), tag);
				element = request.getFirstChildWithName(qname);
			}
		}
		if (element == null) {
			qname = new QName(tag);
			element = request.getFirstChildWithName(qname);
		}
		return element;
	}

	public OMElement createOMElement(EbXML ebxml, Namespace namespace) {
		OMElement element;
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace Namespace = null;
		if (namespace != null) {
			Namespace = factory.createOMNamespace(namespace.getNamespace(),
					namespace.getPrefix());
			element = factory.createOMElement(ebxml.getTag(), Namespace);
		} else {
			QName qname = new QName(ebxml.getTag());
			element = factory.createOMElement(qname);
		}
		return element;
	}

	public OMElement createOMElement(String tag, String namespace, String prefix) {
		OMElement element;
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace Namespace = null;
		if (namespace != null) {
			Namespace = factory.createOMNamespace(namespace, prefix);
			element = factory.createOMElement(tag, Namespace);
		} else {
			element = factory.createOMElement(tag, null);
		}
		return element;
	}

	  
    public OMElement createOMElement(String tag, String value) { 
        OMFactory factory = OMAbstractFactory.getOMFactory(); 
        OMElement element = factory.createOMElement(tag, null); 
        if (value != null) { 
            element.setText(value); 
        } 
        return element; 
    } 
    
	public String getValueOfType(String type, OMElement request) {
		String value;
		QName qname = new QName(type);
		OMElement e = request.getFirstChildWithName(qname);
		value = (e != null) ? e.getText() : null;
		return value;
	}

	public String createTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}

	public void saveLog(String filename, String postfix, OMElement response) {
		File file = new File(root_dir + "/Metadata/");
		if (!file.exists()) {
			file.mkdirs();
		}
		if (response != null) {
			try {
				String output = root_dir + "/Metadata/" + filename
						+ postfix + ".xml";
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(output, false), "utf8"));
				bw.write(response.toString());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String createUUID() {
		UUID uid = UUID.randomUUID();
		return "urn:uuid:" + uid.toString();
	}

}
