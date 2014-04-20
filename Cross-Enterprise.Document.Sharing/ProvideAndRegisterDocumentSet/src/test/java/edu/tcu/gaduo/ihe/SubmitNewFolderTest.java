package edu.tcu.gaduo.ihe;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;
import edu.tcu.gaduo.ihe.utility.test.LoadTesDatatUtil;

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
		IAxiomUtil axiom = AxiomUtil.getInstance();
		ICertificate cert = CertificateDetails.getInstance();
		// cert.setCertificate();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = load.loadTestDataToOMElement("template/submit_new_folder.xml");
		for (int i = 0; i < number; i++) {
			String Title = "folder_" + i;
			String Description = Title;
			OMNamespace namespace = null;
			OMElement folder = axiom.createOMElement("Folder", namespace);
			OMElement title = axiom.createOMElement("Title", namespace);
			title.setText(Title);
			OMElement description = axiom.createOMElement("Description", namespace);
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
