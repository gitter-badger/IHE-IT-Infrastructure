package edu.tcu.gaduo.springmvc.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.hl7.fhir.instance.model.resuorce.FamilyHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import edu.tcu.gaduo.springmvc.service.IService;

public class FamilyHistoryFormatter implements Formatter<FamilyHistory> {

	private final IService<FamilyHistory> service;


    @Autowired
    public FamilyHistoryFormatter(IService<FamilyHistory> service) {
        this.service = service;
    }
	
	@Override
	public String print(FamilyHistory object, Locale locale) {
		return object.getNoteSimple();
	}

	@Override
	public FamilyHistory parse(String text, Locale locale) throws ParseException {
        Collection<FamilyHistory> list = this.service.findAll();
        for (FamilyHistory l : list) {
            if (l.getNoteSimple().equals(text)) {
                return l;
            }
        }
        throw new ParseException("type not found: " + text, 0);
	}

}
