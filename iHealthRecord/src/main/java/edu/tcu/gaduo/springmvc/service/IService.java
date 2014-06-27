package edu.tcu.gaduo.springmvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T> {

    T save(T entity); // Saves the given entity.                                                                  

    T findById(String primaryKey);                                                      

    List<T> findAll();                                                                 

    Page<T> findAll(Pageable pageable);                                                  

    Long count();                                                                      

    void delete(T entity);                                                           

    boolean exists(String primaryKey);   

}
