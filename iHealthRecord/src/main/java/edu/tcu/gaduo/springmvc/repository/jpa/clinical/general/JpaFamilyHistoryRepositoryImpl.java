package edu.tcu.gaduo.springmvc.repository.jpa.clinical.general;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hl7.fhir.instance.model.resuorce.FamilyHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import edu.tcu.gaduo.springmvc.repository.IRepository;

@Repository
public class JpaFamilyHistoryRepositoryImpl implements IRepository<FamilyHistory>{

	
	@Override
	public FamilyHistory save(FamilyHistory entity) {
		System.out.println("jpa");
		return null;
	}

	@Override
	public FamilyHistory findById(String primaryKey) {
    	FamilyHistory familyHistory = new FamilyHistory();
		return familyHistory;
	}

	@Override
	public List<FamilyHistory> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<FamilyHistory> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(FamilyHistory entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
