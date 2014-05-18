package edu.tcu.gaduo.ihe.iti.xds_transaction.template;

import org.apache.axiom.om.OMElement;

import edu.tcu.gaduo.ihe.constants.EbXML;
import edu.tcu.gaduo.ihe.constants.Namespace;
import edu.tcu.gaduo.ihe.constants.ProvideAndRegistryDocumentSet_B_UUIDs;

public class AssociationType extends General {
	private String sourceObject;
	private String targetObject;
	private String association;
	private String note;
	
	public AssociationType(){
		super();
		this.setId(createUUID());
	}
	
	public void setSourceObject(String sourceObject) {
		this.sourceObject = sourceObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public boolean validate() {
		if(sourceObject == null){ 
			logger.info("sourceObject is null.");
			return false;		
		}
		if(targetObject == null){ 
			logger.info("targetObject is null.");
			return false;		
		}
		if(association == null) { 
			logger.info("associationType is null.");
			return false;		
		}
		return true;
	}
	@Override
	public OMElement buildEbXML() {
		if(!validate()){
			return null;
		}
		OMElement root = axiom.createOMElement(EbXML.Association, Namespace.RIM3);
		root.addAttribute("id", this.getId(), null);
		root.addAttribute("objectType", ProvideAndRegistryDocumentSet_B_UUIDs.ASSOCIATION, null);
		
		root.addAttribute("sourceObject", sourceObject, null);
		root.addAttribute("targetObject", targetObject, null);
		root.addAttribute("associationType", association, null);

		if(note != null){
			OMElement slot = addSlot("SubmissionSetStatus", new String[] { note });
			root.addChild(slot);
		}
		return root;
	}
	
}
