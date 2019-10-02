package com.reactivestax.repository;

import com.reactivestax.Audits;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<Audits,Integer> {


}