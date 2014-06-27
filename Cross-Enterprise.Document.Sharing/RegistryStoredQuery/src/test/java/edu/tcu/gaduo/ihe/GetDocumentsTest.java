package edu.tcu.gaduo.ihe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.tcu.gaduo.ihe.constants.RegistryStoredQueryUUIDs;
import edu.tcu.gaduo.ihe.constants.StoredQueryConstants;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.query.AdhocQueryResponseType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExtrinsicObjectType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryObjectListType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.QueryService;
import edu.tcu.gaduo.ihe.iti.xds_transaction.service.RegistryStoredQuery;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ParameterType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.QueryUUIDType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ReturnTypeType;
import edu.tcu.gaduo.ihe.iti.xds_transaction.template.ValueType;
import edu.tcu.gaduo.ihe.security.CertificateDetails;
import edu.tcu.gaduo.ihe.security._interface.ICertificate;
import edu.tcu.gaduo.ihe.utility.AxiomUtil;
import edu.tcu.gaduo.ihe.utility._interface.IAxiomUtil;

public class GetDocumentsTest {
	public static Logger logger = Logger.getLogger(GetDocumentsTest.class);
	private IAxiomUtil axiom;

	@Before
	public void setUp() {
		axiom = AxiomUtil.getInstance();	
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Truststore.jks", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");

	}

	@Test
	public void testObjectPerNumber(){
		// 要知道目前該病患有多少病歷 可以先將  openxds.properties , query.max.return 設為 100
		// 透過 findDocuments 取得回應的錯誤即可知道,  目前我這邊是控制在 1500 筆
		int listNo = 1500;
		for(int j = 850; j <= listNo; j += 50){ // 每次 query j 筆					
			for(int i = 1; i <= 30; i++){ // 實驗 i 次
				logger.info("\n =-=-= 第" + i + "次的每" + (j) + "筆 Query一次的實驗 =-=-= ");
				testObjectPerNumber(j, listNo);  
			}
		}
		// 一次查詢 5 個 DocumentEntry (Metadata 中闡述 Document 的部份)
		// 接著將 /logs/ihe-gaduo.log 移動至 /src/test/resources/log
		// 用 edu.tcu.gaduo.ihe.logfilter.LogFilter 過濾 log 訊息
		// F5 重整,  顯示 log_Client_2
		// 在利用 edu.tcu.gaduo.ihe.logfilter.SpendTime  計算  log_Client_2 兩兩為一交易所花費的時間
		// (I)ITI-18RequestBegin, (VIII)ITI-18ResponseEnd 兩兩
	}
	
	public void testObjectPerNumber(int number, int listNo){
		Class<GetDocumentsTest> clazz = GetDocumentsTest.class;
		ClassLoader loader = clazz.getClassLoader();
		InputStream is = loader.getResourceAsStream("uuid_list/uuid_list_" + listNo + ".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str = "";
		Set<String> array = new TreeSet<String>();
		int i = 1;
		try {
			while((str = br.readLine()) != null){
				if(array.size() < number){
					array.add(str);
				}else{
					array.add(str);
					QueryType query = new QueryType();
					query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_DOCUMENTS_UUID));
					query.setReturnType(new ReturnTypeType(StoredQueryConstants.LEAFCLASS));
					ParameterType p1 = new ParameterType(StoredQueryConstants.DE_ENTRY_UUID);
					Iterator<String> iterator = array.iterator();
					while(iterator.hasNext()){
						String next = iterator.next();
						p1.addValues(new ValueType("'" + next + "'"));
//						System.out.println((i++) + "\t" + next);
					}
					query.addParameters(p1);
					RegistryStoredQuery rsq = new RegistryStoredQuery();
					OMElement response = rsq.QueryGenerator(query);
					array.clear();
				}
			}
			if(!array.isEmpty()){
				QueryType query = new QueryType();
				query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_DOCUMENTS_UUID));
				query.setReturnType(new ReturnTypeType(StoredQueryConstants.LEAFCLASS));
				ParameterType p1 = new ParameterType(StoredQueryConstants.DE_ENTRY_UUID);
				Iterator<String> iterator = array.iterator();
				while(iterator.hasNext()){
					String next = iterator.next();
					p1.addValues(new ValueType("'" + next + "'"));
//					System.out.println((i++) + "\t" + next);
				}
				query.addParameters(p1);
				RegistryStoredQuery rsq = new RegistryStoredQuery();
				OMElement response = rsq.QueryGenerator(query);
				array.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testObject(){
		QueryType query = new QueryType();
		query.setQueryUUID(new QueryUUIDType(RegistryStoredQueryUUIDs.GET_DOCUMENTS_UUID));
		query.setReturnType(new ReturnTypeType(StoredQueryConstants.LEAFCLASS));
		ParameterType p1 = new ParameterType(StoredQueryConstants.DE_ENTRY_UUID);
		p1.addValues(new ValueType("'urn:uuid:10bb1288-b7e9-4918-a8e0-215ea7ea9080'"));
		query.addParameters(p1);
		
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(query);
	}
	
	
	public void testTemplate() {
		ICertificate cert = CertificateDetails.getInstance();
		cert.setCertificate("openxds_2010/OpenXDS_2010_Keystore.p12", "password",  "openxds_2010/OpenXDS_2010_Truststore.jks", "password");
		OMElement source = axiom.resourcesToOMElement("template/GetDocuments.xml");
		RegistryStoredQuery rsq = new RegistryStoredQuery();
		OMElement response = rsq.QueryGenerator(source);
		

		try{
			InputStreamReader is = new InputStreamReader(IOUtils.toInputStream(response.toString()));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AdhocQueryResponseType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdhocQueryResponseType aqr = (AdhocQueryResponseType) jaxbUnmarshaller.unmarshal(is);
			
			
			QueryService<ExtrinsicObjectType> query = new QueryService<ExtrinsicObjectType>();
			RegistryObjectListType registryObjectList = aqr.getRegistryObjectList();
			List<ExtrinsicObjectType> extrinsicObjects = registryObjectList.getExtrinsicObjects();
			Iterator<ExtrinsicObjectType> eIterator = extrinsicObjects.iterator();
			while(eIterator.hasNext()){
				ExtrinsicObjectType extrinsicObject = eIterator.next();
				String fp = query.getValue(extrinsicObject, QueryService.QueryKey.FOLDER_PATIENT);
				logger.info(fp);
			}
			
			
			
			
		}catch(JAXBException e){
			logger.error(e.toString());
		}
	}

}
