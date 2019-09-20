package io.reactivestax.service;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.ExpenseTypes;
import io.reactivestax.repository.ExpenseTypeRepository;
import io.reactivestax.repository.ResourceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseTypeService {


    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;


    public void createExpenseType(ExpenseTypes expenseTypes) {

        expenseTypeRepository.save(expenseTypes);
    }

    public ExpenseTypes getExpenseType(String expenseTypeId) {
        Optional<ExpenseTypes> expenseTypes =  expenseTypeRepository.findById(Integer.parseInt(expenseTypeId));
        return expenseTypes.get();
    }

    public void updateExpenseType(ExpenseTypes expenseTypes, String expenseTypeId) {

        Optional<ExpenseTypes> expenseTypesRetrieved =  expenseTypeRepository.findById(Integer.parseInt(expenseTypeId));

        if(expenseTypesRetrieved.isPresent()){
            expenseTypes.setExpenseTypeId(expenseTypesRetrieved.get().getExpenseTypeId());
            expenseTypesRetrieved.get().setExpenseType(expenseTypes.getExpenseType());
            expenseTypesRetrieved.get().setExpenseDesc(expenseTypes.getExpenseDesc());
            expenseTypeRepository.save(expenseTypesRetrieved.get());
        }
    }

    public void deleteExpenseType(String expenseTypeId) {
        expenseTypeRepository.deleteById(Integer.parseInt(expenseTypeId));
    }
}


