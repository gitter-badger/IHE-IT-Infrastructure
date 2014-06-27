package edu.tcu.gaduo.springmvc.controller.administrative.attribution;

import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.hl7.fhir.instance.model.factory.Factory;
import org.hl7.fhir.instance.model.resuorce.Patient;
import org.hl7.fhir.instance.model.type.CodeableConcept;
import org.hl7.fhir.instance.model.type.Identifier;
import org.hl7.fhir.instance.model.type.String_;
import org.hl7.fhir.instance.model.type.Uri;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Patient")
public class PatientController {
	public static Logger logger = Logger.getLogger(PatientController.class);
	

    @RequestMapping(value = "/{_id}/", method = RequestMethod.GET)
    @ResponseBody
	public Patient read(@PathVariable("_id") String _id, @RequestParam(value = "_format", required = false) String _format) {        
    	Patient patient = new Patient();
	    patient.addIdentifier();
		try {
			Identifier identifier = patient.getIdentifier().get(0);
			Uri uri = Factory.newUri("urn:hapitest:mrns");
			identifier.setSystem(uri);
		    String_ string_ = Factory.newString_(_id);
		    identifier.setValue(string_);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
//	    patient.addName().addFamily("Test");
//	    patient.getName().get(0).addGiven("PatientOne");
		
		CodeableConcept codeableConcept = new CodeableConcept();
		codeableConcept.setTextSimple("M");
	    patient.setGender(codeableConcept);
	    return patient;
	}

}
