package io.reactivestax.repository;

import io.reactivestax.domain.ExpenseTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTypeRepository extends CrudRepository<ExpenseTypes,Integer> {

        public ExpenseTypes findExpenseTypesByExpenseType(String expenseType);
}
