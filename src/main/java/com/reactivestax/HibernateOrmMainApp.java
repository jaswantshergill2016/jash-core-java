package com.reactivestax;

//import io.reactivestax.entity.*;
//import io.reactivestax.utils.DateUtils;
import com.reactivestax.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class HibernateOrmMainApp {

    private static DateUtils du = DateUtils.getInstance();

    public static void main(String[] args) throws Exception {
        // Create a StandardServiceRegistry
        // Getting SessionFactory by defining configuration in code
        SessionFactory sf = HibernateConfigurationUtils.getSessionFactoryByCodeConfig();

        // Getting SessionFactory by defining configuration in hibnernate.cfg.xml file
        //sf= HibernateConfigurationUtils.getSessionFactoryByXmlConfig();

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();


        Vendors vendors = new Vendors("vendor 1","code 1", 1);
        session.save(vendors);

        ResourceTypes resourceTypes = new ResourceTypes("Resource Name 1","nick name 1",1);
        session.save(resourceTypes);

        ExpenseTypes expenseTypes = new ExpenseTypes("Expense Type 1","expense desc 1",1);
        session.save(expenseTypes);



        Invoices invoices = new Invoices("Invoice Id 1",234.92,"Invoice desc 1",new Date(),1,1);
        invoices.setVendors(vendors);
        invoices.setResourceTypes(resourceTypes);
        session.save(invoices);

        Invoices invoices2 = new Invoices("Invoice Id 2",123.23,"Invoice desc 2",new Date(),2,2);
        invoices2.setVendors(vendors);
        invoices2.setResourceTypes(resourceTypes);
        session.save(invoices2);

        Expenses expenses = new Expenses(345.34,"status","direction","mode","desc",'Y',new Date(),1,1);
        expenses.setVendors(vendors);
        expenses.setResourceTypes(resourceTypes);
        expenses.setExpenseTypes(expenseTypes);
        session.save(expenses);

        Payments payments = new Payments('Y',new Date(),1,2);
        payments.setVendors(vendors);
        payments.setResourceTypes(resourceTypes);
        payments.setInvoices(invoices);
        session.save(payments);


        tx.commit();

        session.close();

//        session = sf.openSession();
//
//        Vendors vendor = session.load(Vendors.class,1);
//
//        List<Invoices> invoicesList = vendor.getInvoices();
//        for (Invoices invoice:invoicesList) {
//            System.out.println(invoice.getInvoiceId());
//        }
//
//        session.close();

        sf.close();
        HibernateConfigurationUtils.shutdown();

    }

}
