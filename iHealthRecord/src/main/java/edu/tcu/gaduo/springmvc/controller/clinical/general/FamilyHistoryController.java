package edu.tcu.gaduo.springmvc.controller.clinical.general;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hl7.fhir.instance.model.resuorce.FamilyHistory;
import org.hl7.fhir.instance.model.resuorce.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tcu.gaduo.springmvc.controller.GeneralController;
import edu.tcu.gaduo.springmvc.service.IService;
import edu.tcu.gaduo.springmvc.service.clinical.general.FamilyHistoryServiceImpl;


@Controller
@RequestMapping("/FamilyHistory")
public class FamilyHistoryController extends GeneralController<FamilyHistory> {
	

	public static Logger logger = Logger.getLogger(FamilyHistoryController.class);
	private IService<FamilyHistory> service;
	
    @Autowired
    public FamilyHistoryController(IService<FamilyHistory> service) {
        this.service = service;
    }
	
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	

    @RequestMapping(value = "/{_id}/", method = RequestMethod.GET)
    @ResponseBody
	@Override
	public FamilyHistory read(@PathVariable("_id") String _id) { 
    	FamilyHistory familyHistory = service.findById(_id);
    	return familyHistory;
    }
    
    @RequestMapping(value = "/{_id}/_history/{_vid}", method = RequestMethod.GET)
    @ResponseBody
	@Override
	public FamilyHistory vread(@PathVariable("_id") String _id, @PathVariable("_vid") String _vid, @RequestParam(value = "_format", required = false) String _format) { 
    	FamilyHistory familyHistory = service.findById(_id);
    	return familyHistory;
    }

    

    @RequestMapping(value = "/update/{_id}/", method = RequestMethod.GET)
	public String update(@PathVariable("_id") String _id, Model model) { 
    	FamilyHistory familyHistory = service.findById(_id);
        model.addAttribute(familyHistory);
		model.addAttribute("isNew", ((_id == null)?true:false));
		return "FamilyHistory/createOrUpdate";
    }
    
    @RequestMapping(value = "/{_id}/", method = RequestMethod.PUT)
    @ResponseBody
	@Override
	public FamilyHistory update(@PathVariable("_id") String _id, @Valid FamilyHistory request, @RequestParam(value = "_format", required = false) String _format) { 
    	service.save(request);
    	return request;
    }

    
    @RequestMapping(value = "/{_id}/", method = RequestMethod.DELETE)
    @ResponseBody
	@Override
	public FamilyHistory delete(@PathVariable("_id") String _id, @RequestParam(value = "_format", required = false) String _format) { 
    	FamilyHistory familyHistory = service.findById(_id);
    	service.delete(familyHistory);
    	return familyHistory;
    }

    @RequestMapping(value = "/create/", method = RequestMethod.GET)
	public String create(Model model) { 
    	FamilyHistory familyHistory = new FamilyHistory();
        model.addAttribute(familyHistory);
		model.addAttribute("isNew", true);
		return "FamilyHistory/createOrUpdate";
    }

    @RequestMapping(value = "/{_id}/", method = RequestMethod.POST)
    @ResponseBody
	@Override
	public FamilyHistory create(@Valid FamilyHistory request, @RequestParam(value = "_format", required = false) String _format) {
    	System.out.println(request.getNoteSimple());
    	service.save(request);
    	return request;
	}


    @RequestMapping(value = "/_validate/{_id}/", method = RequestMethod.GET)
    @ResponseBody
	@Override
	public FamilyHistory validate(@PathVariable("_id") String _id) {
		// TODO Auto-generated method stub
		return null;
	}

    
    @RequestMapping(value = "/{_id}/_history/", method = RequestMethod.GET)
    @ResponseBody
	public FamilyHistory history(
			@RequestParam("_id") String _id, 
			@RequestParam("_language") String _language, 
			@RequestParam("subject") Patient subject, 
			@RequestParam(value = "_format", required = false) String _format) { 
    	FamilyHistory familyHistory = service.findById(_id);
    	return familyHistory;
    }
    
    

    @RequestMapping(value = "/_search?", method = RequestMethod.GET)
    @ResponseBody
	public FamilyHistory search(
			@RequestParam("_id") String _id, 
			@RequestParam("_language") String _language, 
			@RequestParam("subject") Patient subject) { 
    	FamilyHistory familyHistory = service.findById(_id);
    	return familyHistory;
    }
}
