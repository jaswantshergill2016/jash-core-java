package io.reactivestax.repository;

import io.reactivestax.domain.Expenses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends CrudRepository<Expenses,Integer> {


}
