package edu.tcu.gaduo.springmvc.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRepository<T> {

    T save(T entity); // Saves the given entity.                                                                  

    T findById(String primaryKey);                                                      

    List<T> findAll();                                                                 

    Page<T> findAll(Pageable pageable);                                                  

    Long count();                                                                      

    void delete(T entity);                                                           

    boolean exists(String primaryKey);                                                  

}