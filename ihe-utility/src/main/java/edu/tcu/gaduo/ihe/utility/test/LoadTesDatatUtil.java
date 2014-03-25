package edu.tcu.gaduo.ihe.utility.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

public class LoadTesDatatUtil {
	public String loadTestDataToString(String resources) {
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream is = loader.getResourceAsStream(resources);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String string = "";
		String temp = "";
		try {
			while ((temp = br.readLine()) != null) {
				string += temp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

	public File loadTestDataToFile(String resources) {
		ClassLoader loader = this.getClass().getClassLoader();
		URL resource = loader.getResource(resources);
		File file = null;
		try {
			file = new File(resource.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return file;
	}

	public OMElement loadTestDataToOMElement(String resources) {
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream is = loader.getResourceAsStream(resources);
		OMElement element = null;
		try {
			element = new StAXOMBuilder(is).getDocumentElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return element;
	}

	public byte[] loadTestDataToByteArray(String resources) throws IOException {
		File file = loadTestDataToFile(resources);
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		is.close();
		return bytes;
	}
}
