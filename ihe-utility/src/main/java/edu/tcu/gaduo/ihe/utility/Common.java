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
	public static XMLPath codes;
	public static XMLPath web;
	public static int count;
	public static String IP;
	private String root_dir;
	private IAxiomUtil axiom ;
	
	private ClassLoader loader;
	private Properties prop;
	private String islogging = "false";
	public Common(){
		loader = getClass().getClassLoader();
		prop = new Properties();
		
		InputStream codesXml = loader.getResourceAsStream("codes.xml");
		InputStream webXml = loader.getResourceAsStream("web.xml");
		Common.codes = new XMLPath(codesXml);
		Common.web = new XMLPath(webXml);

		String userHome = System.getProperty("user.home");
    	
		try {
			InputStream is = loader.getResourceAsStream("ihe-client.properties");
			prop.load(is);
			root_dir = userHome + "/" + prop.getProperty("storage.path").trim();
			islogging = prop.getProperty("is.logging").trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setAxiom(new AxiomUtil());
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
