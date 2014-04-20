package edu.tcu.gaduo.ihe.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.UUID;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.ICommon;
import edu.tcu.gaduo.ihe.utility.xml.XMLPath;

public class Common implements ICommon {
	@Deprecated
	public static XMLPath codes;
	@Deprecated
	public static XMLPath web;
	@Deprecated
	public static int count;
	@Deprecated
	public static String IP;
	@Deprecated
	private ClassLoader loader;
	@Deprecated
	private Properties prop;
	
	private String root_dir;
	private IAxiomUtil axiom ;
	private String islogging = "false";
	public Common(){
		axiom = AxiomUtil.getInstance();
	}

	public String createTime() {
		java.util.Date date = new java.util.Date();
		String value = new Timestamp(date.getTime()).toString();
		value = value.replaceAll("\\D+", "").substring(0, 14);
		return value;
	}

	public void saveLog(String filename, String postfix, OMElement log) {
		if(islogging.equalsIgnoreCase("true")){
			File file = new File(root_dir + "/Metadata/");
			if (!file.exists()) {
				file.mkdirs();
			}
			if (log != null) {
				try {
					String output = root_dir + "/Metadata/" + filename + postfix + ".xml";
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output, false), "utf8"));
					bw.write(log.toString());
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
