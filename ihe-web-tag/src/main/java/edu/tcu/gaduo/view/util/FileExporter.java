/**
 * $Id: templates-FileHeader.xml,v 1.0 2009/12/16 �U��12:33:31 VincentChen Exp
 * apache $ Copyright (c) 2009 Datacom Technology Corp. All Rights Reserved.
 * 
 * H I S T O R Y [&gt;: General, +: Add, #: Modification, -: Remove, ~:
 * BugFixed] $Log: templates-FileHeader.xml,v $ $Revision 1.0 2009/12/16
 * �U��12:33:31 SunMinJa $Java Code��File Header $
 * 
 */

package edu.tcu.gaduo.view.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The <code>FileExporter.java</code> class.
 * 
 * @version $Name: $, $Revision: 1.0 $, $Date: 2009/12/16 �U��12:33:32 $
 * @author <a href="mailto:jackson@datacom.com.tw">Sun Min Ja</a>
 */

public class FileExporter {
    // @(#) STATIC VARIABLES
    // Class Standard Constants...
    public static final String VERSION = "$Name: $, $Revision: 1.0 $, $Date: 2009/12/16 �U��12:33:34 $";
    public static final String COPYRIGHT = "Copyright (c) 2009 Datacom Technology Corp. All Rights Reserved.";

    private static final Log log = LogFactory.getLog(FileExporter.class);
    
    public static String createHtmlByXml(InputStream xslFile, InputStream xmlFile) throws IOException {
        StringWriter out = new StringWriter();
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            StreamSource xslStream = new StreamSource(xslFile);
            Transformer transformer = tFactory.newTransformer(xslStream);
            try {
                StreamSource xmlStream = new StreamSource(xmlFile);
                StreamResult result = new StreamResult(out);
                transformer.transform(xmlStream, result);
            } catch (TransformerException e) {
            	e.printStackTrace();
                log.error(e.getLocalizedMessage(), e);
            }
        } catch (TransformerConfigurationException e) {
        	e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        out.write("\r\n");
        String s = out.toString();
        out.close();
        
        return s;
    }
}