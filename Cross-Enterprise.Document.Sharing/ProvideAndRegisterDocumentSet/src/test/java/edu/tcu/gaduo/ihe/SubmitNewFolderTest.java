package edu.tcu.gaduo.ihe;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.security.Certificate;
import edu.tcu.gaduo.ihe.utility.LoadTesDatatUtil;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;

import edu.tcu.gaduo.ihe.iti.xds_transaction.service.ProvideAndRegisterDocumentSet;

/**
 * Unit test for simple App.
 */
public class SubmitNewFolderTest extends TestCase {
	public static Logger logger = Logger.getLogger(SubmitNewFolderTest.class);
	LoadTesDatatUtil load;

	public SubmitNewFolderTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		load = new LoadTesDatatUtil();
	}

	public void test01() {
		OneSubmit(1);
	}

	private void OneSubmit(int number) {
		AxiomUtil axiom = new AxiomUtil();
		Certificate cert = new Certificate();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12",
				"password", "openxds_2010/OpenXDS_2010_Truststore.jks",
				"password");
		OMElement source = load
				.loadTestDataToOMElement("template/submit_new_folder.xml");
		for (int i = 0; i < number; i++) {
			String Title = "folder_" + i;
			String Description = Title;
			OMElement folder = axiom.createOMElement("Folder", null);
			OMElement title = axiom.createOMElement("Title", null);
			title.setText(Title);
			OMElement description = axiom.createOMElement("Description", null);
			description.setText(Description);

			folder.addChild(title);
			folder.addChild(description);
			source.addChild(folder);
		}
		ProvideAndRegisterDocumentSet pnr = new ProvideAndRegisterDocumentSet();
		OMElement response = pnr.MetadataGenerator(source);
		assertEquals(
				"<rs:RegistryResponse xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>",
				response.toString());
		logger.info(response);
	}
}
