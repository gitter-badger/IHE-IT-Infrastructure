package edu.tcu.gaduo.springmvc.service.clinical.general;

import java.util.List;

import org.hl7.fhir.instance.model.resuorce.FamilyHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tcu.gaduo.springmvc.repository.IRepository;
import edu.tcu.gaduo.springmvc.repository.jpa.clinical.general.JpaFamilyHistoryRepositoryImpl;
import edu.tcu.gaduo.springmvc.service.IService;

@Service
public class FamilyHistoryServiceImpl implements IService<FamilyHistory> {

	IRepository<FamilyHistory> repository;

    @Autowired
    public FamilyHistoryServiceImpl(IRepository<FamilyHistory> repository) {
        this.repository = repository;
    }

	@Override
	public FamilyHistory save(FamilyHistory entity) {
		repository.save(entity);		
		return null;
	}

	@Override
    @Transactional(readOnly = true)
	public FamilyHistory findById(String primaryKey) {
    	FamilyHistory familyHistory = repository.findById(primaryKey);
		return familyHistory;
	}

	@Override
    @Transactional(readOnly = true)
	public List<FamilyHistory> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    @Transactional(readOnly = true)
	public Page<FamilyHistory> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    @Transactional(readOnly = true)
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(FamilyHistory entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
    @Transactional(readOnly = true)
	public boolean exists(String primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
