package edu.tcu.gaduo.ihe.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.UUID;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.ICommon;
import edu.tcu.gaduo.ihe.utility.xml.XMLPath;

public class Common implements ICommon {
	public static Logger logger = Logger.getLogger(Common.class);
	@Deprecated
	public static XMLPath codes;
	@Deprecated
	public static XMLPath web;
	@Deprecated
	public static int count;
	@Deprecated
	public static String IP;
	
	private String root_dir;
	private IAxiomUtil axiom ;
	public Common(){
		root_dir = System.getProperty("user.home");
		axiom = AxiomUtil.getInstance();
	}

	public String createTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}

	public void saveLog(String filename, String postfix, OMElement message) {
		File file = new File(root_dir + "/Metadata/");
		if (!file.exists()) {
			file.mkdirs();
		}
		String log = null;
		try {
			log = message.toString();
		} catch(java.lang.NullPointerException e){
			logger.error(e.toString() + "\t" + e.getLocalizedMessage());
		}
		if (log != null) {
			logger.info(log);
			try {
				String output = root_dir + "/Metadata/" + filename + postfix + ".xml";
				synchronized(output){
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output, false), "utf8"));
					bw.write(log);
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String createUUID() {
		UUID uid = UUID.randomUUID();
		return "urn:uuid:" + uid.toString();
	}


	public IAxiomUtil getAxiom() {
		return axiom;
	}

	public void setAxiom(IAxiomUtil axiom) {
		this.axiom = axiom;
	}

}
