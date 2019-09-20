package io.reactivestax.service;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.Expenses;
import io.reactivestax.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpensesService {



    @Autowired
    private ExpensesRepository expensesRepository;


    public void createExpenses(Expenses expenses) {

        expensesRepository.save(expenses);
    }

    public Expenses getExpense(String expensesId) {
        Optional<Expenses> expenses =  expensesRepository.findById(Integer.parseInt(expensesId));
        return expenses.get();
    }

    public Expenses updateExpense(Expenses expenses, String expensesId) {

        Optional<Expenses> expensesRetrieved =  expensesRepository.findById(Integer.parseInt(expensesId));


        if(expensesRetrieved.isPresent()){
            //expenses.setExpenseId(expensesRetrieved.get().getExpenseId());
            //expensesRetrieved.get().setExpenseTypes(expenses.getExpenseTypes());
            //expensesRetrieved.get().setExpenseDesc(expenses.getExpenseDesc());
            //expensesRepository.save(expensesRetrieved.get());
            expenses.setExpenseId(Integer.parseInt(expensesId));
            expensesRepository.save(expenses);
        }

        return expenses;
    }

    public void deleteExpense(String expenseId) {
        expensesRepository.deleteById(Integer.parseInt(expenseId));
    }
}


