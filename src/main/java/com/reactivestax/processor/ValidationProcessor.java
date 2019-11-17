package com.reactivestax.processor;

import com.reactivestax.model.TransactionRawData;
//import com.reactivestax.springbatchapp.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class ValidationProcessor implements ItemProcessor<TransactionRawData,TransactionRawData>
{
    public TransactionRawData process(TransactionRawData transactionRawData) throws Exception
    {
        /*
        if (employee.getId() == null){
            System.out.println("Missing employee id : " + employee.getId());
            return null;
        }
         
        try
        {
            if(Integer.valueOf(employee.getId()) <= 0) {
                System.out.println("Invalid employee id : " + employee.getId());
                return null;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid employee id : " + employee.getId());
            return null;
        }
        */
        return transactionRawData;
    }
}