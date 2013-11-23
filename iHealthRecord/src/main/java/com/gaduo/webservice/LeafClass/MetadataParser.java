package com.gaduo.webservice.LeafClass;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import com.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;
import com.gaduo.ihe.utility.AxiomUtil;
import com.gaduo.webservice.LeafClass._interface.IEBXMLParser;
import com.gaduo.zk.model.KeyValue.KeyValuesImpl;

public class MetadataParser {
	public static Logger logger = Logger.getLogger(MetadataParser.class);
	private Map<String, KeyValuesImpl> mapArray = new TreeMap<String, KeyValuesImpl>();

	private Set<Entry> set;
	@SuppressWarnings({ "unchecked" })
	public MetadataParser(OMElement element) {
		setSet(new TreeSet<Entry>());
		OMElement firstLayer = element.getFirstElement();
		OMElement secondLayer = firstLayer.getFirstElement();
		Iterator<OMElement> list = null;
		if (firstLayer != null
				&& firstLayer.getLocalName().equals("RegistryObjectList")) {
			logger.info("firstLayer : " + firstLayer.getLocalName());
			list = firstLayer.getChildElements();
		} else if (secondLayer != null
				&& secondLayer.getLocalName().equals("RegistryObjectList")) {
			logger.info("secondLayer : " + secondLayer.getLocalName());
			list = secondLayer.getChildElements();
		}
		while (list != null && list.hasNext()) {
			OMElement e = list.next();
			String id = e.getAttributeValue(new QName("id"));
			String type = getClassifyingRegistryPackageObjects(e);
			logger.info("id : " + id);
			logger.info("type : " + type);
			addItemToSet(new Entry(id, type));
			if (type != null) {
				if (type.equals("DOC_ENTRY_OBJECT")) {
					KeyValuesImpl map = new KeyValuesImpl();
					mapArray.put(id, map);
					IEBXMLParser eo = new ExtrinsicObject();
					eo.execute(e, map);
				}
				if (type.equals("FOLDER_OBJECT")) {
					KeyValuesImpl map = new KeyValuesImpl();
					mapArray.put(id, map);
					IEBXMLParser rp = new RegistryPackage();
					rp.execute(e, map);
				}
				if (type.equals("SUBMISSON_SET_OBJECT")) {
					// KeyValuesImpl map = new KeyValuesImpl();
					// mapArray.put(id, map);
					// IEBXMLParser eo = new SubmissionSet();
					// eo.execute(e, map);
				}
				if (type.equals("ASSOCIATION")) {
					KeyValuesImpl map = new KeyValuesImpl();
					mapArray.put(id, map);
					IEBXMLParser asso = new Association();
					asso.execute(e, map);
				}
			}
			/*
			 * -mapArray - id : map
			 *                   	--> DOC_ENTRY_OBJECT
			 *                   		-- mimeType
			 *                   		-- status
			 *                   		-- creationTime
			 *                   		-- hash
			 *                   		-- languageCode
			 *                   		-- repositoryUniqueId
			 *                   		-- serviceStartTime
			 *                   		-- serviceStopTime
			 *                   		-- size
			 *                   		-- sourcePatientId
			 *                   		-- PID-3
			 *                   		-- PID-5
			 *                   		-- PID-7
			 *                   		-- PID-8
			 *                   		-- PID-11
			 *                   		-- Name
			 *                   		-- Description
			 *                   		-- authorInstitution
			 *                   		-- authorPerson
			 *                   		-- authorRole
			 *                   		-- authorSpecialty
			 *                   		-- DOC_ENTRY_CLASS_CODE
			 *                   		-- DOC_ENTRY_CONFIDENTIALITY_CODE
			 *                   		-- DOC_ENTRY_EVENT_CODE
			 *                   		-- DOC_ENTRY_FORMAT_CODE
			 *                   		-- DOC_ENTRY_HEALTH_CARE_FACILITY_CODE
			 *                   		-- DOC_ENTRY_PRACTICE_SETTING_CODE
			 *                   		-- DOC_ENTRY_TYPE_CODE
			 *                   		-- DOC_ENTRY_PATIENT_IDENTIFICATION_SCHEME
			 *                   		-- DOC_ENTRY_UNIQUE_IDENTIFICATION_SCHEME
			 *                   	--> FOLDER_OBJECT
			 *                   		-- lastUpdateTime
			 *                   		-- Name
			 *                   		-- Description
			 *                   		-- FOLDER_CODE
			 *                   		-- FOLDER_PATIENT_IDENTIFICATION_SCHEME
			 *                   		-- FOLDER_UNIQUE_IDENTIFICATION_SCHEME
			 *                   	--> SUBMISSON_SET_OBJECT
			 *                   		-- 
			 *                   		-- 
			 *                   		-- 
			 *                   		-- 
			 *                   		-- 
			 *                   	--> ASSOCIATION
			 *                   		-- sourceObject
			 *                   		-- targetObject
			 *                   		-- associationType
			 * */
		}
	}

	public Map<String, KeyValuesImpl> getMapArray() {
		return mapArray;
	}

	public void setMapArray(Map<String, KeyValuesImpl> array) {
		this.mapArray = array;
	}

	public String getClassifyingRegistryPackageObjects(OMElement entry) {

		String objectType = entry.getAttributeValue(new QName("objectType"));
		if (objectType != null) {
			if (objectType
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_OBJECT)) {
				return "DOC_ENTRY_OBJECT";
			}
			if (objectType
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_OBJECT)) {
				return "FOLDER_OBJECT";
			}
			if (objectType
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_OBJECT)) {
				return "SUBMISSON_SET_OBJECT";
			}
			if (objectType
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.ASSOCIATION)) {
				return "ASSOCIATION";
			}
		}
		AxiomUtil axiom = new AxiomUtil();
		Iterator<OMElement> iterator = axiom.getChildrenWithName(entry, null,
				"Classification");
		while (iterator.hasNext()) {
			OMElement next = iterator.next();
			String classificationNode = next.getAttributeValue(new QName(
					"classificationNode"));
			if (classificationNode == null) {
				continue;
			}
			if (classificationNode
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.DOC_ENTRY_OBJECT)) {
				return "DOC_ENTRY_OBJECT";
			}
			if (classificationNode
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_OBJECT)) {
				return "FOLDER_OBJECT";
			}
			if (classificationNode
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.SUBMISSON_SET_OBJECT)) {
				return "SUBMISSON_SET_OBJECT";
			}
			if (classificationNode
					.equalsIgnoreCase(ProvideAndRegistryDocumentSet_B_UUIDs.ASSOCIATION)) {
				return "ASSOCIATION";
			}
		}
		return null;
	}
	

	public class Entry implements Comparable<Entry>{
		private String id;
		private String type;
		public Entry(String id, String type){
			this.id = id;
			this.type = type;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int compareTo(Entry o) {
			return this.id.compareTo(o.id);
		}
		
	}

	public Set<Entry> getSet() {
		return set;
	}

	public void setSet(Set<Entry> set) {
		this.set = set;
	}
	
	public void addItemToSet(Entry item) {
		this.set.add(item);
	}

}
