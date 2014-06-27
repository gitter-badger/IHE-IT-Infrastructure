package edu.tcu.gaduo.springmvc.repository.jdbc.clinical.general;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hl7.fhir.instance.model.resuorce.FamilyHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tcu.gaduo.springmvc.repository.IRepository;

@Repository
public class JdbcFamilyHistoryRepositoryImpl implements IRepository<FamilyHistory>{

	
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insert;

    @Autowired
    public JdbcFamilyHistoryRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName("owners")
                .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }
	
	
	@Override
	public FamilyHistory save(FamilyHistory entity) {
		System.out.println("jdbc");
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
