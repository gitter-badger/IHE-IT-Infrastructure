package edu.tcu.gaduo.ihe;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryObjectListType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryPackageType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.QueryService;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class GetFoldersTest extends TestCase {
	public static Logger logger = Logger.getLogger(GetFoldersTest.class);
	private IAxiomUtil axiom;

	public GetFoldersTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		axiom = AxiomUtil.getInstance();
	}

	public void testApp() {
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password", "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = axiom.resourcesToOMElement("template/GetFolders.xml");

		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		try{
			InputStreamReader is = new InputStreamReader(IOUtils.toInputStream(response.toString()));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqr = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
	
		
			QueryService<RegistryPackageType> query = new QueryService<RegistryPackageType>();
			RegistryObjectListType registryObjectList = aqr.getRegistryObjectList();
			List<RegistryPackageType> registryPackags = registryObjectList.getRegistryPackages();
			Iterator<RegistryPackageType> iterator = registryPackags.iterator();
			while(iterator.hasNext()){
				RegistryPackageType registryPackag = iterator.next();
				logger.info(registryPackag);
				String ffc = query.getValue(registryPackag, QueryService.QueryKey.FOLDER_FOLDER_CODE);
				String fl = query.getValue(registryPackag, QueryService.QueryKey.FOLDER_LAST_UPDATE_TIME);
				String fp = query.getValue(registryPackag, QueryService.QueryKey.FOLDER_PATIENT);
				String fu = query.getValue(registryPackag, QueryService.QueryKey.FOLDER_UNIQUE);
				logger.info(ffc);
				logger.info(fl);
				logger.info(fp);
				logger.info(fu);
			}			
		}catch(JAXBException e){
			logger.error(e.toString());
		}
	
	}
}
