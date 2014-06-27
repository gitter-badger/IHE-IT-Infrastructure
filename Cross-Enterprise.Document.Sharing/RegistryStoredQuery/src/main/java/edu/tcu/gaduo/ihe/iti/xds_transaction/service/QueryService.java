package edu.tcu.gaduo.ihe.iti.xds_transaction.service;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ClassificationType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExternalIdentifierType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ExtrinsicObjectType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.RegistryPackageType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.SlotType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueListType;
import edu.tcu.gaduo.ihe.iti.xds_transaction._iti_18.ebxml.rim.ValueType;

/**
 * @author Gaduo
 * 
 * @param <T>
 */
public class QueryService<T> {
	public static Logger logger = Logger.getLogger(QueryService.class);
	private T object;
	
	public String getValue(T object, QueryKey key){
		this.object = object;
		if(object instanceof ExtrinsicObjectType ){
			
		}
		if(object instanceof RegistryPackageType ){
			return folder(key);
		}
		if(object instanceof RegistryPackageType ){
		}		
		return "";
	}
	
	private String documentEntry(QueryKey key){
		return "";
	}
	private String submissionSet(QueryKey key){
		return "";
	}	
	private String folder(QueryKey key){
		
		if(key.equals(QueryKey.FOLDER_LAST_UPDATE_TIME) ){
			List<SlotType> slots = ((RegistryPackageType) object ).getSlots();
			Iterator<SlotType> iterator = slots.iterator();
			while(iterator.hasNext()){
				SlotType slot = iterator.next();
				String name = slot.getName();
				if(name.equals("lastUpdateTime")){
					ValueListType valueList = slot.getValueList();
					List<ValueType> values = valueList.getValues();
					Iterator<ValueType> _iterator = values.iterator();
					while(_iterator.hasNext()){
						ValueType next = _iterator.next();
						String text = next.getText();
						return text;
					}
				}
			}
		}
		if(key.equals(QueryKey.FOLDER_FOLDER_CODE) ){
			List<ClassificationType> classifications = ((RegistryPackageType) object ).getClassifications();
			Iterator<ClassificationType> iterator = classifications.iterator();
			while(iterator.hasNext()){
				ClassificationType classification = iterator.next();
				String classificationScheme = classification.getClassificationScheme();
				if(classificationScheme != null && key.equals(QueryKey.FOLDER_FOLDER_CODE) && classificationScheme.equals(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_CODE)){
					String nodeRepresentation = classification.getNodeRepresentation();
					return nodeRepresentation;
				}
				
			}
		}
		
		if(key.equals(QueryKey.FOLDER_PATIENT) || key.equals(QueryKey.FOLDER_UNIQUE)){
			List<ExternalIdentifierType> externalIdentifiers = ((RegistryPackageType) object ).getExternalIdentifiers();
			Iterator<ExternalIdentifierType> iterator = externalIdentifiers.iterator();
			while(iterator.hasNext()){
				ExternalIdentifierType externalIdentifier = iterator.next();
				String identificationScheme = externalIdentifier.getIdentificationScheme();
				if(key.equals(QueryKey.FOLDER_PATIENT) && identificationScheme.equals(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_PATIENT_IDENTIFICATION_SCHEME)){
					String value = externalIdentifier.getValue();
					return value;
				}
				if(key.equals(QueryKey.FOLDER_UNIQUE) && identificationScheme.equals(ProvideAndRegistryDocumentSet_B_UUIDs.FOLDER_UNIQUE_IDENTIFICATION_SCHEME)){
					String value = externalIdentifier.getValue();
					return value;
				}			
				
			}
		}
		return "";
	}
	
	
	public enum QueryKey{
		DOC_NAME,
		FOLDER_LAST_UPDATE_TIME,
		FOLDER_FOLDER_CODE,
		FOLDER_PATIENT,
		FOLDER_UNIQUE;
	}
	
	
}
