package com.gaduo.ihe.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.UUID;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.Node;

import com.gaduo.ihe.utility._interface.IAxiomUtil;
import com.gaduo.ihe.utility._interface.ICommon;
import com.gaduo.ihe.utility.xml.XMLPath;

public class Common implements ICommon {
	public static XMLPath config;
	public static XMLPath codes;
	public static XMLPath web;
	public static int count;
	public static String IP;
	public static String root_dir;
	private IAxiomUtil axiom ;
	public Common(){
		ClassLoader loader = getClass().getClassLoader();
		InputStream codesXml = loader.getResourceAsStream("codes.xml");
		InputStream webXml = loader.getResourceAsStream("web.xml");
		InputStream configXml = loader.getResourceAsStream("config.xml");
		Common.codes = new XMLPath(codesXml);
		Common.web = new XMLPath(webXml);
		Common.config = new XMLPath(configXml);
		setAxiom(new AxiomUtil());
		setRoot_dir();
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

	protected void setRoot_dir() {
		Node node = Common.config.QueryNode("Config/StoragePath/@value");
		Common.root_dir = (node != null) ? node.getTextContent() : "";
	}

	public IAxiomUtil getAxiom() {
		return axiom;
	}

	public void setAxiom(IAxiomUtil axiom) {
		this.axiom = axiom;
	}

}
